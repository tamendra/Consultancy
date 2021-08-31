package com.masterapps.jobconsultancy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.masterapps.jobconsultancy.models.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationFragment extends Fragment {

    List<Application> savedjobs, appliedJobs;

    TabLayout tabApplicationF;
    RecyclerView rvAppl;
    List<String> savedJobId;
    List<String> appliedJobId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedjobs = new ArrayList<>(10);
        appliedJobs = new ArrayList<>(10);


        return inflater.inflate(R.layout.fragment_application, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabApplicationF = view.findViewById(R.id.tbAppl);
        rvAppl = view.findViewById(R.id.rvApplications);

        tabApplicationF.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}