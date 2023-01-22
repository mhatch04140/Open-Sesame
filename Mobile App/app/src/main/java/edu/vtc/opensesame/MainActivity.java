package edu.vtc.opensesame;
/*
  MainActivity.java

  This is the main activity for the Open Sesame mobile application.

  @author Phillip Vickers
 * Last Edit: 1/13/2023
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDoorsBtn = (Button) findViewById(R.id.openDoor);

        openDoorsBtn.setOnClickListener(view -> openDoorsActivity());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.BTsettings){
           openBluetoothSetting();
       }
       if(item.getItemId()==R.id.close){
           closeApp();
       }
        return true;
    }

    private void closeApp() {
    }

    private void openBluetoothSetting() {
        Intent intent = new Intent(this, BluetoothSettings.class);
        startActivity(intent);
    }

    private void openDoorsActivity(){
        Intent intent = new Intent(this, OpenDoors.class);
        startActivity(intent);
    }
}