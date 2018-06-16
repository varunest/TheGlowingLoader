package com.example.varun.loader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.logging.Handler;

public class GlowingLoader extends View {
    private Paint rp;
    private Paint wp;
    private float x1, x2, x3, x4, y1, y2, y3, y4;
    private float xa11, xa12, ya11, ya12, xa21, ya21, xa22, ya22, xa31, ya31, xa32, ya32;
    private ValueAnimator w11, w12, w21, w22, w31, w32;

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
        rp = new Paint(Paint.ANTI_ALIAS_FLAG);
        rp.setColor(Color.parseColor("#EF5350"));
        rp.setStrokeWidth(25);
        rp.setStrokeCap(Paint.Cap.ROUND);

        wp = new Paint(Paint.ANTI_ALIAS_FLAG);
        wp.setColor(Color.WHITE);
        wp.setStrokeWidth(25);
        wp.setStrokeCap(Paint.Cap.ROUND);
    }

    private void startAnimation() {
        Log.d("TAG", String.valueOf(x1));
        Log.d("TAG", String.valueOf(x2));

        PropertyValuesHolder px1 = PropertyValuesHolder.ofFloat("X", x1, x2);
        PropertyValuesHolder py1 = PropertyValuesHolder.ofFloat("Y", y1, y2);
        PropertyValuesHolder px2 = PropertyValuesHolder.ofFloat("X", x2, x3);
        PropertyValuesHolder py2 = PropertyValuesHolder.ofFloat("Y", y2, y3);
        PropertyValuesHolder px3 = PropertyValuesHolder.ofFloat("X", x3, x4);
        PropertyValuesHolder py3 = PropertyValuesHolder.ofFloat("Y", y3, y4);

        w11 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        w11.setInterpolator(new AccelerateInterpolator(1.8f));
        w11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa12 = (float) valueAnimator.getAnimatedValue("X");
                ya12 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        w12 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        w12.setInterpolator(new AccelerateInterpolator(1.8f));
        w12.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa11 = (float) valueAnimator.getAnimatedValue("X");
                ya11 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        w21 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        w21.setInterpolator(new AccelerateInterpolator(1.8f));
        w21.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa22 = (float) valueAnimator.getAnimatedValue("X");
                ya22 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        w22 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        w22.setInterpolator(new AccelerateInterpolator(1.8f));
        w22.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa21 = (float) valueAnimator.getAnimatedValue("X");
                ya21 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        w31 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        w31.setInterpolator(new DecelerateInterpolator(1.8f));
        w31.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa32 = (float) valueAnimator.getAnimatedValue("X");
                ya32 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        w32 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        w32.setInterpolator(new DecelerateInterpolator(1.8f));
        w32.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                xa31 = (float) valueAnimator.getAnimatedValue("X");
                ya31 = (float) valueAnimator.getAnimatedValue("Y");
                invalidate();
            }
        });

        int tw11 = 416;
        int tw21 = 133;
        int tw31 = 500;
        w11.setDuration(tw11);
        w21.setDuration(tw21);
        w31.setDuration(tw31);

        w21.setStartDelay(tw11);
        w31.setStartDelay(tw11 + tw21);

        int tw12 = 450;
        int tw22 = 150;
        int tw32 = 433;
        w12.setDuration(tw12);
        w22.setDuration(tw22);
        w32.setDuration(tw32);


        w22.setStartDelay(tw21);
        w32.setStartDelay(tw21 + tw22);

        w11.start();
        w12.start();
        w21.start();
        w22.start();
        w31.start();
        w32.start();

        w32.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        initCoordinates();
                        startAnimation();
                    }
                };
                GlowingLoader.this.postDelayed(r, 1000);
            }
        });

    }

    private void initCoordinates() {
        xa11 = xa12 = x1;
        xa21 = xa22 = x2;
        ya11 = ya12 = y1;
        ya21 = ya22 = y2;
        xa31 = xa32 = x3;
        ya31 = ya32 = y3;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w < h) {
            x1 = .1f * w;
            y1 = h / 2 + .15f * w;
            x2 = .30f * w;
            y2 = h / 2 + -.15f * w;
            x3 = .70f * w;
            y3 = h / 2 + .30f * w;
            x4 = .9f * w;
            y4 = h / 2 - .03f * w;
        } else {

        }

        initCoordinates();
        startAnimation();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(xa11, ya11, xa12, ya12, wp);
        canvas.drawLine(xa21, ya21, xa22, ya22, wp);
        canvas.drawLine(xa31, ya31, xa32, ya32, wp);
    }
}
