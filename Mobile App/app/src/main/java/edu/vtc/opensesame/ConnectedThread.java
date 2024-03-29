package edu.vtc.opensesame;
  /* This class represents the a connected bluetooth thread.
  *
  * Credit: Used the tutorial from theFrugalEngineer to to develop class.
  * https://github.com/The-Frugal-Engineer/ArduinoBTExampleLEDControl
  *
  * @author Phillip Vickers
  * Last Edit: 3/20/2023
  */
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

    public class ConnectedThread extends Thread {

        private static final String TAG = "Open Sesame";
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private static OutputStream mmOutStream;
        private static String valueRead;

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
            mmOutStream = tmpOut;
        }

        public static String getValueRead(){
            return valueRead;
        }

        public void run() {

            byte[] buffer = new byte[1024];
            int bytes = 0; // bytes returned from read()

             while (true){
                try {

                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;

                    if (buffer[bytes] == '\n') {
                        readMessage = new String(buffer, 0, bytes);
                        Log.e(TAG, readMessage);
                        valueRead=readMessage;
                        bytes = 0;

                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                }
            }
        }
        //Write to the BT Stream
        public static void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {
                Log.d(TAG, "Trying mmOut");
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error","Unable to send message",e);
            }
        }

        /* Call this method from the main activity to shut down the connection. */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

