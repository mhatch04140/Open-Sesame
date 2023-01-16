package edu.vtc.opensesame;
/*
  This class represents a connected bluetooth thread.

  Credit: Used the tutorial from theFrugalEngineer to to develop class.
  https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl

  @author Phillip Vickers
 *
 * Last Edit: 1/12/2023
 */
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread {
    private static final String TAG = "Open Sesame";
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private String valueRead;

    /**
     * Constructor for the ConnectedThread class.
     * @param socket A bluetooth socket
     */
    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        OutputStream mmOutStream = tmpOut;
    }

    /**
     * Gets a value from the connected BT device
     * @return The value
     */
    public String getValueRead(){
        return valueRead;
    }

    /**
     *
     */
    public void run() {

        byte[] buffer = new byte[1024];
        int bytes = 0; // bytes returned from read()
        int numberOfReadings = 0; //to control the number of readings from the Arduino

        // Keep listening to the InputStream until an exception occurs.
        //We just want to get 1 temperature readings from the Arduino
        while (numberOfReadings < 1) {
            try {

                buffer[bytes] = (byte) mmInStream.read();
                String readMessage;
                // If I detect a "\n" means I already read a full measurement
                if (buffer[bytes] == '\n') {
                    readMessage = new String(buffer, 0, bytes);
                    Log.e(TAG, readMessage);
                    //Value to be read by the Observer streamed by the Obervable
                    valueRead=readMessage;
                    bytes = 0;
                    numberOfReadings++;
                } else {
                    bytes++;
                }

            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }

    }

    /**
     * Cancles the Bt connection
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }


}
