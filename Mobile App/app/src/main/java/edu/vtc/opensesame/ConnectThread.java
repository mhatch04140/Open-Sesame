package edu.vtc.opensesame;
/*
 * This class represents the activity where the bluetooth thread is connected.
 *
 * Credit: Used the tutorial from theFrugalEngineer to to develop class.
 * https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl
 *
 * @author Phillip Vickers
 *
 * Last Edit: 3/20/2023
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
        private static final String TAG = "Open Sesame:";
        public static Handler handler;
        private final static int ERROR_READ = 0;

        @SuppressLint("MissingPermission")
        public ConnectThread(BluetoothDevice device, UUID MY_UUID, Handler handler) {
            BluetoothSocket tmp = null;
            this.handler=handler;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        /*
         * Starts the socket connection
         */
        @SuppressLint("MissingPermission")
        public void run() {

            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                handler.obtainMessage(ERROR_READ, "Unable to connect to the BT device").sendToTarget();
                Log.e(TAG, "connectException: " + connectException);
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
            }
        }

        /*
         * gets the mmSocket
         */
        public BluetoothSocket getMmSocket(){
            return mmSocket;
        }
    }

