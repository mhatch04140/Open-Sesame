package edu.vtc.opensesame;
/**
 * This class represents the main activity for the app.
 *
 * Credit: Used the tutorial from theFrugalEngineer to to develop bluetooth connectivity.
 * https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl
 *
 * @author Phillip Vickers
 *
 * Last Edit: 1/30/2023
 */

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Set;
import java.util.UUID;

import edu.vtc.opensesame.R.id;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    /* The bt module connected to the arduino board */
    BluetoothDevice arduinoBTModule ;

    /* A custom UUID used to connect to the HC-06 */
    UUID arduinoUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /* Message that is sent to the arduino */
    private String message;

    /* Handler used to send error messages */
    public static Handler handler;

    /* error read status */
    private final static int ERROR_READ = 0;

    /* Enables BT */
    private int REQUEST_ENABLE_BT=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        Button connectToVehicle = findViewById(id.connectVehicle);
        TextView btDevices = findViewById(id.textView);
        Button searchDevices = findViewById(id.btDevices);


        //Using a handler to update the interface in case of an error connecting to the BT device
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case ERROR_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino
                        //btReadings.setText(arduinoMsg);
                        break;
                }
            }
        };

        // Create an Observable from RxAndroid
        //The code will be executed when an Observer subscribes to the the Observable
        final Observable<String> connectToBTObservable = Observable.create(emitter -> {
            Log.d(TAG, "Calling connectThread class");
            //Call the constructor of the ConnectThread class
            //Passing the Arguments: an Object that represents the BT device,
            // the UUID and then the handler to update the UI
            ConnectThread connectThread = new ConnectThread(arduinoBTModule, arduinoUUID, handler);
            connectThread.run();
            //Check if Socket connected
            if (connectThread.getMmSocket().isConnected()) {
                Log.d(TAG, "Calling ConnectedThread class");
                //The pass the Open socket as arguments to call the constructor of ConnectedThread
                ConnectedThread connectedThread = new ConnectedThread(connectThread.getMmSocket());
                connectedThread.run();
                if(connectedThread.getValueRead()!=null)
                {
                    // If we have read a value from the Arduino
                    // we call the onNext() function
                    //This value will be observed by the observer
                    emitter.onNext(connectedThread.getValueRead());
                }
            }
            emitter.onComplete();

        });

        //Button to view all paired BT devices
        searchDevices.setOnClickListener(new View.OnClickListener() {
            //Display all the linked BT Devices
            @Override
            public void onClick(View view) {


                //Check if the phone supports BT
                if (bluetoothAdapter == null) {
                    Log.d(TAG, "Device doesn't support Bluetooth");
                } else {
                    Log.d(TAG, "Device supports Bluetooth");

                    if (!bluetoothAdapter.isEnabled()) {
                        Log.d(TAG, "Bluetooth is disabled");
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "We don't BT Permissions");
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                            Log.d(TAG, "Bluetooth is enabled now");
                        } else {
                            Log.d(TAG, "We have BT Permissions");
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                            Log.d(TAG, "Bluetooth is enabled now");
                        }

                    } else {
                        Log.d(TAG, "Bluetooth is enabled");
                    }
                    String btDevicesString="";
                    Set< BluetoothDevice > pairedDevices = bluetoothAdapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device: pairedDevices) {
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress();
                            Log.d(TAG, "deviceName:" + deviceName);
                            Log.d(TAG, "deviceHardwareAddress:" + deviceHardwareAddress);

                            btDevicesString=btDevicesString+deviceName+" || "+deviceHardwareAddress+"\n";

                            if (deviceName.equals("HC-06")) {
                                Log.d(TAG, "HC-06 found");
                                arduinoUUID = device.getUuids()[0].getUuid();
                                arduinoBTModule = device;
                                //HC -06 Found, enabling the button to read results
                                connectToVehicle.setEnabled(true);
                            }
                            btDevices.setText(btDevicesString);
                        }
                    }
                }
                Log.d(TAG, "Button Pressed");
            }
        });

        //Connect to the HC-06 bt module
        connectToVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Trying to connect to vehicle",Toast.LENGTH_SHORT).show();
                if (arduinoBTModule != null) {
                    Toast.makeText(getApplicationContext(),"Connected to vehicle",Toast.LENGTH_LONG).show();
                    connectToVehicle.setEnabled(false);
                    connectToVehicle.setVisibility(View.GONE);
                    searchDevices.setVisibility(View.GONE);
                    btDevices.setVisibility(View.GONE);
                    setUpDoorToggles();
                    connectToBTObservable.
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribeOn(Schedulers.io()).
                            subscribe(valueRead -> {
                            //can do something here if needed
                            });
                }
            }
        });

    }//end of onCreate()


    /**
     * Sets up the toggle buttons for the doors and onChangeListeners
     */
    private void setUpDoorToggles() {
        /* Toggle Buttons for opening doors */
        ToggleButton driverSideFrontToggle = findViewById(id.driverSideFrontToggle);
        ToggleButton driverSideRearToggle = findViewById(id.driverSideRearToggle);
        ToggleButton passengerSideFrontToggle = findViewById(id.passengerSideFrontToggle);
        ToggleButton passengerSideRearToggle = findViewById(id.passengerSideRearToggle);

        driverSideFrontToggle.setEnabled(true);
        driverSideRearToggle.setEnabled(true);
        passengerSideFrontToggle.setEnabled(true);
        passengerSideRearToggle.setEnabled(true);

        driverSideFrontToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                message = "1";
                Toast.makeText(getApplicationContext(),"Opening driverside front",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Opening driver front");
                sendMessage();
            } else {
                message = "2";
                Toast.makeText(getApplicationContext(),"Closing driverside front",Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });

        driverSideRearToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                message = "3";
                Toast.makeText(getApplicationContext(),"Opening driverside rear",Toast.LENGTH_SHORT).show();
                sendMessage();
            } else {
                message = "4";
                Toast.makeText(getApplicationContext(),"Closing driverside rear",Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });

        passengerSideFrontToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                message = "5";
                Toast.makeText(getApplicationContext(),"Opening passengerside front",Toast.LENGTH_SHORT).show();
                sendMessage();
            } else {
                message = "6";
                Toast.makeText(getApplicationContext(),"Closing passengerside front",Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });

        passengerSideRearToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                message = "7";
                Toast.makeText(getApplicationContext(),"Opening passenger front",Toast.LENGTH_SHORT).show();
                sendMessage();
            } else {
                message = "8";
                Toast.makeText(getApplicationContext(),"Opening passenger rear",Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });
    }

    /**
     * Sends a message the the arduino board
     */
    public void sendMessage() {
        ConnectedThread.write(message);
        Log.d(TAG, "Sending Message");
        }


}//end of class



