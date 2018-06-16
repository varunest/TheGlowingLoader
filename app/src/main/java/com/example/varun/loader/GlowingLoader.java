package com.example.varun.loader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GlowingLoader extends View {
    private Paint rp;
    private Paint wp;
    private LineValueAnimator lineAnimator;


    public GlowingLoader(Context context) {
        super(context);
        init();
    }

    public GlowingLoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlowingLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GlowingLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        lineAnimator = new LineValueAnimator(GlowingLoader.this);
        rp = new Paint(Paint.ANTI_ALIAS_FLAG);
        rp.setColor(Color.parseColor("#FF5C5C"));
        rp.setStrokeWidth(25);
        rp.setStrokeCap(Paint.Cap.ROUND);

        wp = new Paint(Paint.ANTI_ALIAS_FLAG);
        wp.setColor(Color.WHITE);
        wp.setStrokeWidth(25);
        wp.setStrokeCap(Paint.Cap.ROUND);
    }

    private void startAnimation() {
        lineAnimator.start(new LineValueAnimator.Callback() {
            @Override
            public void onValueUpdated() {
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float offset = 0;
        float x1, x2, x3, x4, y1, y2, y3, y4;

        if (w > h) {
            offset = (w - h) * 80 / 100;
        }

        w = w - (int) offset;
        x1 = .05f * w + offset / 2;
        y1 = h / 2 + .15f * w;
        x2 = .30f * w + offset / 2;
        y2 = h / 2 + -.12f * w;
        x3 = .70f * w + offset / 2;
        y3 = h / 2 + .27f * w;
        x4 = .95f * w + offset / 2;
        y4 = h / 2 - .02f * w;

        lineAnimator.updateEdgeCoordinates(x1, x2, x3, x4, y1, y2, y3, y4);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(x1, y1, x2, y2, wp);
//        canvas.drawLine(x2, y2, x3, y3, wp);
//        canvas.drawLine(x3, y3, x4, y4, wp);

        canvas.drawLine(lineAnimator.wxa11, lineAnimator.wya11, lineAnimator.wxa12, lineAnimator.wya12, wp);
        canvas.drawLine(lineAnimator.wxa21, lineAnimator.wya21, lineAnimator.wxa22, lineAnimator.wya22, wp);
        canvas.drawLine(lineAnimator.wxa31, lineAnimator.wya31, lineAnimator.wxa32, lineAnimator.wya32, wp);
        canvas.drawLine(lineAnimator.rxa11, lineAnimator.rya11, lineAnimator.rxa12, lineAnimator.rya12, rp);
        canvas.drawLine(lineAnimator.rxa21, lineAnimator.rya21, lineAnimator.rxa22, lineAnimator.rya22, rp);
        canvas.drawLine(lineAnimator.rxa31, lineAnimator.rya31, lineAnimator.rxa32, lineAnimator.rya32, rp);
    }


}
