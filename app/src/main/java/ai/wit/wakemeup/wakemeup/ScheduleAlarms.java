package ai.wit.wakemeup.wakemeup;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by eamorrow on 31/01/15.
 */
public class ScheduleAlarms extends BroadcastReceiver {

    // @Override
    public void onReceive(Context context,Intent intent) {
        Intent alarmIntent = new Intent(context,AlarmScreen.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }

    public static void setAlarms(Context context) {

    }

    public static void cancelAlarms(Context context) {

    }

    private static PendingIntent createPendingIntent(Context context, Object model) {
        // object should be AlarmModel
        return null;
    }

}
