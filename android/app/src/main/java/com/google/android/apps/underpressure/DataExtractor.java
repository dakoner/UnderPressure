package com.google.android.apps.underpressure;

import android.os.Handler;

import java.util.HashMap;

public class DataExtractor {
    private float pressure = 0.f;
    private float temperature = 0.f;
    private Handler handler;

    public DataExtractor(Handler handler) {
        this.handler = handler;
    }

    public String ProcessLine(String line) {
        String result = "";
        if (line.regionMatches(0, "Pressure:", 0, 9)) {
            String value = line.substring(10, line.length()-4).trim();
            pressure = Float.parseFloat(value);
        }
        else if (line.regionMatches(0, "Temperature:", 0, 12)) {
            String value = line.substring(13, line.length()-2).trim();
            temperature = Float.parseFloat(value);
        }
        else if (line.equals("")) {
            if (pressure != 0. && temperature != 0.) {
                HashMap<String, Float> hm = new HashMap<String, Float>();
                hm.put("pressure", pressure);
                hm.put("temperature", temperature);
                handler.obtainMessage(0x2a, hm).sendToTarget();
            }
            pressure = 0; temperature = 0;

        }
        return result;
    }
}
