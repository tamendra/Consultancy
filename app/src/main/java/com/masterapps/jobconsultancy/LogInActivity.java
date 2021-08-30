package com.masterapps.jobconsultancy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "LoginActivity";

    ProgressBar pbLogIn;

    EditText etEmail, etPassword;
    Button btLogIn, btGuestLogIn;
    TextView tvSignUpDirect;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        InitializeUI();

        tvSignUpDirect.setOnClickListener(this);
        btLogIn.setOnClickListener(this);
        btGuestLogIn.setOnClickListener(this);

        Handler handlerUserCheck = new Handler();
        handlerUserCheck.postDelayed(new Runnable()

        {
            @Override
            public void run() {

                 SharedPreferences sp = getApplicationContext().getSharedPreferences("com.masterapps.jobconsultancy",MODE_PRIVATE);

                 try{

                     if(sp.contains("nameKey")){

                         startActivity(new Intent(LogInActivity.this,MainActivity.class));
                         finish();
                         Toast.makeText(getApplicationContext(),"Logged in as "+sp.getString("nameKey","Username"),Toast.LENGTH_SHORT).show();

                     }
                     else{

                         pbLogIn.setVisibility(View.INVISIBLE);
                         Toast.makeText(getApplicationContext(),"Log in to continue",Toast.LENGTH_SHORT).show();
                     }
                 }
                 catch (Exception e) {
                     e.printStackTrace();
                     Toast.makeText(getApplicationContext(),"Error in auto logging",Toast.LENGTH_SHORT).show();
                 }
             }
             },0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btLogin :{
                //todo internet permission and availability

                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError("can't be empty");
                }else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError("can't be empty");
                }else if(etPassword.getText().toString().length()<6){
                    etPassword.setError("can't be less than 6 characters");
                }else{
                    loginUser(etEmail.getText().toString(),etPassword.getText().toString());
                }
                break;
            }
            case R.id.tvSignDirect:{

                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                break;
            }
            case R.id.btGuest : {

                Random r = new Random();
                int i = r.nextInt(1000);
                int ci = r.nextInt(26);
                char ch = 'a';
                for (int c = 'a'; c < 26 ; c++) {
                    if(ci==c){
                        ch++;
                        break;
                    }
                }

                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat(ch+""+"ssyyyy"+""+i+""+"dd");
                String userId = sdf.format(cal.getTime());

                Toast.makeText(getApplicationContext(),"Guest User Welcome",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInActivity.this,MainActivity.class));
                finish();
                break;
            }

        }
    }

    private void InitializeUI() {

        pbLogIn = findViewById(R.id.pbLogin);
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPassLogin);
        btLogIn = findViewById(R.id.btLogin);
        btGuestLogIn = findViewById(R.id.btGuest);
        tvSignUpDirect = findViewById(R.id.tvSignDirect);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //reference = database.getReference();

    }

    private void loginUser(String email, String password) {

        pbLogIn.setVisibility(View.VISIBLE);

        Log.d(LOG_TAG,"login initiated");

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Log.d(LOG_TAG,"User authenticated");

                            Toast.makeText(getApplicationContext(), "Welcome Back Username", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LogInActivity.this,MainActivity.class));

                        }else{

                            Toast.makeText(getApplicationContext(), "email or password is incorrect", Toast.LENGTH_LONG).show();
                            pbLogIn.setVisibility(View.INVISIBLE);
                            Log.d(LOG_TAG,"user couldn't be authenticated");


                        }
                    }
                })
        .addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getApplicationContext(), "Login cancelled", Toast.LENGTH_LONG);
            }
        });

    }




    //
}