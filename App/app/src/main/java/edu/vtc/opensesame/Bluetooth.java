package edu.vtc.opensesame;

/**
 * Bluetooth.java
 *
 * Bluetooth settings screen.From this screen a user must be able to turn bluetooth on or off
 * and be able to connect to a bluetooth enabled device. It is from here that the app will connect
 * to the hardware component of the system.
 *
 * @author Phillip Vickers
 *
 * Last Update: 10/17/2022
 */

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bluetooth extends AppCompatActivity {

    /* Buttons for the bluetooth settings screen */
    Button btOn, btOff, connect;


    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btOn = findViewById(R.id.btOn);
        btOff = findViewById(R.id.btOff);
        connect = findViewById(R.id.connect);

        btOn.setOnClickListener(view -> enableBt());



    }

    private void enableBt() {
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
        }
        else {
            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth Enabled", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Bluetooth Already Enabled", Toast.LENGTH_LONG).show();

            }

            }
        }
}
