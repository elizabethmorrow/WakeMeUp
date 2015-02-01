package ai.wit.wakemeup.wakemeup;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Created by eamorrow on 31/01/15.
 */
public class AlarmService extends Service{
    public static String tag = AlarmService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public int onStartComand(Intent intent, int flags,int startID){
        ScheduleAlarms.setAlarms(this);
        return super.onStartCommand(intent,flags,startID);
    }


    private static PendingIntent createPendingIntent(Context context){
    Intent intent = new Intent(context, ScheduleAlarms.class);
        /*intent.putExtra(id,model.id);
        intent.putExtra(NAME, model.name);
        intent.putExtra(TIME_HOUR, model.timeHour);
        intent.putExtra(TIME_MINUTE, model.timeMinute);
        intent.putExtra(TONE, model.alarmTone);*/

        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    /*public static void cancelAlarms(Context context) {
        AlarmDBHelper dbHelper = new AlarmDBHelper(context);

        List<AlarmModel> alarms = dbHelper.getAlarms();

        if (alarms != null) {
            for (AlarmModel alarm : alarms) {
                if (alarm.isEnabled) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }*/

    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
//        Intent i = new Intent();
  //      i.setAction("com.wakemeup.alarmAction");
    //    context.sendBroadcast(i);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //long cali = calendar.getTimeInMillis();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    public static void callSetAlarm(Context context, Calendar calendar)
    {
        setAlarm(context,calendar,createPendingIntent(context));
    }

}
