package fr.lym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        FloatingActionButton addRecordButton = (FloatingActionButton) findViewById(R.id.addRecordButton);
        RadioButton listenRecordButton = (RadioButton) findViewById(R.id.listenButton);
        RadioButton partitionButton = (RadioButton) findViewById(R.id.partitionButton);
        ListView recordsList = (ListView) findViewById(R.id.recordsList);
        String[] records = new String[]{"ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau","ceci est un morceau"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,records);
        recordsList.setAdapter(adapter);

        addRecordButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent addSoundIntent = new Intent(MainActivity.this,addRecordActivity.class);
                MainActivity.this.startActivity(addSoundIntent);
            }
        });

        listenRecordButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent listenRecordIntent = new Intent(MainActivity.this,listenRecordActivity.class);
                MainActivity.this.startActivity(listenRecordIntent);
            }
        });





    }
}

