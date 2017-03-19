package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;
import com.example.android.background.sync.ReminderTasks;
import com.example.android.background.sync.WaterReminderIntentService;

/**
 * Created by Shagun on 27/12/2016.
 */

public class NotificationUtils {

    private static final int WATER_REMINDER_PENDING_ID=2345;
    private static final int NOTIFICATION_ID=6574;
    private static final int ACTION_IGNORE_PENDING_ID=2375;
    private static final int ACTION_DRINK_PENDING_ID=1234;

    public static void clearallnotification(Context context)
    {
        NotificationManager notificationManager=(NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    //notification
    public static void remindUserBecauseCharging(Context context)
    {
        NotificationCompat.Builder notifybuild= (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeicon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent(context))
                .addAction(ignoreaction(context))
                .addAction(drinkaction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            notifybuild.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager=(NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notifybuild.build());

    }

    private static NotificationCompat.Action ignoreaction(Context context)
    {
        Intent startservice=new Intent(context, WaterReminderIntentService.class);
        startservice.setAction(ReminderTasks.ACTION_DISMISSED_REMINDER);
        PendingIntent ignorePending= PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_ID,
                startservice,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignore=new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,
                "No Thanks",
                ignorePending);
        return ignore;
    }

    private static NotificationCompat.Action drinkaction(Context context)
    {
        Intent startservice=new Intent(context, WaterReminderIntentService.class);
        startservice.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent ignorePending= PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_ID,
                startservice,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignore=new NotificationCompat.Action(R.drawable.ic_local_drink_black_24px,
                "I Drank it",
                ignorePending);
        return ignore;
    }

    private static PendingIntent pendingIntent(Context context)
    {
        Intent startactivity=new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_ID,
                startactivity,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap largeicon(Context context)
    {
        Resources res=context.getResources();
        Bitmap large= BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return large;
    }

}
