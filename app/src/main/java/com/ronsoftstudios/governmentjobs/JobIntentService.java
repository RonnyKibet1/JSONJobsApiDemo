package com.ronsoftstudios.governmentjobs;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.ronsoftstudios.governmentjobs.Model.Job;
import com.ronsoftstudios.governmentjobs.utils.JobsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronsoft on 11/10/2017.
 */

public class JobIntentService extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public JobIntentService() {
        super("HelloIntentService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Handler handler = new Handler();
        final int delay = 300 * 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                /***STORE JOB TO USER DB***/
                JobsDBHelper jobsDBHelper= new JobsDBHelper(JobIntentService.this);
                List<Job> queriedJobs = (ArrayList<Job>)intent.getSerializableExtra("JOBS");
                for (Job job:queriedJobs) {
                    //check if the job exist in the db already
                    if(!jobsDBHelper.doesJobExist(job.getJobId())){
                        jobsDBHelper.saveNewJobData(job);
                    }else{
                        //that job already exists in db.
                        //Toast.makeText(JobIntentService.this, "Exists " + job.getJobId(), Toast.LENGTH_SHORT).show();
                    }

                }

               // Toast.makeText(JobIntentService.this, "service starting ", Toast.LENGTH_LONG).show();
                //do something
                handler.postDelayed(this, delay);
            }
        }, delay);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
    }
}