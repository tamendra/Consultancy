package com.masterapps.jobconsultancy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.masterapps.jobconsultancy.R;
import com.masterapps.jobconsultancy.models.Application;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    List<Application> applications;
    Context context;

    public ApplicationAdapter(List<Application> applications, Context context) {
        this.applications = applications;
        this.context = context;

        Log.d("ApplAdapter Log", "Adapter created");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_application, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvTitle.setText(applications.get(viewHolder.getAdapterPosition()).getsJobId() + i);

    }

    @Override
    public int getItemCount() {
        //return jobAd.size();
        return applications.size();
    }

    public void updateList(List<Application> showList) {
        this.applications = showList;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDetail, tvQualification, tvLocation, tvSalary, tvDate;


        public ViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvApplTitle);
            tvDetail = view.findViewById(R.id.tvApplDescription);
            tvQualification = view.findViewById(R.id.tvApplQualification);
            tvLocation = view.findViewById(R.id.tvApplLocation);
            tvSalary = view.findViewById(R.id.tvApplSalary);
            tvDate = view.findViewById(R.id.tvApplDate);

        }
    }

}
