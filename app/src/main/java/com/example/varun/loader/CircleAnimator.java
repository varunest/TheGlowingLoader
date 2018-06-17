package com.example.varun.loader;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CircleAnimator {
    private float circleMaxRadius;
    private float circleRadius;
    private int circleStroke;
    private float circleAlpha;
    private float cX, cY;
    private View view;

    CircleAnimator(View v) {
        view = v;
    }

    public void setCircleMaxRadius(float r) {
        this.circleMaxRadius = r;
    }

    public void setCircleCenter(float x, float y) {
        cX = x;
        cY = y;
    }

    public void start(final Callback callback) {
        PropertyValuesHolder rp = PropertyValuesHolder.ofFloat("radius", 0, circleMaxRadius);
        PropertyValuesHolder ap = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder sp = PropertyValuesHolder.ofInt("stroke", 30, 0);

        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(rp, ap, sp);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleRadius = (float) animation.getAnimatedValue("radius");
                circleAlpha = (float) animation.getAnimatedValue("alpha");
                circleStroke = (int) animation.getAnimatedValue("stroke");
                callback.onValueUpdated();
            }
        });
        va.setDuration(450);
        va.start();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setMaskFilter(null);
        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStrokeWidth(circleStroke);
        paint.setAlpha((int) (255 * circleAlpha));

        canvas.drawCircle(cX, cY, circleRadius, paint);
    }

    public interface Callback {
        void onValueUpdated();
    }
}
