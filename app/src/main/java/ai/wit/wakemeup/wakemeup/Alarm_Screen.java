package ai.wit.wakemeup.wakemeup;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
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
public class Alarm_Screen extends Activity implements IWitListener {

    public String accessToken = "H4WHYS6X47TGKFUHNC7WJ36NLKZ7UGZS";
    private Wit snoozeWit = new Wit(accessToken, this);
    public Wit.vadConfig vad = Wit.vadConfig.detectSpeechStop;

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

    @Override
    public void witDidGraspIntent(ArrayList<WitOutcome> witOutcomes, String s, Error error) {
       // TextView jsonView = (TextView) findViewById(R.id.jsonView);
        //jsonView.setMovementMethod(new ScrollingMovementMethod());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonOutput = gson.toJson(witOutcomes);
        JSONArray obj=null;
        try {
            obj = new JSONArray(jsonOutput);
            if("set_alarm".equals(obj.getJSONObject(0).getString("intent"))) {
                Toast.makeText(this, "WOOOO", Toast.LENGTH_SHORT).show();
            }
            // other functions here under else if
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void witDidStartListening() {

    }

    @Override
    public void witDidStopListening() {

    }

    @Override
    public void witActivityDetectorStarted() {

    }

    @Override
    public String witGenerateMessageId() {
        return null;
    }
}