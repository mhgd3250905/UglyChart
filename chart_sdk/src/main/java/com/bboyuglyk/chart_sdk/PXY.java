package com.bboyuglyk.chart_sdk;

public class PXY {
    public float x;
    public float y;

    public PXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PXY{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public  PXY add(PXY p) {
        return new PXY(x + p.x, y + p.y);
    }

    public PXY subduct(PXY p) {
        return new PXY(x - p.x, y - p.y);
    }

    public  PXY multiply(float factor) {
        return new PXY(x * factor, y * factor);
    }
}
