package com.example.varun.loader;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GlowingLoader extends View {
    private Paint paint;
    private LineValueAnimator lineAnimator;
    private static final String COLOR_RED = "#FF5C5C";
    private static final String COLOR_WHITE = "#FFFFFF";

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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setXfermode(new PorterDuffXfermode(
//                PorterDuff.Mode.SRC_OUT));
//        paint.setAntiAlias(true);
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

        paint.setMaskFilter(null);
        paint.setStrokeWidth(30);

        paint.setColor(Color.parseColor(COLOR_WHITE));
        if (lineAnimator.wxa11 != lineAnimator.wxa12 && lineAnimator.wya11 != lineAnimator.wya12)
            canvas.drawLine(lineAnimator.wxa11, lineAnimator.wya11, lineAnimator.wxa12, lineAnimator.wya12, paint);
        if (lineAnimator.wxa21 != lineAnimator.wxa22 && lineAnimator.wya21 != lineAnimator.wya22)
            canvas.drawLine(lineAnimator.wxa21, lineAnimator.wya21, lineAnimator.wxa22, lineAnimator.wya22, paint);
        if (lineAnimator.wxa31 != lineAnimator.wxa32 && lineAnimator.wya31 != lineAnimator.wya32)
            canvas.drawLine(lineAnimator.wxa31, lineAnimator.wya31, lineAnimator.wxa32, lineAnimator.wya32, paint);

        paint.setColor(Color.parseColor(COLOR_RED));
        if (lineAnimator.rxa11 != lineAnimator.rxa12 && lineAnimator.rya11 != lineAnimator.rya12)
            canvas.drawLine(lineAnimator.rxa11, lineAnimator.rya11, lineAnimator.rxa12, lineAnimator.rya12, paint);
        if (lineAnimator.rxa21 != lineAnimator.rxa22 && lineAnimator.rya21 != lineAnimator.rya22)
            canvas.drawLine(lineAnimator.rxa21, lineAnimator.rya21, lineAnimator.rxa22, lineAnimator.rya22, paint);
        if (lineAnimator.rxa31 != lineAnimator.rxa32 && lineAnimator.rya31 != lineAnimator.rya32)
            canvas.drawLine(lineAnimator.rxa31, lineAnimator.rya31, lineAnimator.rxa32, lineAnimator.rya32, paint);


        paint.setMaskFilter(new BlurMaskFilter(35, BlurMaskFilter.Blur.NORMAL));

        paint.setStrokeWidth(80);


        paint.setColor(Color.parseColor(COLOR_WHITE));
        paint.setAlpha(0x10);
        if (lineAnimator.wxa11 != lineAnimator.wxa12 && lineAnimator.wya11 != lineAnimator.wya12)
            canvas.drawLine(lineAnimator.wxa11, lineAnimator.wya11 + 100, lineAnimator.wxa12, lineAnimator.wya12 + 100, paint);
        if (lineAnimator.wxa21 != lineAnimator.wxa22 && lineAnimator.wya21 != lineAnimator.wya22)
            canvas.drawLine(lineAnimator.wxa21, lineAnimator.wya21 + 100, lineAnimator.wxa22, lineAnimator.wya22 + 100, paint);
        if (lineAnimator.wxa31 != lineAnimator.wxa32 && lineAnimator.wya31 != lineAnimator.wya32)
            canvas.drawLine(lineAnimator.wxa31, lineAnimator.wya31 + 100, lineAnimator.wxa32, lineAnimator.wya32 + 100, paint);

        paint.setColor(Color.parseColor(COLOR_RED));
        paint.setAlpha(0x10);
        if (lineAnimator.rxa11 != lineAnimator.rxa12 && lineAnimator.rya11 != lineAnimator.rya12)
            canvas.drawLine(lineAnimator.rxa11, lineAnimator.rya11 + 100, lineAnimator.rxa12, lineAnimator.rya12 + 100, paint);
        if (lineAnimator.rxa21 != lineAnimator.rxa22 && lineAnimator.rya21 != lineAnimator.rya22)
            canvas.drawLine(lineAnimator.rxa21, lineAnimator.rya21 + 100, lineAnimator.rxa22, lineAnimator.rya22 + 100, paint);
        if (lineAnimator.rxa31 != lineAnimator.rxa32 && lineAnimator.rya31 != lineAnimator.rya32)
            canvas.drawLine(lineAnimator.rxa31, lineAnimator.rya31 + 100, lineAnimator.rxa32, lineAnimator.rya32 + 100, paint);
    }
}
