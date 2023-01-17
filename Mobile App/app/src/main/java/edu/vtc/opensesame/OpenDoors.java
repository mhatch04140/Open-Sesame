package edu.vtc.opensesame;
/*
  This class represents the activity where the control of the vehicle doors is performed

  @author Phillip Vickers
 *
 * Last Edit: 1/16/2023
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ToggleButton;

import java.nio.charset.Charset;
import java.util.Objects;

public class OpenDoors extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_doors);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ToggleButton driverSideFrontToggle = findViewById(R.id.driverSideFrontToggle);
        ToggleButton driverSideRearToggle = findViewById(R.id.driverSideRearToggle);
        ToggleButton passengerSideFrontToggle = findViewById(R.id.passengerSideFrontToggle);
        ToggleButton passengerSideRearToggle = findViewById(R.id.passengerSideRearToggle);

       driverSideFrontToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
           if (isChecked) {
               // The toggle is enabled
               message = "1";
               sendMessage();
           } else {
               message = "2";
               sendMessage();
           }
       });

        driverSideRearToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                message = "3";
                sendMessage();
            } else {
                message = "4";
                sendMessage();
            }
        });

        passengerSideFrontToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        });

        passengerSideRearToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
            }
        });

    }
    public void sendMessage() {
        byte[] bytes = message.getBytes(Charset.defaultCharset());
        ConnectedThread.write(bytes);
    }
}