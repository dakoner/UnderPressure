package com.google.android.apps.underpressure;

import android.content.Context;
import android.util.AttributeSet;

public class PressureArcView extends ArcView {
    public PressureArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void convertValue(double value) {
        if (value < 980.) value = 980.;
        if (value > 1050.) value = 1050.;
        mSweepAngle = (float) ((value - 980.) / (1050. - 980.) * 360.);
    }
}
