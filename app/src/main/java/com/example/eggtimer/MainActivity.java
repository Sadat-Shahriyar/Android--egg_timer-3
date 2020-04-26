package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.nio.file.FileAlreadyExistsException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button start, restart;
    private TextView timerText;

    private boolean timerRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekbar);
        start = findViewById(R.id.startButton);
        restart = findViewById(R.id.restartButton);
        timerText = findViewById(R.id.textView);

        restart.setVisibility(View.INVISIBLE);

        start.setOnClickListener(this);
        restart.setOnClickListener(this);

        seekBar.setMax(60*60);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(this);

        mediaPlayer = MediaPlayer.create(this,R.raw.finish);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.startButton){
            timerRunning = true;
            start.setEnabled(false);
            restart.setVisibility(View.VISIBLE);
            int time = seekBar.getProgress();
            seekBar.setEnabled(false);

            final CountDownTimer timer = new CountDownTimer(time*1000,1000) {
                @Override
                public void onTick(long miliSecondsRemaining) {
                    long totalSeconds = miliSecondsRemaining/1000 ;
                    long minute = totalSeconds/60;
                    long second = totalSeconds%60;

                    timerText.setText(String.valueOf(minute) + ":" + String.valueOf(second));

                    if(!timerRunning){
                        this.cancel();
                        timerText.setText("0:00");
                    }

                }

                @Override
                public void onFinish() {
                    timerText.setText("0:0");
                    mediaPlayer.start();
                }
            };

            timer.start();


        }

        else if(view.getId() == R.id.restartButton){
            timerRunning = false;
            seekBar.setProgress(0);
            seekBar.setEnabled(true);
            start.setEnabled(true);
            restart.setVisibility(View.INVISIBLE);
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int minute = i/60;
        int second = i%60;

        timerText.setText(String.valueOf(minute) + ":" +String.valueOf(second));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
