package com.masterapps.jobconsultancy;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;
import com.masterapps.jobconsultancy.SampleData.JobList;
import com.masterapps.jobconsultancy.adapters.*;
import com.masterapps.jobconsultancy.models.*;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JobsFragment extends Fragment {

    RecyclerView rvJob;
    jobAdapter adapter;
    List<Job> jobs;
    View parent;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = this.getView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("Jobs")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException error) {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                                {
                                    jobs.clear();
                                    value.iterator().forEachRemaining((new Consumer<QueryDocumentSnapshot>() {
                                                @Override
                                                public void accept(QueryDocumentSnapshot queryDocumentSnapshot) {
                                                    Job job = (Job) queryDocumentSnapshot.toObject(Job.class);
                                                    jobs.add(job);
                                                    adapter.refreshList(jobs);
                                                    Log.d("firestore getdata", "getting data ");
                                                }

                                            }
                                            )
                                    );

                                }
                            }});

            }
        },500);
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobs = new ArrayList<>(10);



        context = this.getContext();
        rvJob = this.getView().findViewById(R.id.rvJobs);
        rvJob.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter = new jobAdapter(jobs,context);
        rvJob.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();




    }

    void packageName(){
        /*
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = Objects.requireNonNull(this.getView()).findViewById(R.id.tvTest);
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName;
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                    textView.setText(codeName);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        PackageInfo pinfo = null;
        try {
            pinfo = this.getActivity().getPackageManager().getPackageInfo(this.getActivity().getPackageName(),0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;

            textView.setText("version "+versionNumber+" "+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

         */
    }

}