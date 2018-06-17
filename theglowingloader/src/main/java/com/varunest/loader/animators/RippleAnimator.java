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
import com.varunest.loader.Constants;
import com.varunest.loader.particles.Circle;
import com.varunest.loader.particles.ParticleView;
import com.varunest.loader.particles.Triangle;

public class RippleAnimator {
    private final static int PARTICLE_TYPE_TRIANGLE = 0;
    private final static int PARTICLE_TYPE_CIRCLE = 1;

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

    public void startParticleAnimation(float degree, int particleType, int particleColor) {
        float length = 2 * circleMaxRadius;
        float x = (float) (cX + length * Math.cos(Math.toRadians(degree)));
        float y = (float) (cY - length * Math.sin(Math.toRadians(degree)));
        final ParticleView particleView;
        switch (particleType) {
            case PARTICLE_TYPE_CIRCLE:
                particleView = new Circle(view.getContext());
                break;
            case PARTICLE_TYPE_TRIANGLE:
                particleView = new Triangle(view.getContext());
                break;
            default:
                particleView = new Circle(view.getContext());
        }
        int size = (int) (circleMaxRadius * .3f);
        particleView.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        particleView.setPaintColor(particleColor);
        view.addView(particleView);

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
                particleView.setX(x);
                particleView.setY(y);
                particleView.setRotation(rotation);
                particleView.setScaleX(scale);
                particleView.setScaleY(scale);
                particleView.setAlpha(alpha);
            }
        });
        va.setInterpolator(new AccelerateInterpolator(.4f));
        va.setDuration(550);
        va.start();

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.removeView(particleView);
            }
        });
    }

    public void start(final Callback callback, float degree1, float degree2, float degree3) {
        startCircleMajor(callback);
        startCircleMinor(callback);
        if (degree1 != Constants.INVALID_DEG)
            startParticleAnimation(degree1, PARTICLE_TYPE_TRIANGLE, configuration.getParticle1Color());
        if (degree2 != Constants.INVALID_DEG)
            startParticleAnimation(degree2, PARTICLE_TYPE_CIRCLE, configuration.getParticle2Color());
        if (degree3 != Constants.INVALID_DEG)
            startParticleAnimation(degree3, PARTICLE_TYPE_TRIANGLE, configuration.getParticle3Color());
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setMaskFilter(null);
        paint.setStyle(Paint.Style.STROKE);

        paint.setColor(configuration.getRippleColor());
        paint.setStrokeWidth(circleStroke);
        paint.setAlpha((int) (255 * circleAlpha));
        canvas.drawCircle(cX, cY, circleRadius, paint);

        if (!configuration.isDisableShadows()) {
            paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
            paint.setStrokeWidth(.28f * circleRadius);
            paint.setAlpha((int) (255 * circleAlpha * configuration.getShadowOpacity()));
            canvas.drawCircle(cX, cY + 100, circleRadius, paint);
        }


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
