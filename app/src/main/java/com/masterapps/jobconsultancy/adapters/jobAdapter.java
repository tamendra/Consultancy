package com.masterapps.jobconsultancy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.masterapps.jobconsultancy.R;
import com.masterapps.jobconsultancy.models.JobAd;
import com.masterapps.jobconsultancy.models.UserDetails;

import java.util.List;

public class jobAdapter extends RecyclerView.Adapter<jobAdapter.ViewHolder> implements View.OnClickListener {

    List<JobAd> jobAd;
    Context context;

    public jobAdapter(List<JobAd> jobAd, Context context) {
        this.jobAd = jobAd;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_job,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvTitle.setText("adapter title "+i);
        if(jobAd.get(i)!=null){

            viewHolder.tvTitle.setText(jobAd.get(viewHolder.getAdapterPosition()).getsTitle());
            viewHolder.tvDetail.setText(jobAd.get(viewHolder.getAdapterPosition()).getsTitle());
            viewHolder.tvDate.setText(jobAd.get(viewHolder.getAdapterPosition()).getsDateTime());
            viewHolder.tvSalary.setText(jobAd.get(viewHolder.getAdapterPosition()).getsSalary()+"");
            viewHolder.tvLocation.setText(jobAd.get(viewHolder.getAdapterPosition()).getsJobLocation().get(0));
            viewHolder.tvQualification.setText(jobAd.get(viewHolder.getAdapterPosition()).getsQualification());

            viewHolder.btApply.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users")
                            .document(FirebaseAuth.getInstance().getUid());

                    documentReference.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    DocumentSnapshot doc = task.getResult();
                                    UserDetails userDetails = doc.toObject(UserDetails.class);
                                    if (userDetails.getAppliedJobs().contains(jobAd.get(viewHolder.getAdapterPosition()).getsJobId())) {
                                        Toast.makeText(context, "You have already applied for job!", Toast.LENGTH_SHORT).show();
                                        updateJobCandidate(jobAd.get(viewHolder.getAdapterPosition()).getsJobId());
                                    } else {
                                        userDetails.getAppliedJobs().add(jobAd.get(viewHolder.getAdapterPosition()).getsJobId());
                                        documentReference.update("appliedJobs", userDetails.getAppliedJobs());
                                        updateJobCandidate(jobAd.get(viewHolder.getAdapterPosition()).getsJobId());
                                        Toast.makeText(context, "You have successfully applied for job!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });

            viewHolder.btSave.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {

                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users")
                            .document(FirebaseAuth.getInstance().getUid());

                    documentReference.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    DocumentSnapshot doc = task.getResult();
                                    UserDetails userDetails = doc.toObject(UserDetails.class);
                                    if (userDetails.getSavedJobs().contains(jobAd.get(viewHolder.getAdapterPosition()))) {
                                        Toast.makeText(context, "Job is already saved!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        userDetails.getSavedJobs().add(jobAd.get(viewHolder.getAdapterPosition()).getsJobId());
                                        documentReference.update("savedJobs", userDetails.getSavedJobs());
                                        Toast.makeText(context, "Job is already saved!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
        }

    }

    private void updateJobCandidate(String jobId) {
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Jobs")
                .document(jobId);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot doc = task.getResult();
                        JobAd job = doc.toObject(JobAd.class);
                        if (job.getsApplicant().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            return;

                        } else {
                            job.getsApplicant().add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            documentReference.update("sApplicant", job.getsApplicant());
                            return;
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "could not be applied please try again " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return jobAd.size();
    }

    public void refreshList(List<JobAd> jobs1) {
        this.jobAd = jobs1;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDetail, tvQualification, tvLocation, tvSalary, tvDate;
        Button btSave, btApply;

        public ViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvJobTitle);
            tvDetail = view.findViewById(R.id.tvJobDescription);
            tvQualification = view.findViewById(R.id.tvJobQualification);
            tvLocation = view.findViewById(R.id.tvJobLocation);
            tvSalary = view.findViewById(R.id.tvJobSalary);
            tvDate = view.findViewById(R.id.tvJobDate);

            btSave = view.findViewById(R.id.btSave);
            btApply = view.findViewById(R.id.btApply);

        }
    }
}
