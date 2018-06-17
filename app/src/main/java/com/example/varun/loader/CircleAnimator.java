package com.example.varun.loader;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.example.varun.MainActivity;

public class CircleAnimator {
    private float circleMaxRadius;
    private float circleRadius, circleRadius2;
    private int circleStroke, circleStroke2;
    private float circleAlpha, circleAlpha2;
    private float cX, cY;
    private View view;

    private float p1x, p2x, p3x, p1y, p2y, p3y;

    private float p1x2, p1x3, p1y2, p1y3, p1Alpha;
    private float p2x2, p2x3, p2y2, p2y3, p2Alpha;
    private float p3Radius, p3Alpha;


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

    public void startCircleMajor(final Callback callback) {
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
    }

    public void startCircleMinor(final Callback callback) {
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

    // TODO: Fix logic for drawing triangle.
    public void startParticleTwo(final Callback callback) {
        float length = 2 * circleMaxRadius;
        float x = cX;
        float y = cY + (length);

        PropertyValuesHolder sP = PropertyValuesHolder.ofFloat("side", 0, 200);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, sP, aP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p2x = (float) animation.getAnimatedValue("x");
                p2y = (float) animation.getAnimatedValue("y");
                float side = (float) animation.getAnimatedValue("side");
                p2x2 = (float) (p2x - side * Math.cos(30));
                p2y2 = (float) (p2y + side * Math.cos(30));
                p2x3 = (float) (p2x + side * Math.cos(30));
                p2y3 = p2y2;
                p2Alpha = (float) animation.getAnimatedValue("alpha");
                callback.onValueUpdated();
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();
    }

    public void startParticleThree(final Callback callback) {
        float length = 2 * circleMaxRadius;
        float x = (float) (cX - (length) / Math.sqrt(2));
        float y = (float) (cY - (length) / Math.sqrt(2));

        PropertyValuesHolder rP = PropertyValuesHolder.ofFloat("radius", 0, 20);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, rP, aP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p3x = (float) animation.getAnimatedValue("x");
                p3y = (float) animation.getAnimatedValue("y");
                p3Radius = (float) animation.getAnimatedValue("radius");
                p3Alpha = (float) animation.getAnimatedValue("alpha");
                callback.onValueUpdated();
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();
    }

    // TODO: Fix logic for drawing triangle.
    public void startParticleOne(final Callback callback) {
        float length = 2 * circleMaxRadius;
        float x = (float) (cX + (length) / Math.sqrt(2));
        float y = (float) (cY - (length) / Math.sqrt(2));

        PropertyValuesHolder sP = PropertyValuesHolder.ofFloat("side", 0, 40);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, sP, aP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                p1x = (float) animation.getAnimatedValue("x");
                p1y = (float) animation.getAnimatedValue("y");
                float side = (float) animation.getAnimatedValue("side");
                p1x2 = p1x - side;
                p1x3 = (float) (p1x - side * Math.sin(60));
                p1y3 = (float) (p1y - side * Math.cos(60));
                p1y2 = p1y3;
                p1Alpha = (float) animation.getAnimatedValue("alpha");
                callback.onValueUpdated();
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();
    }

    public void start(final Callback callback) {
        startCircleMajor(callback);
        startCircleMinor(callback);
        startParticleOne(callback);
        startParticleTwo(callback);
        startParticleThree(callback);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setMaskFilter(null);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStrokeWidth(circleStroke);
        paint.setAlpha((int) (255 * circleAlpha));
        canvas.drawCircle(cX, cY, circleRadius, paint);

        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        paint.setStrokeWidth(40);
        paint.setAlpha((int) (255 * circleAlpha * .4));
        canvas.drawCircle(cX, cY + 100, circleRadius, paint);

        paint.setMaskFilter(null);
        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStrokeWidth(circleStroke2);
        paint.setAlpha((int) (255 * circleAlpha2));
        canvas.drawCircle(cX, cY, circleRadius2, paint);

        paint.setColor(Color.parseColor(Constants.COLOR_YELLOW));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(Math.min((int) (255 * p1Alpha * 1.5f), 255));
        Path p1Path = new Path();
        p1Path.moveTo(p1x, p1y);
        p1Path.lineTo(p1x2, p1y2);
        p1Path.lineTo(p1x3, p1y3);
        p1Path.lineTo(p1x, p1y);
        p1Path.close();
        canvas.drawPath(p1Path, paint);

        paint.setColor(Color.parseColor(Constants.COLOR_BLUE));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(Math.min((int) (255 * p2Alpha * 1.5f), 255));
        Path p2Path = new Path();
        p2Path.moveTo(p2x, p2y);
        p2Path.lineTo(p2x2, p2y2);
        p2Path.lineTo(p2x3, p2y3);
        p2Path.lineTo(p2x, p2y);
        p2Path.close();
        canvas.drawPath(p2Path, paint);

        paint.setColor(Color.parseColor(Constants.COLOR_WHITE));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(Math.min((int) (255 * p3Alpha * 1.5f), 255));
        canvas.drawCircle(p3x, p3y, p3Radius, paint);
    }

    public interface Callback {
        void onValueUpdated();
    }
}
