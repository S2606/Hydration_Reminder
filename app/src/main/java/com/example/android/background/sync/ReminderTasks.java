package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.NotificationUtils;
import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks
{
    public static final String ACTION_INCREMENT_WATER_COUNT="increment-water-count";
    public static final String ACTION_DISMISSED_REMINDER="dismiss-notification";
    public static final String ACTION_CHARGING_REMINDER="charge-notification";

    public static void executeTask(Context context, String action)
    {
        if (ACTION_INCREMENT_WATER_COUNT.equals(action))
        {
            IncrementWaterCount(context);
        }
        else if(ACTION_DISMISSED_REMINDER.equals(action))
        {
            NotificationUtils.clearallnotification(context);
        }
        else if(ACTION_CHARGING_REMINDER.equals(action))
        {
            issueChargingReminder(context);
        }
    }

    private static void issueChargingReminder(Context context) {
        PreferenceUtilities.getChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);
    }

    private static void IncrementWaterCount(Context context)
    {
        PreferenceUtilities.incrementWaterCount(context);
        NotificationUtils.clearallnotification(context);
    }


}