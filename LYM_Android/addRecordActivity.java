package fr.lym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class addRecordActivity extends AppCompatActivity {
    boolean pause = true;
    MediaRecorder recorder = new MediaRecorder();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        final FloatingActionButton recordButton = (FloatingActionButton) findViewById(R.id.launchRecorder);
        final FloatingActionButton deleteButton = (FloatingActionButton) findViewById((R.id.deleteButton));
        final FloatingActionButton checkButton = (FloatingActionButton) findViewById(R.id.checkButton);
        recordButton.setEnabled(true);
        deleteButton.setVisibility(View.GONE);
        checkButton.setVisibility(View.GONE);

        initRecord();


        recordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            // Si on clic sur enregistrer
                if(pause) {
                    pause = false;
                    recordButton.setImageResource(R.drawable.pause);
                    try {
                        recorder.prepare();
                        recorder.start();
                    } catch (IllegalStateException e) {
                        Log.e("EXCEPTION",e.getMessage());
                    } catch (IOException ex) {Log.e("EXCEPTION",ex.getMessage());}

                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
             //Si on clic sur pause
                }else{
                    pause = true;
                    recordButton.setImageResource(R.drawable.record);
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    recordButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.VISIBLE);
                    checkButton.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Audio on pause",Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"Audio added",Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(addRecordActivity.this,MainActivity.class);
                addRecordActivity.this.startActivity(mainIntent);
            }
        });
// On recommence l'enregistrement
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Audio canceled",Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(addRecordActivity.this,MainActivity.class);
                addRecordActivity.this.startActivity(mainIntent);
            }

        });

//        play.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                try{
//                    mediaPlayer.setDataSource(outPutFile);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//
//                    Toast.makeText(getApplicationContext(),"Playing Audio",Toast.LENGTH_LONG).show();
//                }catch(Exception e){}
//            }
//        });
//
////        int sampleRateInHz = 44100;
////        int channelconfig = AudioFormat.CHANNEL_IN_STEREO;
////        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
////        int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelconfig, audioFormat);
////        AudioRecord AudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, channelconfig, audioFormat, bufferSize);
////        short[] buffer = new short[bufferSize];
////        while(AudioRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
////            // Retourne le nombre de « shorts » lus, parce qu'il peut y en avoir moins que la taille du tableau
////            int nombreDeShorts = AudioRecorder.read(buffer, 0, bufferSize);
////        }
////        AudioRecorder.stop();
////        AudioRecorder.release();
////        AudioRecorder = null;
//
//
//
//
    }
    void initRecord(){
        String outPutFile = getExternalCacheDir().getAbsolutePath() + "/recording.3gp";
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(outPutFile);
    }

}
