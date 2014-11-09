package com.google.android.apps.underpressure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ArcView extends View {
    private Paint mPaint = new Paint(0);
    protected float mStartAngle = 0;
    protected float mSweepAngle = 0;

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(0xff101010);
    }

    protected void convertValue(double value) {
        mSweepAngle = (float) value;
    }

    public void setValue(double value) {
        convertValue(value);
        invalidate();
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Do nothing. Do not call the superclass method--that would start a layout pass
        // on this view's children. ArcView lays out its children in onSizeChanged().
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
               // Draw the shadow
        canvas.drawArc(0,0,100,100,mStartAngle,mSweepAngle,true,mPaint);

    }
}
