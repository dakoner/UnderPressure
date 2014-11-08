package com.google.android.apps.underpressure;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    private ArcView arcView;
    private DataExtractor dataExtractor = new DataExtractor();
    private ProgressDialog dialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcView = (ArcView)findViewById(R.id.arc_view);

        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String line = (String)msg.obj;
                String result = dataExtractor.ProcessLine(line);

                if (result != "") {
                    TextView tv = (TextView) findViewById(R.id.console_text);
                    tv.append(result);
                    ((ScrollView) findViewById(R.id.console_scrollview)).scrollTo(0, tv.getHeight());
                    // How to get value from ProcessLine?
                        // shouyld we just pass the text view into the data extractor?
                    arcView.setValue(1050.);
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
