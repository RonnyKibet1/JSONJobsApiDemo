package com.ronsoftstudios.governmentjobs.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ronsoftstudios.governmentjobs.Model.Job;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ronsoft on 9/16/2017.
 */

public class JobsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userJobs.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "Jobs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_JOB_ID = "jobId";
    public static final String COLUMN_CREATED_AT = "createdAt";


    public JobsDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Jobs ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "jobId TEXT, "
                + "createdAt DATE)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveNewJobData(Job job) {

        SimpleDateFormat createdDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = createdDate.format(new Date());


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JOB_ID, job.getJobId());
        values.put(COLUMN_CREATED_AT, date);

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public List<Job> jobList() {

        List<Job> jobLinkedList = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Job job;

        if (cursor.moveToFirst()) {
            do {
                job = new Job();

                job.setJobId(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_ID)));
                jobLinkedList.add(job);
            } while (cursor.moveToNext());
        }


        return jobLinkedList;
    }

    public List<Job> todaysNewJobsList() {

        SimpleDateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = todaysDate.format(new Date());

        List<Job> jobLinkedList = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_CREATED_AT + " = " + date;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Job job;

        if (cursor.moveToFirst()) {
            do {
                job = new Job();

                job.setJobId(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_ID)));
                jobLinkedList.add(job);
            } while (cursor.moveToNext());
        }


        return jobLinkedList;
    }

    public boolean doesJobExist(String jobId){
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_JOB_ID + " = '" + jobId+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    public void deleteWeightData(long id, Context context, JobsAdapter adapter) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
// /      db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE id='"+id+"'");
//        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
//
//        List<WeightData> newItems = weightDataList();
//        Collections.reverse(newItems);
//        adapter.clear();
//        adapter.addAll(newItems);
//        adapter.notifyDataSetChanged();


    }

    public void updateWeightData(long jobId, Context context, JobsAdapter adapter, View view) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.execSQL("UPDATE  "+TABLE_NAME+" SET weight ='"+newWeight+"', diff ='"+weightDiff+"'  WHERE id='"+id+"'");
//        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
//
//        if(weightDiff == 0){
//            TextView text = (TextView)view.findViewById(R.id.weightGainOrLooseTextView);
//            text.setTextColor(Color.parseColor("#808080"));
//            text.setText("0 lb");
//        }
//
//
//
//        List<WeightData> newItems = weightDataList();
//        Collections.reverse(newItems);
//        adapter.clear();
//        adapter.addAll(newItems);
//        adapter.notifyDataSetChanged();
//        db.close();


    }


}
