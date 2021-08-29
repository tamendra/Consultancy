package com.masterapps.jobconsultancy.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.masterapps.jobconsultancy.JobApplyActivity;
import com.masterapps.jobconsultancy.R;
import com.masterapps.jobconsultancy.models.Job;

import java.util.List;

public class jobAdapter extends RecyclerView.Adapter<jobAdapter.ViewHolder> {

    List<Job> job;
    Context context;

    public jobAdapter(List<Job> job, Context context) {
        this.job = job;
        this.context = context;

        Log.d("adapter log", "adapter created");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_job,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvTitle.setText("adapter title "+i);
        if(job.get(i)!=null){

            viewHolder.tvTitle.setText(job.get(viewHolder.getAdapterPosition()).getsTitle());
            viewHolder.tvDetail.setText(job.get(viewHolder.getAdapterPosition()).getsTitle());
            viewHolder.tvDate.setText(job.get(viewHolder.getAdapterPosition()).getsDateTime());
            viewHolder.tvSalary.setText(job.get(viewHolder.getAdapterPosition()).getsSalary()+"");
            viewHolder.tvLocation.setText(job.get(viewHolder.getAdapterPosition()).getsJobLocatoin().get(0));
            viewHolder.tvQualification.setText(job.get(viewHolder.getAdapterPosition()).getsQualification());

            viewHolder.btApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobApplyActivity.class);
                    intent.putExtra("JOB_ID",job.get(viewHolder.getAdapterPosition()).getsJobId());
                    context.startActivity(new Intent(context, JobApplyActivity.class));
                }
            });
            viewHolder.btSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext()
                            ,"Job "+job.get(viewHolder.getAdapterPosition()).getsTitle()+" has been added to your Saved Job Section"
                            ,Toast.LENGTH_SHORT)
                            .show();

                    //TODO Firebase for adding jobId to saved jobs
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        //return job.size();
        return job.size();
    }

    public void refreshList(List<Job> jobs1) {
        this.job = jobs1;
        this.notifyDataSetChanged();
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
