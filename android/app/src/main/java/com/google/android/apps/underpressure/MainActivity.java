package com.google.android.apps.underpressure;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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
    private FakeCommThread thread;
    private ArcView pressureArcView;
    private TextView pressureValue;
    private ArcView temperatureArcView;
    private LineView temperatureLineView;
    private TextView temperatureValue;
    private ProgressDialog dialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pressureArcView = (ArcView)findViewById(R.id.pressure_arc_view);
        pressureValue = (TextView)findViewById(R.id.pressure_value);
        temperatureArcView = (ArcView)findViewById(R.id.temperature_arc_view);
        temperatureLineView = (LineView)findViewById(R.id.temperature_line_view);
        temperatureValue = (TextView)findViewById(R.id.temperature_value);
        /*final List<Float> datapoints = new ArrayList<Float>();*/
        float[] dp = new float[5];
        dp[0] = 1;
        dp[1] = 2;
        dp[2] = 3;
        dp[3] = 4;
        temperatureLineView.setChartData(dp);


        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                HashMap<String, Float> result = (HashMap<String,Float>)msg.obj;

                // TODO(dek): subclasses of ArcView with different conversions
                if (result.containsKey("pressure")) {
                    pressureArcView.setValue(result.get("pressure"));
                    pressureValue.setText(result.get("pressure").toString());
                }
                if (result.containsKey("temperature")) {
                    temperatureArcView.setValue(result.get("temperature"));
                    temperatureValue.setText(result.get("temperature").toString());
                    /*datapoints.add(result.get("temperature"));
                    float[] dp = new float[datapoints.size()];
                    int i = 0;
                    for (ListIterator<Float> it = datapoints.listIterator(); it.hasNext(); ) {
                        Float t = it.next();
                        dp[i] = t;
                        i++;
                    }
                    temperatureLineView.setChartData(dp);*/
                }
            }
        };
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dialog = ProgressDialog.show(this, "Connecting", "Searching for a Bluetooth serial port...");
        thread = new FakeCommThread(BluetoothAdapter.getDefaultAdapter(), dialog, handler);
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
