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

import java.util.Objects;

public class OpenDoors extends AppCompatActivity {

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
           } else {
               // The toggle is disabled
           }
       });

        driverSideRearToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
            } else {
                // The toggle is disabled
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
}