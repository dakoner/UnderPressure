/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.google.apps.android.underpressure;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class CommThread extends Thread {
    private BluetoothSocket socket;
    private InputStream istream;
    private OutputStream ostream;
    private Handler handler;
    private ProgressDialog dialog;
    private BluetoothAdapter adapter;

    public CommThread(BluetoothAdapter adapter, ProgressDialog dialog, Handler handler) {
        this.handler = handler;
        this.dialog = dialog;
        this.adapter = adapter;
    }

    public void run() {
        if (adapter == null)
            return;

        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        BluetoothDevice device = null;
        for (BluetoothDevice curDevice : devices) {
            if (curDevice.getName().matches("HC-06")) {
                device = curDevice;
                break;
            }
        }
        if (device == null)
            // TODO(dek): correct this address.
            device = adapter.getRemoteDevice("00:06:66:03:A7:52");

        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            socket.connect();
        } catch (IOException e) {
            socket = null;
        }
        if (socket == null) return;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        istream = tmpIn;
        ostream = tmpOut;

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        BufferedReader buffer=new BufferedReader(new InputStreamReader(istream));

        while (true) {
            try {
                String line=buffer.readLine();
                // Read from the InputStream
                handler.obtainMessage(0x2a, line).sendToTarget();

            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main Activity to shutdown the connection */
    public void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) { }
    }
}