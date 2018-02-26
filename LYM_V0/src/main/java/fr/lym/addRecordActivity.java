package fr.lym;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class addRecordActivity extends FragmentActivity  {
    boolean pause = true;
    MediaRecorder recorder;
    private FloatingActionButton recordButton;
    private FloatingActionButton deleteButton;
    private FloatingActionButton checkButton;
    private String fileName = null;
    private String folderPath = null;
    private static String TAG ="RECORDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        recordButton = (FloatingActionButton) findViewById(R.id.launchRecorder);
        deleteButton = (FloatingActionButton) findViewById((R.id.deleteButton));
        checkButton = (FloatingActionButton) findViewById(R.id.checkButton);
        recordButton.setEnabled(true);
        deleteButton.setVisibility(View.GONE);
        checkButton.setVisibility(View.GONE);

        launchDialog();

    }
        public void startOrStopRecording(View view){
                if(pause) {
                    initRecord();
                    pause = false;
                    recordButton.setImageResource(R.drawable.pause);
                    try {
                        recorder.prepare();
                        recorder.start();
                    } catch (IOException ex) {Log.e(TAG,ex.getMessage());}

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

                }

        }


        public void checkFile(View view){
            Toast.makeText(getApplicationContext(),"Audio added",Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(addRecordActivity.this,MainActivity.class);
            addRecordActivity.this.startActivity(mainIntent);
        }

// On recommence l'enregistrement
        public void deleteFile(View view){
            File file = new File(folderPath + "/" + fileName + ".3gp");
            file.delete();
            Toast.makeText(getApplicationContext(),"Audio canceled",Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(addRecordActivity.this,MainActivity.class);
            addRecordActivity.this.startActivity(mainIntent);
        }

    void initRecord(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        directoryCreator();
        recorder.setOutputFile(folderPath + "/" + fileName + ".3gp");
    }

    private void directoryCreator() {
        File checkDirectory;
        folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LYM_records";
        checkDirectory = new File(folderPath);
        if (!checkDirectory.exists()){
            checkDirectory.mkdir();
        }
    }

    public void launchDialog() {
        final EditText fileNameInput = new EditText(this);
        fileNameInput.setHint("File name");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter file name");
        builder.setView(fileNameInput);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String temp = fileNameInput.getText().toString();
                fileName =temp;

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainIntent = new Intent(addRecordActivity.this,MainActivity.class);
                addRecordActivity.this.startActivity(mainIntent);
            }
        });
        builder.create();
        builder.show();

    }
}

