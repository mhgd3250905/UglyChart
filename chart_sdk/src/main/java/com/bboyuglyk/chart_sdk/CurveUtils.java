package com.bboyuglyk.chart_sdk;

public class CurveUtils {

    /// <summary>
    /// Catmull-Rom 曲线插值
    /// </summary>
    /// <param name="p0"></param>
    /// <param name="p1"></param>
    /// <param name="p2"></param>
    /// <param name="p3"></param>
    /// <param name="t">0-1</param>
    /// <returns></returns>
    public static PXY CatmullRomPoint(PXY P0, PXY P1, PXY P2, PXY P3, float t) {
        float factor = 0.5f;
        PXY c0 = P1;
        PXY c1 = P2.subduct(P0).multiply(factor);
        PXY c2 = (P2.subduct(P1).multiply(3f))
                .subduct(P3.subduct(P1).multiply(factor))
                .subduct(P2.subduct(P0).multiply(2f * factor));
        PXY c3 = (P2.subduct(P1).multiply(-2f))
                .add(P3.subduct(P1).multiply(factor))
                .add(P2.subduct(P0).multiply(factor));

        PXY curvePoint = c3.multiply(t * t * t)
                .add(c2.multiply(t * t))
                .add(c1.multiply(t))
                .add(c0);
        return curvePoint;
    }
}
