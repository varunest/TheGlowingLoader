package com.example.varun.loader;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

public class CircleAnimator {
    private float circleMaxRadius;
    private float circleRadius, circleRadius2;
    private int circleStroke, circleStroke2;
    private float circleAlpha, circleAlpha2;
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
        va.setInterpolator(new AccelerateInterpolator(.4f));
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

        PropertyValuesHolder rp2 = PropertyValuesHolder.ofFloat("radius", 0, circleMaxRadius * .60f);
        PropertyValuesHolder ap2 = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder sp2 = PropertyValuesHolder.ofInt("stroke", 10, 0);

        ValueAnimator va2 = ValueAnimator.ofPropertyValuesHolder(rp2, ap2, sp2);
        va2.setInterpolator(new AccelerateInterpolator(.4f));
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleRadius2 = (float) animation.getAnimatedValue("radius");
                circleAlpha2 = (float) animation.getAnimatedValue("alpha");
                circleStroke2 = (int) animation.getAnimatedValue("stroke");
                callback.onValueUpdated();
            }
        });
        va2.setDuration(400);
        va2.start();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setMaskFilter(null);

        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStrokeWidth(circleStroke);
        paint.setAlpha((int) (255 * circleAlpha));
        canvas.drawCircle(cX, cY, circleRadius, paint);


        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStrokeWidth(circleStroke2);
        paint.setAlpha((int) (255 * circleAlpha2));
        canvas.drawCircle(cX, cY, circleRadius2, paint);

    }

    public interface Callback {
        void onValueUpdated();
    }
}
