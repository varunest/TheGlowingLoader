package com.varunest.loader.animators;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.varunest.loader.Configuration;

public class LineAnimator {
    private View view;
    private Configuration configuration;
    private float x1, x2, x3, x4, y1, y2, y3, y4;
    private float wxa11, wxa12, wya11, wya12, wxa21, wya21, wxa22, wya22, wxa31, wya31, wxa32, wya32;
    private float rxa11, rxa12, rya11, rya12, rxa21, rya21, rxa22, rya22, rxa31, rya31, rxa32, rya32;

    public LineAnimator(View view, Configuration configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    public void updateEdgeCoordinates(float x1, float x2, float x3, float x4, float y1, float y2, float y3, float y4) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
    }

    private void initLineCoordinates(boolean forward) {
        if (forward) {
            wxa11 = wxa12 = rxa11 = rxa12 = x1;
            wxa21 = wxa22 = rxa21 = rxa22 = x2;
            wya11 = wya12 = rya11 = rya12 = y1;
            wya21 = wya22 = rya21 = rya22 = y2;
            wxa31 = wxa32 = rxa31 = rxa32 = x3;
            wya31 = wya32 = rya31 = rya32 = y3;
        } else {
            wxa11 = wxa12 = rxa11 = rxa12 = x2;
            wxa21 = wxa22 = rxa21 = rxa22 = x3;
            wya11 = wya12 = rya11 = rya12 = y2;
            wya21 = wya22 = rya21 = rya22 = y3;
            wxa31 = wxa32 = rxa31 = rxa32 = x4;
            wya31 = wya32 = rya31 = rya32 = y4;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setMaskFilter(null);
        paint.setStrokeWidth(configuration.getLineStrokeWidth());

        paint.setColor(configuration.getLine1Color());
        if (wxa11 != wxa12 && wya11 != wya12)
            canvas.drawLine(wxa11, wya11, wxa12, wya12, paint);
        if (wxa21 != wxa22 && wya21 != wya22)
            canvas.drawLine(wxa21, wya21, wxa22, wya22, paint);
        if (wxa31 != wxa32 && wya31 != wya32)
            canvas.drawLine(wxa31, wya31, wxa32, wya32, paint);

        paint.setColor(configuration.getLine2Color());
        if (rxa11 != rxa12 && rya11 != rya12)
            canvas.drawLine(rxa11, rya11, rxa12, rya12, paint);
        if (rxa21 != rxa22 && rya21 != rya22)
            canvas.drawLine(rxa21, rya21, rxa22, rya22, paint);
        if (rxa31 != rxa32 && rya31 != rya32)
            canvas.drawLine(rxa31, rya31, rxa32, rya32, paint);


        if (!configuration.isDisableShadows()) {
            paint.setMaskFilter(new BlurMaskFilter(70, BlurMaskFilter.Blur.NORMAL));
            paint.setStrokeWidth(2.666f * configuration.getLineStrokeWidth());
            paint.setColor(configuration.getLine1Color());
            paint.setAlpha((int) (255 * configuration.getShadowOpacity()));
            if (wxa11 != wxa12 && wya11 != wya12)
                canvas.drawLine(wxa11, wya11 + 100, wxa12, wya12 + 100, paint);
            if (wxa21 != wxa22 && wya21 != wya22)
                canvas.drawLine(wxa21, wya21 + 100, wxa22, wya22 + 100, paint);
            if (wxa31 != wxa32 && wya31 != wya32)
                canvas.drawLine(wxa31, wya31 + 100, wxa32, wya32 + 100, paint);

            paint.setColor(configuration.getLine2Color());
            paint.setAlpha((int) (255 * configuration.getShadowOpacity()));
            if (rxa11 != rxa12 && rya11 != rya12)
                canvas.drawLine(rxa11, rya11 + 100, rxa12, rya12 + 100, paint);
            if (rxa21 != rxa22 && rya21 != rya22)
                canvas.drawLine(rxa21, rya21 + 100, rxa22, rya22 + 100, paint);
            if (rxa31 != rxa32 && rya31 != rya32)
                canvas.drawLine(rxa31, rya31 + 100, rxa32, rya32 + 100, paint);
        }
    }

    public void start(final Callback callback) {
        playForwardAnimation(new LocalCallback() {
            @Override
            public void onValueUpdated() {
                callback.onValueUpdated();
            }

            @Override
            public void startFirstCircleAnimation(float x, float y) {
                callback.startFirstCircleAnimation(x, y);
            }

            @Override
            public void startSecondCircleAnimation(float x, float y) {
                callback.startSecondCircleAnimation(x, y);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void playForwardAnimation(final LocalCallback callback) {
        playWhiteLineForward(new LocalCallback() {
            @Override
            public void onValueUpdated() {
                callback.onValueUpdated();
            }

            @Override
            public void startFirstCircleAnimation(float x, float y) {
                callback.startFirstCircleAnimation(x, y);
            }

            @Override
            public void startSecondCircleAnimation(float x, float y) {
                callback.startSecondCircleAnimation(x, y);
            }


            @Override
            public void onComplete() {

            }
        });

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                playRedLineForward(new LocalCallback() {
                    @Override
                    public void onValueUpdated() {
                        callback.onValueUpdated();
                    }

                    @Override
                    public void startFirstCircleAnimation(float x, float y) {
                        callback.startFirstCircleAnimation(x, y);
                    }

                    @Override
                    public void startSecondCircleAnimation(float x, float y) {
                        callback.startSecondCircleAnimation(x, y);
                    }

                    @Override
                    public void onComplete() {
                        playBackwardAnimation(callback);
                    }
                });
            }
        }, 183);
    }

    private void playBackwardAnimation(final LocalCallback callback) {
        playWhiteLineBackward(new LocalCallback() {
            @Override
            public void onValueUpdated() {
                callback.onValueUpdated();
            }

            @Override
            public void startFirstCircleAnimation(float x, float y) {
                callback.startFirstCircleAnimation(x, y);
            }

            @Override
            public void startSecondCircleAnimation(float x, float y) {
                callback.startSecondCircleAnimation(x, y);
            }

            @Override
            public void onComplete() {

            }
        });

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                playRedLineBackward(new LocalCallback() {
                    @Override
                    public void onValueUpdated() {
                        callback.onValueUpdated();
                    }

                    @Override
                    public void startFirstCircleAnimation(float x, float y) {
                        callback.startFirstCircleAnimation(x, y);
                    }

                    @Override
                    public void startSecondCircleAnimation(float x, float y) {
                        callback.startSecondCircleAnimation(x, y);
                    }

                    @Override
                    public void onComplete() {
                        playForwardAnimation(callback);
                    }
                });
            }
        }, 183);
    }

    private void playWhiteLineForward(final LocalCallback callback) {
        initLineCoordinates(true);
        ValueAnimator va11, va12, va21, va22, va31, va32;
        PropertyValuesHolder px1 = PropertyValuesHolder.ofFloat("X", x1, x2);
        PropertyValuesHolder py1 = PropertyValuesHolder.ofFloat("Y", y1, y2);
        PropertyValuesHolder px2 = PropertyValuesHolder.ofFloat("X", x2, x3);
        PropertyValuesHolder py2 = PropertyValuesHolder.ofFloat("Y", y2, y3);
        PropertyValuesHolder px3 = PropertyValuesHolder.ofFloat("X", x3, x4);
        PropertyValuesHolder py3 = PropertyValuesHolder.ofFloat("Y", y3, y4);

        va11 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va11.setInterpolator(new AccelerateInterpolator(1.8f));
        va11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa12 = (float) valueAnimator.getAnimatedValue("X");
                wya12 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va12 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va12.setInterpolator(new AccelerateInterpolator(1.8f));
        va12.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa11 = (float) valueAnimator.getAnimatedValue("X");
                wya11 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });


        va21 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va21.setInterpolator(new AccelerateInterpolator(1.8f));
        va21.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa22 = (float) valueAnimator.getAnimatedValue("X");
                wya22 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va22 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va22.setInterpolator(new AccelerateInterpolator(1.8f));
        va22.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa21 = (float) valueAnimator.getAnimatedValue("X");
                wya21 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va31 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va31.setInterpolator(new DecelerateInterpolator(1.8f));
        va31.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa32 = (float) valueAnimator.getAnimatedValue("X");
                wya32 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va32 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va32.setInterpolator(new DecelerateInterpolator(1.8f));
        va32.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa31 = (float) valueAnimator.getAnimatedValue("X");
                wya31 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        int tw11 = 450;
        int tw21 = 143;
        int tw31 = 510;
        va11.setDuration(tw11);
        va21.setDuration(tw21);
        va31.setDuration(tw31);

        va21.setStartDelay(tw11);
        va31.setStartDelay(tw11 + tw21);

        int tw12 = 510;
        int tw22 = 165;
        int tw32 = 433;
        va12.setDuration(tw12);
        va22.setDuration(tw22);
        va32.setDuration(tw32);


        va22.setStartDelay(tw12);
        va32.setStartDelay(tw12 + tw22);

        va11.start();
        va12.start();
        va21.start();
        va22.start();
        va31.start();
        va32.start();

        va12.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.startFirstCircleAnimation(x2, y2);
            }
        });

        va22.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.startSecondCircleAnimation(x3, y3);
            }
        });

        va32.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.onComplete();
            }
        });
    }

    private void playWhiteLineBackward(final LocalCallback callback) {
        initLineCoordinates(false);
        ValueAnimator va11, va12, va21, va22, va31, va32;
        PropertyValuesHolder px1 = PropertyValuesHolder.ofFloat("X", x4, x3);
        PropertyValuesHolder py1 = PropertyValuesHolder.ofFloat("Y", y4, y3);
        PropertyValuesHolder px2 = PropertyValuesHolder.ofFloat("X", x3, x2);
        PropertyValuesHolder py2 = PropertyValuesHolder.ofFloat("Y", y3, y2);
        PropertyValuesHolder px3 = PropertyValuesHolder.ofFloat("X", x2, x1);
        PropertyValuesHolder py3 = PropertyValuesHolder.ofFloat("Y", y2, y1);

        va11 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va11.setInterpolator(new AccelerateInterpolator(1.8f));
        va11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa32 = (float) valueAnimator.getAnimatedValue("X");
                wya32 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va12 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va12.setInterpolator(new AccelerateInterpolator(1.8f));
        va12.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa31 = (float) valueAnimator.getAnimatedValue("X");
                wya31 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va21 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va21.setInterpolator(new AccelerateInterpolator(1.8f));
        va21.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa22 = (float) valueAnimator.getAnimatedValue("X");
                wya22 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va22 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va22.setInterpolator(new AccelerateInterpolator(1.8f));
        va22.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa21 = (float) valueAnimator.getAnimatedValue("X");
                wya21 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va31 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va31.setInterpolator(new DecelerateInterpolator(1.8f));
        va31.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa12 = (float) valueAnimator.getAnimatedValue("X");
                wya12 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va32 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va32.setInterpolator(new DecelerateInterpolator(1.8f));
        va32.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                wxa11 = (float) valueAnimator.getAnimatedValue("X");
                wya11 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        int tw11 = 450;
        int tw21 = 143;
        int tw31 = 510;
        va11.setDuration(tw11);
        va21.setDuration(tw21);
        va31.setDuration(tw31);

        va21.setStartDelay(tw11);
        va31.setStartDelay(tw11 + tw21);

        int tw12 = 510;
        int tw22 = 165;
        int tw32 = 433;
        va12.setDuration(tw12);
        va22.setDuration(tw22);
        va32.setDuration(tw32);


        va22.setStartDelay(tw12);
        va32.setStartDelay(tw12 + tw22);

        va11.start();
        va21.start();
        va31.start();

        va12.start();
        va22.start();
        va32.start();


        va12.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.startSecondCircleAnimation(x3, y3);
            }
        });

        va22.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.startFirstCircleAnimation(x2, y2);
            }
        });

        va32.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.onComplete();
            }
        });
    }

    private void playRedLineForward(final LocalCallback callback) {
        initLineCoordinates(true);
        ValueAnimator va11, va12, va21, va22, va31, va32;
        PropertyValuesHolder px1 = PropertyValuesHolder.ofFloat("X", x1, x2);
        PropertyValuesHolder py1 = PropertyValuesHolder.ofFloat("Y", y1, y2);
        PropertyValuesHolder px2 = PropertyValuesHolder.ofFloat("X", x2, x3);
        PropertyValuesHolder py2 = PropertyValuesHolder.ofFloat("Y", y2, y3);
        PropertyValuesHolder px3 = PropertyValuesHolder.ofFloat("X", x3, x4);
        PropertyValuesHolder py3 = PropertyValuesHolder.ofFloat("Y", y3, y4);

        va11 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va11.setInterpolator(new AccelerateInterpolator(1.8f));
        va11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa12 = (float) valueAnimator.getAnimatedValue("X");
                rya12 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va12 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va12.setInterpolator(new AccelerateInterpolator(1.8f));
        va12.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa11 = (float) valueAnimator.getAnimatedValue("X");
                rya11 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va21 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va21.setInterpolator(new AccelerateInterpolator(1.8f));
        va21.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa22 = (float) valueAnimator.getAnimatedValue("X");
                rya22 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va22 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va22.setInterpolator(new AccelerateInterpolator(1.8f));
        va22.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa21 = (float) valueAnimator.getAnimatedValue("X");
                rya21 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va31 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va31.setInterpolator(new DecelerateInterpolator(1.8f));
        va31.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa32 = (float) valueAnimator.getAnimatedValue("X");
                rya32 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va32 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va32.setInterpolator(new DecelerateInterpolator(1.8f));
        va32.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa31 = (float) valueAnimator.getAnimatedValue("X");
                rya31 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });


        int tw11 = 450;
        int tw21 = 143;
        int tw31 = 510;
        va11.setDuration(tw11);
        va21.setDuration(tw21);
        va31.setDuration(tw31);

        va21.setStartDelay(tw11);
        va31.setStartDelay(tw11 + tw21);

        int tw12 = 510;
        int tw22 = 165;
        int tw32 = 433;
        va12.setDuration(tw12);
        va22.setDuration(tw22);
        va32.setDuration(tw32);


        va22.setStartDelay(tw12);
        va32.setStartDelay(tw12 + tw22);

        va11.start();
        va12.start();
        va21.start();
        va22.start();
        va31.start();
        va32.start();

        va32.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.onComplete();
            }
        });
    }

    private void playRedLineBackward(final LocalCallback callback) {
        initLineCoordinates(false);
        ValueAnimator va11, va12, va21, va22, va31, va32;
        PropertyValuesHolder px1 = PropertyValuesHolder.ofFloat("X", x4, x3);
        PropertyValuesHolder py1 = PropertyValuesHolder.ofFloat("Y", y4, y3);
        PropertyValuesHolder px2 = PropertyValuesHolder.ofFloat("X", x3, x2);
        PropertyValuesHolder py2 = PropertyValuesHolder.ofFloat("Y", y3, y2);
        PropertyValuesHolder px3 = PropertyValuesHolder.ofFloat("X", x2, x1);
        PropertyValuesHolder py3 = PropertyValuesHolder.ofFloat("Y", y2, y1);

        va11 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va11.setInterpolator(new AccelerateInterpolator(1.8f));
        va11.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa32 = (float) valueAnimator.getAnimatedValue("X");
                rya32 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va12 = ValueAnimator.ofPropertyValuesHolder(px1, py1);
        va12.setInterpolator(new AccelerateInterpolator(1.8f));
        va12.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa31 = (float) valueAnimator.getAnimatedValue("X");
                rya31 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va21 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va21.setInterpolator(new AccelerateInterpolator(1.8f));
        va21.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa22 = (float) valueAnimator.getAnimatedValue("X");
                rya22 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va22 = ValueAnimator.ofPropertyValuesHolder(px2, py2);
        va22.setInterpolator(new AccelerateInterpolator(1.8f));
        va22.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa21 = (float) valueAnimator.getAnimatedValue("X");
                rya21 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va31 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va31.setInterpolator(new DecelerateInterpolator(1.8f));
        va31.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa12 = (float) valueAnimator.getAnimatedValue("X");
                rya12 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        va32 = ValueAnimator.ofPropertyValuesHolder(px3, py3);
        va32.setInterpolator(new DecelerateInterpolator(1.8f));
        va32.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rxa11 = (float) valueAnimator.getAnimatedValue("X");
                rya11 = (float) valueAnimator.getAnimatedValue("Y");
                callback.onValueUpdated();
            }
        });

        int tw11 = 450;
        int tw21 = 143;
        int tw31 = 510;
        va11.setDuration(tw11);
        va21.setDuration(tw21);
        va31.setDuration(tw31);

        va21.setStartDelay(tw11);
        va31.setStartDelay(tw11 + tw21);

        int tw12 = 510;
        int tw22 = 165;
        int tw32 = 433;
        va12.setDuration(tw12);
        va22.setDuration(tw22);
        va32.setDuration(tw32);


        va22.setStartDelay(tw12);
        va32.setStartDelay(tw12 + tw22);

        va11.start();
        va12.start();
        va21.start();
        va22.start();
        va31.start();
        va32.start();

        va32.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                callback.onComplete();
            }
        });
    }

    private interface LocalCallback {
        void onValueUpdated();

        void startFirstCircleAnimation(float x, float y);

        void startSecondCircleAnimation(float x, float y);

        void onComplete();
    }

    public interface Callback {
        void onValueUpdated();

        void startFirstCircleAnimation(float x, float y);

        void startSecondCircleAnimation(float x, float y);
    }
}
