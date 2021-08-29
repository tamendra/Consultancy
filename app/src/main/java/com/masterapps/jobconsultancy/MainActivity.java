package com.masterapps.jobconsultancy;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;


    MyApplicationFragment MyApplicationFragment = new MyApplicationFragment();
    ProfileFragment ProfileFragment = new ProfileFragment();
    JobsFragment JobsFragment = new JobsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.mJobs);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        {
            switch (item.getItemId()) {
                case R.id.mJobs: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, JobsFragment).commit();
                    return true;
                }
                case R.id.mProfile: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, ProfileFragment).commit();
                    return true;
                }
                case R.id.mApplications: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, MyApplicationFragment).commit();
                    return true;
                }

            }
            return false;
        }
    }
}