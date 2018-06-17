package com.varunest.loader;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.varunest.theglowingloader.R;


public class Configuration {
    private int line1Color;
    private int line2Color;
    private int rippleColor;
    private int particle1Color;
    private int particle2Color;
    private int particle3Color;
    private int lineStrokeWidth;

    public Configuration() {

    }

    public Configuration(Context context) {
        line1Color = ContextCompat.getColor(context, R.color.white);
        line2Color = ContextCompat.getColor(context, R.color.red);
        lineStrokeWidth = Constants.DEF_LINE_STROKE_WIDTH;
        rippleColor = ContextCompat.getColor(context, R.color.white);
        particle1Color = ContextCompat.getColor(context, R.color.yellow);
        particle2Color = ContextCompat.getColor(context, R.color.blue);
        particle3Color = ContextCompat.getColor(context, R.color.white);
    }

    public int getLine1Color() {
        return line1Color;
    }

    public void setLine1Color(int line1Color) {
        this.line1Color = line1Color;
    }

    public int getLine2Color() {
        return line2Color;
    }

    public void setLine2Color(int line2Color) {
        this.line2Color = line2Color;
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    public int getParticle1Color() {
        return particle1Color;
    }

    public void setParticle1Color(int particle1Color) {
        this.particle1Color = particle1Color;
    }

    public int getParticle2Color() {
        return particle2Color;
    }

    public void setParticle2Color(int particle2Color) {
        this.particle2Color = particle2Color;
    }

    public int getParticle3Color() {
        return particle3Color;
    }

    public void setParticle3Color(int particle3Color) {
        this.particle3Color = particle3Color;
    }

    public int getLineStrokeWidth() {
        return lineStrokeWidth;
    }

    public void setLineStrokeWidth(int lineStrokeWidth) {
        this.lineStrokeWidth = lineStrokeWidth;
    }
}
