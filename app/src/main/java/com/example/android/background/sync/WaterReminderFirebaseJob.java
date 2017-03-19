package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Shagun on 27/12/2016.
 */

public class WaterReminderFirebaseJob extends JobService {

    private AsyncTask masync;

    @Override
    public boolean onStartJob(final JobParameters job) {
        masync=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context=WaterReminderFirebaseJob.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o)
            {

                jobFinished(job,false);
            }
        };

        masync.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (masync!=null)masync.cancel(true);
        return true;
    }
}
