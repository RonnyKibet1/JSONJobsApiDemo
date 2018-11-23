package com.ronsoftstudios.governmentjobs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ronsoftstudios.governmentjobs.Model.Job;
import com.ronsoftstudios.governmentjobs.utils.JobsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private ImageButton mSearchJobImageButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Job> jobsList = new ArrayList<>();
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(this);

        //init
        mSearchEditText = (EditText)findViewById(R.id.searchEditText);
        mSearchJobImageButton = (ImageButton)findViewById(R.id.searchJobsImageButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recentJobsHomeREcyclerView);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        queryJobs();



        /**listen to search**/
        mSearchJobImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = mSearchEditText.getText().toString();
                if(!searchTerm.isEmpty()){
                    Intent goToSearchActivity = new Intent(MainActivity.this, JobSearchActivity.class);
                    goToSearchActivity.putExtra("SEARCH_TERM", searchTerm);
                    startActivity(goToSearchActivity);
                }else{
                    Toast.makeText(MainActivity.this, "You must enter a search term.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**SENDS NOTIFICATION ON NEW JOBS**/
        Intent intent = new Intent(MainActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //bir gun daily
        int days = 1;
       // long interval = 1000 * 60 * 60 * 24 * days; //5 days
        long interval = 60000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 00);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        intent.putExtra("JOBS", (Serializable) jobsList);
        sendBroadcast(intent);








    }

    @Override
    protected void onStart() {
        super.onStart();

        queryJobs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryJobs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        queryJobs();
    }

    private void queryJobs(){

        mDialog.setMessage("Loading. Please wait...");
        mDialog.show();

        // specify an adapter (see also next example)
        new DownloadFilesTask().execute();


    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {
            // call send message here
            try {
                String jobsJsonArray = runGet("https://api.usa.gov/jobs/search.json?size=100");
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
                    String jobIdFormatted = jobId.substring(jobId.indexOf(':'), jobId.length()).trim();

                    Job newJob = new Job(jobIdFormatted, jobTitle, organizationName, rateIntervalCode, minimum, maximum, startDate, endDate, locations, url);

                    jobsList.add(newJob);

                }
                if(jobsList.size() <= 0){
                    mDialog.setMessage("Sorry. No jobs found. Try again later.");
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
            mAdapter = new JobsAdapter(jobsList, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);


            /**START INTENT SERVICE***/

            Intent intentService = new Intent(MainActivity.this, JobIntentService.class);
            intentService.putExtra("JOBS", (Serializable) jobsList);
            startService(intentService);

        }
    }

    static OkHttpClient client = new OkHttpClient();
    static String runGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
