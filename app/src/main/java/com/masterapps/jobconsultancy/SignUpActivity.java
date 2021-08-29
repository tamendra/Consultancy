package com.masterapps.jobconsultancy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.masterapps.jobconsultancy.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "SignUpActivity logifo";

    //pick photo
    private int PICK_IMAGE_REQUEST = 71;
    Uri filePath, downloadUrl;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;

    EditText etEmail,etPassword, etUserName,etName, etPassConfirm;
    Button btSignUp, btLoginDirect;
    ImageView ivUSerIcon;



    List<String> testData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        InitializeUi();

        btSignUp.setOnClickListener(this);
        btLoginDirect.setOnClickListener(this);
        ivUSerIcon.setOnClickListener(this);

    }

    private void InitializeUi() {

        etEmail = findViewById(R.id.etEmailSignUp);
        etPassword = findViewById(R.id.etPassword);
        etPassConfirm = findViewById(R.id.etPassConfirm);
        etName = findViewById(R.id.etName);
        etUserName = findViewById(R.id.etUserName);
        ivUSerIcon = findViewById(R.id.imgUserIcon);

        btSignUp = findViewById(R.id.btSignUp);
        btLoginDirect = findViewById(R.id.btLogInDirect);

        testData = new ArrayList<>();

        //firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btSignUp:{
                credentialValidationAndSignUp();
                break;
            }
            case R.id.btLogInDirect:{
                startActivity(new Intent(SignUpActivity.this,LogInActivity.class));
                finish();
                break;
            }
            case R.id.imgUserIcon:{
                chooseImage();
                break;
            }
        }

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivUSerIcon.setImageBitmap(bitmap);
                //uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    downloadUrl = task.getResult();
                                    Log.d("Loginfo", "download url is "+ downloadUrl.toString());
                                    reference.child("sUserIcon").setValue(downloadUrl.toString());

                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                }
            });
        }
    }

    private void credentialValidationAndSignUp() {
        /**
         * checking credential are valid or not
         * if valid create user
         * else validate credentials
         */

        if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("can't be empty");
        }
        else if(etPassword.getText().toString().isEmpty()){
            etPassword.setError("can't be empty");
        }
        else if(etPassword.getText().toString().length()<6){
            etPassword.setError("can't be less than 6 characters");
        }
        else if(!etPassword.getText().toString().equals(etPassConfirm.getText().toString())){
            etPassConfirm.setError("password didn't match");
        }
        else if(etName.getText().toString().isEmpty()){
            etName.setError("can't be empty");
        }
        else if(etUserName.getText().toString().isEmpty()){
            etUserName.setError("can't be empty");
        }
        else if(!userNameAvail()){

            etUserName.setError("userName not available");

        }
        else{
            Log.d(LOG_TAG,"good input ");

            mAuth.fetchSignInMethodsForEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(task.isComplete()){
                        if(task.getResult().getSignInMethods().size()==0){
                            createUser(etEmail.getText().toString(),etPassword.getText().toString());
                        }
                        else{
                            etEmail.setError("email already registerd with us");
                            Toast.makeText(getApplicationContext(), "email already exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }

    private boolean userNameAvail() {
        //todo checkUserNameAvailability
        /**
         * checkUserNameAvailability
         *
         */
        return Boolean.TRUE;
    }

    private void createUser(final String email, String password) {

        /**
         * Registering User
         *
         */

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                            Log.d(LOG_TAG,"registration successful");

                            //todo update rest of the user creddential here

                            try {
                                Log.d(LOG_TAG,"user credential");
                                userCredential(task.getResult().getUser());

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("signUp","Error data upload "+e.getMessage());
                            }

                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_SHORT).show();
                            Log.d("signUp","Registration failed! Please try again later");
                        }
                    }
                });
    }

    private void userCredential(FirebaseUser firebaseUser) {

        /**
         * maintaining User Database
         *
         */

        Log.d(LOG_TAG,"user Credential upload started ");

        //todo instead of uploading every single string try object user

        /**
         * trying with object User
         * this code works
         *
         */

        try{
            User user = new User(etName.getText().toString(),etUserName.getText().toString(),etEmail.getText().toString(),"0","0","0");
            reference = database.getReference(firebaseUser.getUid());
            reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete()){
                        Log.d(LOG_TAG,"object user successful");
                        uploadImage();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,"object user failed "+e.getMessage().toString());
        }

/*
        try{

            reference = database.getReference(firebaseUser.getUid());

            try{
                reference.child("name").setValue(etName.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(LOG_TAG,"error in uploading name "+e.getMessage());
            }

            reference.child("userName").setValue(etUserName.getText().toString());
            reference.child("exp").setValue(0);
            reference.child("kauri").setValue(0);
            reference.child("userIcon").setValue(0);

            reference.child("email").setValue(firebaseUser.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "data uploaded", Toast.LENGTH_LONG).show();
                                Log.d(LOG_TAG,"data upload successful");
                            }else {
                                Toast.makeText(getApplicationContext(), "data not uploaded", Toast.LENGTH_LONG).show();
                                Log.d(LOG_TAG,"data not uploaded");
                            }
                        }
                    });
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,"error uploading data "+e.getMessage());

        }

 */


    }



}