package com.google.android.apps.underpressure;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.apps.underpressure.R;

public class PressureSensorEventListener implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;

    PressureSensorEventListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager.registerListener(this,
                mPressure,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float millibars_of_pressure = event.values[0];
        // Do something with this sensor data.
    }

}