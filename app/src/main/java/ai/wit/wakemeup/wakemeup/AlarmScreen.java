package ai.wit.wakemeup.wakemeup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import ai.wit.sdk.IWitListener;
import ai.wit.sdk.Wit;
import ai.wit.sdk.model.WitOutcome;

/**
 * Created by eamorrow on 31/01/15.
 */
public class AlarmScreen extends Activity{

    // public String accessToken = "H4WHYS6X47TGKFUHNC7WJ36NLKZ7UGZS";
    // private Wit snoozeWit = new Wit(accessToken, this);
    // public Wit.vadConfig vad = Wit.vadConfig.detectSpeechStop;

    public final String TAG = this.getClass().getSimpleName();
    private PowerManager.WakeLock mWakeLock;
    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    // @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Setup layout
        this.setContentView(R.layout.alarm_screen);

        // media player play alarm
        final MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.never_gonna);
        mPlayer.start();

        String now = Calendar.HOUR + ":" +Calendar.MINUTE;
        ((TextView) findViewById(R.id.alarm_screen_time)).setText(now);

        Button stahp = (Button)findViewById(R.id.alarm_screen_button);

        /*try {
            snoozeWit.startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        stahp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPlayer.stop();
                mPlayer.release();
                finish();
            }
        });


    }

    Runnable releaseWakelock = new Runnable() {
        @Override
        public void run() {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.release();
            }
        }
    };
    // new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
//}
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
// Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
// Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
        }
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
            Log.i(TAG, "Wakelock aquired!!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }
}