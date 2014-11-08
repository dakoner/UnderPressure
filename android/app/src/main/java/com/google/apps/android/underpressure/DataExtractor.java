package com.google.apps.android.underpressure;

public class DataExtractor {
    private boolean complete_record = false;
    private double pressure = 0.;
    private double temperature = 0.;

    public DataExtractor() {
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
                result = "p="  + pressure + " t=" + temperature + "\n";
            }
            pressure = 0; temperature = 0;

        }
        return result;
    }
}
