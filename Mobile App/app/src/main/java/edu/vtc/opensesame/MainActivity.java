package edu.vtc.opensesame;
/*
  This class represents the main activity for the app.

  Credit: Used the tutorial from theFrugalEngineer to to develop bluetooth connectivity.
  https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl

  @author Mike Hatch and Phillip Vickers
 *
 * Last Edit: 3/20/2023
 */

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
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
//    private final static int ERROR_READ = 0;

    /* Enables BT */
    private final int REQUEST_ENABLE_BT=1;

    ToggleButton driverRear;
    ToggleButton driverFront;
    ToggleButton passengerFront;
    ToggleButton passengerRear;

    String status;

    ArrayList<String> data;

    SpeechRecognizer speechRecognizer;

    int count=0;

    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        Button connectToVehicle = findViewById(id.connectVehicle);
        TextView btDevices = findViewById(id.textView);

        Button searchDevices = findViewById(id.btDevices);

        ImageButton mic = findViewById(R.id.micButton);
        TextView voiceCommand= findViewById(id.voiceCommand);

        setUpDoors();


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);

        Intent speechRecognizerIntent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mic.setOnClickListener(view -> {
            if(count==0){
               mic.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24));

               //start
                speechRecognizer.startListening(speechRecognizerIntent);
                count=1;
            }
            else{
                mic.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_off_24));
                //turn off
                speechRecognizer.stopListening();
                count=0;
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                voiceCommand.setText(data.get(0));

                if(data.get(0).equalsIgnoreCase("Open driver front")){
                    message="1";
                    sendMessage();
                }
                else if (data.get(0).equalsIgnoreCase("Close driver front")){
                    message="2";
                    sendMessage();
                }
                else if(data.get(0).equalsIgnoreCase("Open driver rear")){
                    message="3";
                    sendMessage();

                }
                else if(data.get(0).equalsIgnoreCase("Close driver rear")){
                    message="4";
                    sendMessage();

                }
                else if(data.get(0).equalsIgnoreCase("Open passenger front")){
                    message="5";
                    sendMessage();
                }
                else if (data.get(0).equalsIgnoreCase("Close passenger front")){
                    message="6";
                    sendMessage();
                }
                else if(data.get(0).equalsIgnoreCase("Open passenger rear")){
                    message="7";
                    sendMessage();
                }
                else if(data.get(0).equalsIgnoreCase("Close passenger rear")){
                    message="8";
                    sendMessage();
                }
                else{
                    Toast.makeText(getApplicationContext() ,"Invalid Command",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        // Create an Observable from RxAndroid
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
                if(ConnectedThread.getValueRead()!=null)
                {
                    emitter.onNext(ConnectedThread.getValueRead());
                }
            }
            emitter.onComplete();
        });

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH_CONNECT},1);
        }

        //Button to view all paired BT devices
        //Display all the linked BT Devices
        searchDevices.setOnClickListener(view -> {
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
                    } else {
                        Log.d(TAG, "We have BT Permissions");
                    }
                    //noinspection deprecation
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    Log.d(TAG, "Bluetooth is enabled now");

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
        });

        //Connect to the HC-06 bt module
        connectToVehicle.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Trying to connect to vehicle",Toast.LENGTH_SHORT).show();
            if (arduinoBTModule != null) {
                Toast.makeText(getApplicationContext(),"Connected to vehicle",Toast.LENGTH_LONG).show();

                setUpDoors();
                connectToBTObservable.
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribeOn(Schedulers.io()).
                        subscribe(valueRead -> {
                        //can do something here if needed
                            status = valueRead;
                            Log.e(TAG, status);
                            //
                        });
            }
        });

    }//end of onCreate()

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Sets up the toggle buttons for the doors and onChangeListeners
     */
    private void setUpDoors() {
        /* Toggle Buttons for opening doors */

        driverFront = findViewById(id.driverFront);
        driverRear = findViewById(id.driverRearToggle);
        passengerFront = findViewById(id.passengerFront);
        passengerRear = findViewById(id.passengerRear);

        driverFront.setOnCheckedChangeListener((compoundButton, b) -> {
            if(driverFront.isChecked()){
                message="1";
            }
            else {
                message="2";
            }
            sendMessage();
            SystemClock.sleep(1700);
            status = ConnectedThread.getValueRead();
            Log.d(TAG, "Status: "+ status);
            checkStatus(driverFront);
        });

        driverRear.setOnCheckedChangeListener((compoundButton, b) -> {
            if(driverRear.isChecked()){
                message="3";
            }
            else {
                message="4";
            }
            sendMessage();
            SystemClock.sleep(1700);
            status = ConnectedThread.getValueRead();
            Log.d(TAG, "Status: "+ status);
            checkStatus(driverRear);
        });
        passengerFront.setOnCheckedChangeListener((compoundButton, b) -> {
            if(passengerFront.isChecked()){
                message="5";
            }
            else {
                message="6";
            }
            sendMessage();
            SystemClock.sleep(1700);
            status = ConnectedThread.getValueRead();
            Log.d(TAG, "Status: "+ status);
            checkStatus(passengerFront);
        });
        passengerRear.setOnCheckedChangeListener((compoundButton, b) -> {
            if(passengerRear.isChecked()){
                message="7";
            }
            else {
                message="8";
            }
            sendMessage();
            SystemClock.sleep(1700);
            status = ConnectedThread.getValueRead();
            Log.d(TAG, "Status: "+ status);
            checkStatus(passengerRear);
        });
    }

    /*
     * Sends a message the the arduino board
     */
    public void sendMessage() {
        ConnectedThread.write(message);
        Log.d(TAG, "Sending Message");
    }

    /*
     * Checks the status of a door
     * @param door The activated door
     */
    public void checkStatus(ToggleButton door){
        int statusCode=1;
      if(status.equals("0\r")) statusCode = 0;
      if(status.equals("180\r")) statusCode = 180;

        if(statusCode==0) door.setChecked(false);
        else if(statusCode==180){
            door.setChecked(true);
        }
    }

}//end of class



