package com.ronsoftstudios.governmentjobs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.ronsoftstudios.governmentjobs.Model.Job;
import com.ronsoftstudios.governmentjobs.utils.JobsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronsoft on 9/17/2017.
 */

public class Receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context);
    }


    public void showNotification(final Context context) {



        final Handler handler = new Handler();
        final int delay = 200 * 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                //query db
                JobsDBHelper jobsDBHelper= new JobsDBHelper(context);
                List<Job> savedJobs = jobsDBHelper.jobList();

                //Toast.makeText(context, "jobs receiver " + savedJobs.size(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "from server list " + new MainActivity().jobsList.size(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
                android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Number of jobs " + savedJobs.size())
                        .setContentText("Enter today's weight and hit save to monitor your weight.");
                mBuilder.setContentIntent(pi);
                mBuilder.setDefaults(Notification.DEFAULT_SOUND);
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

                //do something
                handler.postDelayed(this, delay);
            }
        }, delay);



    }
}