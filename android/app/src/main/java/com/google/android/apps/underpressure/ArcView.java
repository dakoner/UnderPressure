package com.google.android.apps.underpressure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ArcView extends View {
    private Paint mShadowPaint = new Paint(0);

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShadowPaint.setColor(0xff101010);
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
        canvas.drawArc(0,0,100,100,10,90,true,mShadowPaint);

    }
}
