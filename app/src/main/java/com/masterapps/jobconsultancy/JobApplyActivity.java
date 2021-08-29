package com.masterapps.jobconsultancy;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.masterapps.jobconsultancy.SampleData.JobList;
import com.masterapps.jobconsultancy.models.Application;
import com.masterapps.jobconsultancy.models.Job;

public class JobApplyActivity extends AppCompatActivity {

    TextView tvTitle, tvDetail, tvQualification, tvLocation, tvSalary, tvDate;
    Button btSave, btApply;

    Job job;
    ProgressBar pbApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        Intent intent = new Intent();
        intent.getStringExtra("JOB_ID");

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
                //TODO Firebase load job data from jobId

                job = JobList.myJobMLM();

                if(updateJobData(job)){

                    pbApply.setVisibility(View.INVISIBLE);
                    btApply.setClickable(true);
                }
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
                        //TODO Firebase add jobId to User's applied jobs section

                        Application application = new Application(job.getsJobId(),"thisUser07");

                        Toast.makeText(v.getContext(),"You have successfully Applied for this job", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },3000);
            }
        });

    }

    private Boolean updateJobData(Job job) {

        if(job!=null){
            tvTitle.setText(job.getsTitle().toString());
            tvDetail.setText(job.getsDescription().toString());
            tvDate.setText(job.getsDateTime().toString());
            tvLocation.setText(job.getsJobLocatoin().toString());
            tvQualification.setText(job.getsQualification().toString());
            tvSalary.setText(job.getsSalary()+"");
        }

        return true;
    }

}