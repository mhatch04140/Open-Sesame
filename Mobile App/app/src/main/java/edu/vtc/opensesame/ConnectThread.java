package edu.vtc.opensesame;
/*
  This class represents a connecting to a bluetooth thread.

  Credit: Used the tutorial from theFrugalEngineer to to develop class.
  https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl

  @author Phillip Vickers
 *
 * Last Edit: 1/12/2023
 */
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private static final String TAG = "Open Sesame";
    public static Handler handler;
    private final static int ERROR_READ = 0;

    /**
     * Constructor for ConnectThread
     *
     * @param device  The BT device
     * @param MY_UUID The UUID of the bluetooth device
     * @param handler Handler for the connection
     */
    @SuppressLint("MissingPermission")
    public ConnectThread(BluetoothDevice device, UUID MY_UUID, Handler handler) {
        BluetoothSocket temp = null;
        ConnectThread.handler = handler;

        try {
            temp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = temp;
    }


    /**
     * Connects to a bluetooth device.
     */
    @SuppressLint("MissingPermission")
    public void run() {
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            handler.obtainMessage(ERROR_READ, "Unable to connect to the BT device").sendToTarget();
            Log.e(TAG, "connectException: " + connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
        }
    }

    /**
     * Closes the BT connection
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

    /**
     * Gets the BT socket
     *
     * @return the BT socket
     */
    public BluetoothSocket getMmSocket() {
        return mmSocket;
    }

}