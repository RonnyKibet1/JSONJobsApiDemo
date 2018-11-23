package com.ronsoftstudios.governmentjobs.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ronsoftstudios.governmentjobs.JobDetailsActivity;
import com.ronsoftstudios.governmentjobs.Model.Job;
import com.ronsoftstudios.governmentjobs.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
    private List<Job> mJobs;
    private Context mContext;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mJObIdTxtV;
        public TextView mPositionTitleTxtV;
        public TextView mOrganizationNameTxtV;
        public TextView mRateIntervalTxtV;
        public TextView mMinimumPayTxtV;
        public TextView mMaximumPayTxtV;
        public TextView mStartApplicationDateTxtV;
        public TextView mEndApplicationDateTxtV;
        public TextView mJobLocationsTxtV;
        public TextView mJobPayTextViewFull;
        public TextView mJobUrlTxtV;
        public Button mApplyNowBtn;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            mJObIdTxtV = (TextView) v.findViewById(R.id.jobIdRowTextView);
            mPositionTitleTxtV = (TextView) v.findViewById(R.id.jobPositionTitleRowTextView);
            mOrganizationNameTxtV = (TextView) v.findViewById(R.id.jobOrganizationNameRowTextView);
            mRateIntervalTxtV = (TextView) v.findViewById(R.id.jobRateIntervalCodeRowTextView);
            mMinimumPayTxtV = (TextView) v.findViewById(R.id.jobMinimumRowTextView);
            mMaximumPayTxtV = (TextView) v.findViewById(R.id.jobMaximumRowTextView);
            mStartApplicationDateTxtV = (TextView) v.findViewById(R.id.jobStartDateRowTextView);
            mEndApplicationDateTxtV = (TextView) v.findViewById(R.id.jobEndDateRowTextView);
            mJobLocationsTxtV = (TextView) v.findViewById(R.id.jobsLocationRowTextView);
            mJobUrlTxtV = (TextView) v.findViewById(R.id.jobUrlRowTextView);
            mJobPayTextViewFull = (TextView)v.findViewById(R.id.jobPayTextViewFull);
            mApplyNowBtn = (Button)v.findViewById(R.id.applyNowButton);

        }
    }

    public void add(int position, Job job) {
        mJobs.add(position, job);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mJobs.remove(position);
        notifyItemRemoved(position);
    }

    private void goToViewJobDescription(String url, Context context){
        Intent goToViewDesc = new Intent(context, JobDetailsActivity.class);
        goToViewDesc.putExtra("JOB_URL", url);
        context.startActivity(goToViewDesc);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public JobsAdapter(List<Job> myDataset, Context context) {
        mJobs = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.job_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
         SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

        final Job job = mJobs.get(position);
        holder.mJObIdTxtV.setText(job.getJobId());
        holder.mPositionTitleTxtV.setText(job.getJobPositionTitle());
        holder.mOrganizationNameTxtV.setText(job.getJobOrganization());
        holder.mRateIntervalTxtV.setText(job.getJobRateIntervalCode());
        holder.mMinimumPayTxtV.setText(currencyFormat(job.getJobMimimumPay()));
        holder.mMaximumPayTxtV.setText(job.getJobMaximumPay());

        String startApplicationDate = job.getJobStartApplicationDate();
        String datePostedStr = startApplicationDate;
        try {
            Date date = inputFormat.parse(datePostedStr);
            String niceDatePostedStr = (String) DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
            holder.mStartApplicationDateTxtV.setText("Posted: " + niceDatePostedStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String endApplicationDate = job.getJobEndApplicationDate();
        String dateEndingStr = endApplicationDate;
        try {
            Date date = inputFormat.parse(dateEndingStr);
            String niceDateEndingStr = (String) DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
            holder.mEndApplicationDateTxtV.setText("Ends in: " + niceDateEndingStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String salaryText = job.getJobRateIntervalCode();
        if(salaryText.equals("PA")){
            salaryText = "$" + currencyFormat(job.getJobMimimumPay()) + " - " + currencyFormat(job.getJobMaximumPay()) + " per year.";
        }else if(salaryText.equals("PM")){
            salaryText = "$" + currencyFormat(job.getJobMimimumPay()) + " - " + currencyFormat(job.getJobMaximumPay()) + " per month.";
        }else if(salaryText.equals("PH")){
            salaryText = "$" + currencyFormat(job.getJobMimimumPay()) + " - " + currencyFormat(job.getJobMaximumPay()) + " per hour.";
        }

        holder.mJobPayTextViewFull.setText(salaryText);

        holder.mJobLocationsTxtV.setText(TextUtils.join(" - ",job.getJobLocations()));
       // holder.mJobUrlTxtV.setText(job.getJobUrl());
        holder.mApplyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToViewJobDescription(job.getJobUrl().trim(), mContext);
                String url = job.getJobUrl().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });



    }



    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mJobs.size();
    }



}