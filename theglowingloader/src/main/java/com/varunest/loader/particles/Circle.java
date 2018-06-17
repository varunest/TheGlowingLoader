package com.varunest.loader.particles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class Circle extends ParticleView {
    private int w;
    private int h;
    private Paint paint;

    public Circle(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(w / 2, h / 2, w / 2, paint);
    }

    public void setPaintColor(int paintColor) {
        paint.setColor(paintColor);
        invalidate();
    }
}
