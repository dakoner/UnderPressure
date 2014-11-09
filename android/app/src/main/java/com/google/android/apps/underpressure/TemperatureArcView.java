package com.google.android.apps.underpressure;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.apps.underpressure.ArcView;


public class TemperatureArcView extends ArcView {
    public TemperatureArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void convertValue(double value) {
        if (value < -4.) value = -4.;
        if (value > 43.) value = 43.;
        mSweepAngle = (float) ((value - -4.) / (43. - -4.) * 360.);
    }
}
