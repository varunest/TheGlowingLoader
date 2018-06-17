package com.varunest.loader.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.varunest.loader.Configuration;
import com.varunest.loader.particles.Circle;
import com.varunest.loader.particles.Triangle;

public class RippleAnimator {
    private float circleMaxRadius;
    private float circleRadius, circleRadius2;
    private int circleStroke, circleStroke2;
    private float circleAlpha, circleAlpha2;
    private float cX, cY;
    private FrameLayout view;
    private Configuration configuration;


    public RippleAnimator(FrameLayout v, Configuration configuration) {
        view = v;
        this.configuration = configuration;
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
        PropertyValuesHolder sp = PropertyValuesHolder.ofInt("stroke", (int) (circleMaxRadius * .15f), 0);

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
        PropertyValuesHolder sp2 = PropertyValuesHolder.ofInt("stroke", (int) (circleMaxRadius * .06f), 0);

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
        va2.setDuration(380);
        va2.start();
    }

    // TODO: Fix logic for drawing triangle.
    public void startParticleOne(int degree) {
        float length = 2 * circleMaxRadius;
        float x = (float) (cX + (length) / Math.sqrt(2));
        float y = (float) (cY - (length) / Math.sqrt(2));

        final Triangle triangle = new Triangle(view.getContext());
        int size = (int) (circleMaxRadius * .3f);
        triangle.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        triangle.setPaintColor(configuration.getParticle1Color());
        view.addView(triangle);

        PropertyValuesHolder sP = PropertyValuesHolder.ofFloat("scale", 0, 1);
        PropertyValuesHolder rP = PropertyValuesHolder.ofFloat("rotation", 0, 360);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, sP, aP, rP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue("x");
                float y = (float) animation.getAnimatedValue("y");
                float scale = (float) animation.getAnimatedValue("scale");
                float alpha = (float) animation.getAnimatedValue("alpha");
                float rotation = (float) animation.getAnimatedValue("rotation");
                triangle.setX(x);
                triangle.setY(y);
                triangle.setRotation(rotation);
                triangle.setScaleX(scale);
                triangle.setScaleY(scale);
                triangle.setAlpha(alpha);
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.removeView(triangle);
            }
        });
    }

    // TODO: Fix logic for drawing triangle.
    public void startParticleTwo(final Callback callback) {
        float length = 2 * circleMaxRadius;
        float x = cX;
        float y = cY + (length);

        final Triangle triangle = new Triangle(view.getContext());
        int size = (int) (circleMaxRadius * .3f);
        triangle.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        triangle.setPaintColor(configuration.getParticle2Color());
        view.addView(triangle);

        PropertyValuesHolder sP = PropertyValuesHolder.ofFloat("scale", 0, 1);
        PropertyValuesHolder rP = PropertyValuesHolder.ofFloat("rotation", 0, 360);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, sP, aP, rP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue("x");
                float y = (float) animation.getAnimatedValue("y");
                float scale = (float) animation.getAnimatedValue("scale");
                float alpha = (float) animation.getAnimatedValue("alpha");
                float rotation = (float) animation.getAnimatedValue("rotation");
                triangle.setX(x);
                triangle.setY(y);
                triangle.setRotation(rotation);
                triangle.setScaleX(scale);
                triangle.setScaleY(scale);
                triangle.setAlpha(alpha);
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.removeView(triangle);
            }
        });
    }

    public void startParticleThree(final Callback callback) {
        float length = 2 * circleMaxRadius;
        float x = (float) (cX - (length) / Math.sqrt(2));
        float y = (float) (cY - (length) / Math.sqrt(2));

        final Circle circle = new Circle(view.getContext());
        int size = (int) (circleMaxRadius * .3f);
        circle.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        circle.setPaintColor(configuration.getParticle3Color());
        view.addView(circle);

        PropertyValuesHolder sP = PropertyValuesHolder.ofFloat("scale", 0, 1);
        PropertyValuesHolder aP = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder xP = PropertyValuesHolder.ofFloat("x", cX, x);
        PropertyValuesHolder yP = PropertyValuesHolder.ofFloat("y", cY, y);
        ValueAnimator va = ValueAnimator.ofPropertyValuesHolder(xP, yP, sP, aP);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue("x");
                float y = (float) animation.getAnimatedValue("y");
                float scale = (float) animation.getAnimatedValue("scale");
                float alpha = (float) animation.getAnimatedValue("alpha");
                circle.setX(x);
                circle.setY(y);
                circle.setScaleX(scale);
                circle.setScaleY(scale);
                circle.setAlpha(alpha);
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.removeView(circle);
            }
        });
    }

    public void start(final Callback callback) {
        startCircleMajor(callback);
        startCircleMinor(callback);
        startParticleOne(45);
        startParticleTwo(callback);
        startParticleThree(callback);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setMaskFilter(null);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(configuration.getRippleColor());
        paint.setStrokeWidth(circleStroke);
        paint.setAlpha((int) (255 * circleAlpha));
        canvas.drawCircle(cX, cY, circleRadius, paint);

        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

        paint.setStrokeWidth(.28f * circleRadius);
        paint.setAlpha((int) (255 * circleAlpha * .4));
        canvas.drawCircle(cX, cY + 100, circleRadius, paint);

        paint.setMaskFilter(null);
        paint.setColor(configuration.getRippleColor());
        paint.setStrokeWidth(circleStroke2);
        paint.setAlpha((int) (255 * circleAlpha2));
        canvas.drawCircle(cX, cY, circleRadius2, paint);
    }

    public interface Callback {
        void onValueUpdated();
    }
}
