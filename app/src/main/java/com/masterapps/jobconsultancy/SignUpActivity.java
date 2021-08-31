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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.masterapps.jobconsultancy.models.UserDetails;

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
    //DatabaseReference reference;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseStorage storage;
    StorageReference storageReference;

    EditText etEmail,etPassword, etUserDescription,etName, etPassConfirm;
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
        etUserDescription = findViewById(R.id.etUserName);
        ivUSerIcon = findViewById(R.id.imgUserIcon);

        btSignUp = findViewById(R.id.btSignUp);
        btLoginDirect = findViewById(R.id.btLogInDirect);

        testData = new ArrayList<>();

        //firebase
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Users");

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
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage(FirebaseUser firebaseUser) {

        if(filePath!= null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString())
                    ;
            ref.putFile(filePath)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed to upload image "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            downloadUrl = task.getResult().getUploadSessionUri();
                            Log.d("Loginfo", "download url is "+ downloadUrl.getPath());

                            collectionReference.document(firebaseUser.getUid()).update("sUserIcon",downloadUrl.toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isComplete()){

                                                progressDialog.dismiss();
                                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(getApplicationContext(),"error uploading image",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"error uploading image",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent);
        }
    }

    private void credentialValidationAndSignUp() {


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
        else if(etUserDescription.getText().toString().isEmpty()){
            etUserDescription.setError("can't be empty");
        }
        else if(!userNameAvail()){

            etUserDescription.setError("can't be empty");

        }
        else{
            Log.d(LOG_TAG,"good input ");

            mAuth.fetchSignInMethodsForEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(task.isComplete()){
                        try {
                            if (task.getResult().getSignInMethods().isEmpty()) {

                                Log.d(LOG_TAG,"userDetails check no emails registered");
                                createUser(etEmail.getText().toString(), etPassword.getText().toString());
                            } else {
                                etEmail.setError("email already exist");
                                Toast.makeText(getApplicationContext(), "email already exist", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(LOG_TAG,"error in userDetails check "+e.getMessage());
                        }
                    }
                }
            });

        }
    }

    private boolean userNameAvail() {
        return Boolean.TRUE;
    }

    private void createUser(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                            Log.d(LOG_TAG,"registration successful");

                            try {
                                Log.d(LOG_TAG,"userDetails credential");
                                userCredential(task.getResult().getUser());

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("signUp","Error data upload "+e.getMessage());
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_SHORT).show();
                            Log.d("signUp","Registration failed! Please try again later");
                        }
                    }
                });
    }

    private void userCredential(FirebaseUser firebaseUser) {

        try{
            UserDetails userDetails = new UserDetails(etName.getText().toString(),etEmail.getText().toString(),"",
                    "","","","","",firebaseUser.getUid(),new ArrayList<>(10),
                    new ArrayList<>(10),new ArrayList<>(10),new ArrayList<>(10));

            firestore.collection("Users").document(firebaseUser.getUid())
                    .set(userDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){

                                Log.d(LOG_TAG,"object userDetails successful "+firebaseUser.getUid());
                                uploadImage(firebaseUser);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"image upload cancelled ",Toast.LENGTH_LONG).show();
                        }
                    });



        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG,"object userDetails failed "+e.getMessage().toString());
        }

    }



}