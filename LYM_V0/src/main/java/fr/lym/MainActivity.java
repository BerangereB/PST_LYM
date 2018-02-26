package fr.lym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
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

        final FloatingActionButton addRecordButton = (FloatingActionButton) findViewById(R.id.addRecordButton);
        final RadioButton listenRecordButton = (RadioButton) findViewById(R.id.listenButton);
        final RadioButton partitionButton = (RadioButton) findViewById(R.id.partitionButton);
        ListView recordsList = (ListView) findViewById(R.id.recordsList);

        String directory = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/LYM_records";
        File dir = new File(directory);
        File[] files = dir.listFiles();
        final String[] records = new String[files.length];
        for(int i = 0; i < files.length ; i++){
            records[i] = files[i].getName().replace(".3gp","");
            Log.e("MAIN",records[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,records);
        recordsList.setAdapter(adapter);

        recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(listenRecordButton.isChecked()) {
                        String songSelected = records[position];
                        Intent listenRecordIntent = new Intent(MainActivity.this,listenRecordActivity.class);
                        listenRecordIntent.putExtra("SONG",songSelected);
                        MainActivity.this.startActivity(listenRecordIntent);
                    }
            }

        });

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
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void help(){
        Toast.makeText(this,"Help",Toast.LENGTH_LONG).show();
    }

    private void delete(){
        Toast.makeText(this,"Options on files",Toast.LENGTH_LONG).show();
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                help();
                return true;
            case R.id.action_delete:
                delete();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

