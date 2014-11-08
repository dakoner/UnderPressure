package com.google.android.apps.underpressure;

import android.os.Handler;

import java.util.HashMap;

public class DataExtractor {
    private boolean complete_record = false;
    private double pressure = 0.;
    private double temperature = 0.;
    private Handler handler;

    public DataExtractor(Handler handler) {
        this.handler = handler;
    }

    public String ProcessLine(String line) {
        String result = "";
        if (line.regionMatches(0, "Pressure:", 0, 9)) {
            String value = line.substring(10, line.length()-4).trim();
            pressure = Double.parseDouble(value);
        }
        else if (line.regionMatches(0, "Temperature:", 0, 12)) {
            String value = line.substring(13, line.length()-2).trim();
            temperature = Double.parseDouble(value);
        }
        else if (line.equals("")) {
            if (pressure != 0. && temperature != 0.) {
                HashMap<String, Double> hm = new HashMap<String, Double>();
                hm.put("pressure", pressure);
                hm.put("temperature", temperature);
                handler.obtainMessage(0x2a, hm).sendToTarget();
            }
            pressure = 0; temperature = 0;

        }
        return result;
    }
}
