package com.varunest.loader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.varunest.loader.animators.RippleAnimator;
import com.varunest.loader.animators.LineAnimator;
import com.varunest.theglowingloader.R;

public class TheGlowingLoader extends FrameLayout {
    private Paint paint;
    private LineAnimator lineAnimator;
    private RippleAnimator rippleAnimator1, rippleAnimator2;
    private Configuration configuration;

    public TheGlowingLoader(Context context) {
        super(context);
        init();
    }

    public TheGlowingLoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStuffFromXML(attrs);
        init();
    }

    public TheGlowingLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStuffFromXML(attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TheGlowingLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getStuffFromXML(attrs);
        init();
    }

    private void getStuffFromXML(AttributeSet attrs) {
        configuration = new Configuration();
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TheGlowingLoader);
        configuration.setLine1Color(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_line_1_color, R.color.white)));
        configuration.setLine2Color(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_line_2_color, R.color.red)));
        configuration.setRippleColor(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_ripple_color, R.color.white)));
        configuration.setParticle1Color(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_particle_1_color, R.color.yellow)));
        configuration.setParticle2Color(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_particle_2_color, R.color.white)));
        configuration.setParticle3Color(ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.TheGlowingLoader_theglowingloader_particle_3_color, R.color.blue)));
        configuration.setLineStrokeWidth(a.getInt(R.styleable.TheGlowingLoader_theglowingloader_line_stroke_width, Constants.DEF_LINE_STROKE_WIDTH));
        configuration.setDisableShadows(a.getBoolean(R.styleable.TheGlowingLoader_theglowingloader_disable_shadows, false));
        configuration.setDisableRipple(a.getBoolean(R.styleable.TheGlowingLoader_theglowingloader_disable_ripple, false));
        configuration.setShadowOpacity(a.getFloat(R.styleable.TheGlowingLoader_theglowingloader_shadow_opacity, .2f));
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private void init() {
        if (configuration == null) {
            configuration = new Configuration(getContext());
        }
        setWillNotDraw(false);
        rippleAnimator1 = new RippleAnimator(TheGlowingLoader.this, configuration);
        rippleAnimator2 = new RippleAnimator(TheGlowingLoader.this, configuration);
        lineAnimator = new LineAnimator(TheGlowingLoader.this, configuration);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void startAnimation() {
        lineAnimator.start(new LineAnimator.Callback() {
            @Override
            public void onValueUpdated() {
                invalidate();
            }

            @Override
            public void startFirstCircleAnimation(float x, float y) {
                if (!configuration.isDisableRipple()) {
                    rippleAnimator1.setCircleCenter(x, y);
                    rippleAnimator1.start(new RippleAnimator.Callback() {
                        @Override
                        public void onValueUpdated() {
                            invalidate();
                        }
                    }, 60, 150, 270);
                }
            }

            @Override
            public void startSecondCircleAnimation(float x, float y) {
                if (!configuration.isDisableRipple()) {
                    rippleAnimator2.setCircleCenter(x, y);
                    rippleAnimator2.start(new RippleAnimator.Callback() {
                        @Override
                        public void onValueUpdated() {
                            invalidate();
                        }
                    }, -60, 0, Constants.INVALID_DEG);
                }

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

        float circleMaxRadius = (x4 - x1) * .18f;
        rippleAnimator2.setCircleMaxRadius(circleMaxRadius);
        rippleAnimator1.setCircleMaxRadius(circleMaxRadius);
        lineAnimator.updateEdgeCoordinates(x1, x2, x3, x4, y1, y2, y3, y4);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rippleAnimator1.draw(canvas, paint);
        rippleAnimator2.draw(canvas, paint);
        lineAnimator.draw(canvas, paint);
    }
}
