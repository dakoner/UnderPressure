package com.google.android.apps.underpressure;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;


public class MainActivity extends Activity {
    private Handler handler;
    private CommThread thread;
    private ArcView pressureArcView;
    private TextView pressureValue;

    private ProgressDialog dialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pressureArcView = (ArcView)findViewById(R.id.pressure_arc_view);
        pressureValue = (TextView)findViewById(R.id.pressure_value);

        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                HashMap<String, Double> result = (HashMap<String,Double>)msg.obj;

                if (result.containsKey("pressure")) {
                    pressureArcView.setValue(result.get("pressure"));
                    pressureValue.setText(result.get("pressure").toString());
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = ProgressDialog.show(this, "Connecting", "Searching for a Bluetooth serial port...");
        thread = new CommThread(BluetoothAdapter.getDefaultAdapter(), dialog, handler);
        thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        if (thread != null)
            thread.cancel();
        thread = null;
    }
}
