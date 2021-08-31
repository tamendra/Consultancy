package com.masterapps.jobconsultancy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.masterapps.jobconsultancy.models.JobAd;

public class JobApplyActivity extends AppCompatActivity {

    TextView tvTitle, tvDetail, tvQualification, tvLocation, tvSalary, tvDate;
    Button btApply;
    String jobId;

    JobAd jobAd;
    ProgressBar pbApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        Intent intent = new Intent();
        jobId = intent.getStringExtra("JOB_ID");


        tvTitle = findViewById(R.id.tvJobTitle);
        tvDetail = findViewById(R.id.tvJobDescription);
        tvQualification = findViewById(R.id.tvJobQualification);
        tvLocation = findViewById(R.id.tvJobLocation);
        tvSalary = findViewById(R.id.tvJobSalary);
        tvDate = findViewById(R.id.tvJobDate);
        pbApply = findViewById(R.id.pbApply);
        btApply = findViewById(R.id.btApply);

        Handler handler = new Handler();
        pbApply.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                jobAd = new JobAd();
                FirebaseFirestore.getInstance().collection("Jobs").document(jobId)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isComplete()){
                            jobAd = (JobAd) task.getResult().toObject(JobAd.class);
                            for(String s : jobAd.getsApplicant()){
                                if(s.equals("applied"+FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    Toast.makeText(getApplicationContext(),"Already applied",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MyApplicationFragment.class));
                                    finish();
                                }
                            }
                            if(updateJobData(jobAd)){
                                pbApply.setVisibility(View.INVISIBLE);
                                btApply.setClickable(true);
                            }else if(updateJobData(jobAd)){
                                pbApply.setVisibility(View.INVISIBLE);
                                btApply.setClickable(true);
                            }else {
                                Toast.makeText(getApplicationContext(),"try again",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(),"try again",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error occured "+e.getMessage()+ " try again",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        },0);

        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btApply.setClickable(false);
                pbApply.setVisibility(View.VISIBLE);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FirebaseFirestore.getInstance().collection("Users")
                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection("Application")
                                .document("apply"+jobId).set(jobId)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isComplete()){
                                            FirebaseFirestore.getInstance().collection("Jobs")
                                                    .document(jobId)
                                                    .collection("sApplicant")
                                                    .document("applied"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .set(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        }
                                    }
                                });


                    }
                },1000);
            }
        });

    }

    private Boolean updateJobData(JobAd jobAd) {

        if(jobAd !=null){
            tvTitle.setText(jobAd.getsTitle());
            tvDetail.setText(jobAd.getsDescription());
            tvDate.setText(jobAd.getsDateTime());
            tvLocation.setText(jobAd.getsJobLocation().toString());
            tvQualification.setText(jobAd.getsQualification());
            tvSalary.setText(jobAd.getsSalary()+"");
            return true;
        }else {
            return false;
        }

    }

}