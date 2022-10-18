package edu.vtc.opensesame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectBtn= findViewById(R.id.connectBtn);

        connectBtn.setOnClickListener(v -> openNewActivity());

    }

    /** Opens the bluetooth settings screen **/
    private void openNewActivity() {
        Intent intent = new Intent(this, Bluetooth.class);
        startActivity(intent);
    }

}