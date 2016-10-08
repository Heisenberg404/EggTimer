package com.example.andresmontoya.eggtimer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button button;
    TextView textView;
    ImageView imageView;
    CountDownTimer countDownTimer;
    Boolean couter_active = false;
    Boolean flag_win = false;

    public void updateTimer(int seconds_left){
        int minutes = (int) seconds_left/60;
        int seconds = seconds_left - minutes*60;
        String seconds_text = Integer.toString(seconds);
        String minutes_text = Integer.toString(minutes);
        minutes_text = minutes >= 0 && minutes<10? "0"+Integer.toString(minutes) : Integer.toString(minutes);
        seconds_text = seconds >= 0 && seconds<10? "0"+Integer.toString(seconds) : Integer.toString(seconds);
        textView.setText(minutes_text+":"+seconds_text);
        imageView.setImageResource(R.drawable.egg);
        flag_win = false;
    }

    public void  controlTimer(View view){
        if(!couter_active){
            couter_active = true;
            seekBar.setEnabled(false);
            button.setText("Stop!");
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000+100, 1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    seekBar.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    //doing that text of timer back to 00:00
                    //updateTimer(0);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                    flag_win = true;
                    setImageWin(flag_win);
                }
            }.start();
        }
        else{
            resetTimer();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetTimer(){
        textView.setText("00:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
        seekBar.setEnabled(true);
        couter_active = false;
    }

    public void setImageWin(Boolean flag_win){
        if (flag_win)
            imageView.setImageResource(R.drawable.chicken);
        else
            imageView.setImageResource(R.drawable.egg);
    }
}
