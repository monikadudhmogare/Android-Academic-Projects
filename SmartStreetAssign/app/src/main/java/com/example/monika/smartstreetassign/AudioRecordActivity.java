package com.example.monika.smartstreetassign;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by monika on 10/23/17.
 */

public class AudioRecordActivity extends AppCompatActivity {
    static String TAG = "AudioRecordActivity";

    Button record, stop, play;
    private String outputFile = null;
    private MediaRecorder audioRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorecord);

        record = (Button) findViewById(R.id.record);
        stop = (Button) findViewById(R.id.stop);
        play = (Button) findViewById(R.id.play);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/record_audio_sample" + ".mp3";

        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(outputFile);
    }

    /* Function to handle the audio recording */
    public void recording(View view) {
        try {
            audioRecorder.prepare();
            audioRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        record.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording has been started", Toast.LENGTH_LONG)
                .show();
    }

    /* Function to handle the stop the recording*/
    public void stop(View view) {
        audioRecorder.stop();
        audioRecorder.release();
        audioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Successfully Recorded the Audio", Toast.LENGTH_LONG).show();
    }

    /* Function to handle the playing of the recorded audio file */
    public void playing(View view) {
        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
        Toast.makeText(getApplicationContext(), "Playing the Recorded Audio", Toast.LENGTH_LONG).show();
    }

    /* Function to handle the sharing of the recorded audio file */
    public void sharingFile(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(outputFile));
        try {
            startActivity(Intent.createChooser(share, "Share audio"));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
