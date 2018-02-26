package fr.lym;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.Time;

//Cette version fonctionne

public class listenRecordActivity extends AppCompatActivity {
    private MediaPlayer mySong;
    private Button startButton;
    private SeekBar soundSeekBar;
    private Button pauseButton;
    private Button restartButton;
    private TextView timerText;
    private TextView totalDurationText;
    private Handler handler = new Handler();
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_record);

        String name = null;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            name =(String) bundle.get("SONG");
        }


        file = new File(Environment.getExternalStorageDirectory()+ "/LYM_records/" + name + ".3gp");
        mySong = MediaPlayer.create(listenRecordActivity.this, Uri.fromFile(file));
        soundSeekBar = findViewById(R.id.soundSeekBar);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        restartButton = findViewById(R.id.restartButton);
        timerText = findViewById(R.id.timerTextView);
        totalDurationText = findViewById(R.id.totalDurationTextView);
        totalDurationText.setText(TimeConverter.millisecondsToTimer(mySong.getDuration()));

        initSeekBar();


        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        restartButton.setEnabled(false);

        setupListeners();
    }

    private void setupListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.start();
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                restartButton.setEnabled(true);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.pause();
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                restartButton.setEnabled(true);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.stop();
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                mySong = MediaPlayer.create(listenRecordActivity.this,Uri.fromFile(file));
                initSeekBar();
            }
        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && mySong != null){
                    mySong.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void initSeekBar(){
        soundSeekBar.setMax(mySong.getDuration()/1000);
        final Runnable updateSeekBar = new Runnable(){
            @Override
            public void run(){
                if(mySong != null){
                    int currentPosition = mySong.getCurrentPosition()/1000;
                    soundSeekBar.setProgress(currentPosition);
                    setTimerText();
               }
                handler.postDelayed(this,50);
            }
        };
        handler.postDelayed(updateSeekBar,50);
    }

    private void setTimerText() {
        String timer = TimeConverter.millisecondsToTimer(mySong.getCurrentPosition());
        timerText.setText(""+timer);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mySong != null){
            mySong.pause();
            mySong.release();
            mySong = null;
        }
    }

    @Override
    public void onBackPressed()
    {
        if(mySong != null){
            mySong.pause();
            mySong.release();
            mySong = null;
        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }




}
