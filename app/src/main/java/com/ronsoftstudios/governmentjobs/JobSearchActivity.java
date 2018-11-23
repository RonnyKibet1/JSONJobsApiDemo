package com.ronsoftstudios.governmentjobs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ronsoftstudios.governmentjobs.Model.Job;
import com.ronsoftstudios.governmentjobs.utils.JobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JobSearchActivity extends AppCompatActivity {

    private String mSearchTerm;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Job> jobsList = new ArrayList<>();
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);


        mSearchTerm = getIntent().getStringExtra("SEARCH_TERM");

        //init
        mRecyclerView = (RecyclerView) findViewById(R.id.jobSearchRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading. Please wait...");
        mDialog.show();


        // run async task
        new DownloadFilesTask().execute();





    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {

            // call send message here
            try {
                String jobsJsonArray = MainActivity.runGet("https://api.usa.gov/jobs/search.json?size=100&query=" + mSearchTerm);
                JSONArray jsonArray = new JSONArray(jobsJsonArray);
                for(int i =0; i < jsonArray.length(); i++){
                    JSONObject job     = jsonArray.getJSONObject(i);

                    String jobId = job.getString("id");
                    String jobTitle = job.getString("position_title");
                    String organizationName = job.getString("organization_name");
                    String rateIntervalCode = job.getString("rate_interval_code");
                    String minimum = job.getString("minimum");
                    String maximum = job.getString("maximum");
                    String startDate = job.getString("start_date");
                    String endDate = job.getString("end_date");
                    JSONArray locationsJsonArray = job.getJSONArray("locations");
                    String[] locations = new String[locationsJsonArray.length()];
                    for(int k = 0; k < locationsJsonArray.length(); k++) {
                        locations[k] = locationsJsonArray.getString(k);
                    }
                    String url = job.getString("url");

                    jobsList.add(new Job(jobId, jobTitle, organizationName, rateIntervalCode, minimum, maximum, startDate, endDate, locations, url));
                }

                if(jobsList.size() <= 0){
                    mDialog.setMessage("Sorry. No jobs found matching." + mSearchTerm + " Try again later.");
                    mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mDialog.show();
                }else{
                    mDialog.dismiss();
                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            mAdapter = new JobsAdapter(jobsList, JobSearchActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


}
