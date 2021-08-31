package com.masterapps.jobconsultancy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.masterapps.jobconsultancy.adapters.jobAdapter;
import com.masterapps.jobconsultancy.models.JobAd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JobsFragment extends Fragment {

    RecyclerView rvJob;
    jobAdapter adapter;
    List<JobAd> jobAds;
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
                                    jobAds.clear();
                                    value.iterator().forEachRemaining((new Consumer<QueryDocumentSnapshot>() {
                                                @Override
                                                public void accept(QueryDocumentSnapshot queryDocumentSnapshot) {
                                                    JobAd jobAd = (JobAd) queryDocumentSnapshot.toObject(JobAd.class);
                                                    jobAds.add(jobAd);
                                                    adapter.refreshList(jobAds);
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

        jobAds = new ArrayList<>(10);

        context = this.getContext();
        rvJob = this.getView().findViewById(R.id.rvJobs);
        rvJob.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter = new jobAdapter(jobAds,context);
        rvJob.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}