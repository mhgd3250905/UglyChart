package com.bboyuglyk.chart_sdk;


import static com.bboyuglyk.chart_sdk.DataType.bar;
import static com.bboyuglyk.chart_sdk.DataType.bitmap;
import static com.bboyuglyk.chart_sdk.DataType.range_bar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.bboyuglyk.chart_sdk.dataset.DataSet;
import com.bboyuglyk.chart_sdk.dataset.DataSetBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class MChart extends View {
    private static final String TAG = "MChart";

    private Paint lPaint;
    private Paint highLightPaint;
    private Paint gridPaint;
    private Paint tPaint;
    private Paint barTPaint;
    private Paint markerPaint;
    private TextPaint markerTPaint;
    private TextPaint markerTLPaint;
    private TextPaint markerTLUPaint;
    private float width = 0;
    private float height = 0;
    private BaseAxis xAxis = new BaseAxis();
    private BaseAxis yAxis = new BaseAxis();

    private BaseAxis y2Axis = new BaseAxis();
    private float topMargin = 20;
    private float bottomMargin = 50;
    private float leftMargin = 100;
    private float rightMargin = 40;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float left;
    private float right;
    private float top;
    private float bottom;
    private float realWidth;
    private float xLabelGap;
    private int maxXCountGap;
    private float realHeight;
    private float yLabelGap;
    private int maxYCountGap;
    private float y2LabelGap;
    private int maxY2CountGap;
    private int labelValue;

    public static final int STYLE_LIGHT = 0;
    public static final int STYLE_DARK = 1;

    private int style = STYLE_LIGHT;

    //notice:  是否绘制横向网格
    private boolean showGridHorizontal = true;
    //notice:  是否绘制纵向网格
    private boolean showGridVertical = true;


    //notice:  是否显示横向label
    private boolean showLabelHorizontal = true;
    //notice:  是否显示纵向label
    private boolean showLabelVertical = true;


    private Path borderPath;

    private boolean fullClickEnable = false;
    private float nearbyFilterRange;
    private DataType selectDataType;
    private LinkedList<Entry> selectEntries;
    private ConcurrentHashMap<String, Entry> nearestMap;
    private float maxMarkerWidth = 200;
    private String labelStr;
    private Rect labelRect = new Rect();
    private int labelRectHeight;
    private int labelRectWidth;

    public void setFullClickEnable(boolean fullClickEnable) {
        this.fullClickEnable = fullClickEnable;
    }

    public void setGridVisibility(boolean showGridHorizontal, boolean showGridVertical) {
        this.showGridHorizontal = showGridHorizontal;
        this.showGridVertical = showGridVertical;
//        if (!isWatch) {
//            this.showLabelHorizontal = showGridHorizontal;
//            this.showLabelVertical = showGridVertical;
//        }
    }

    public void setLabelVisibility(boolean showLabelHorizontal, boolean showLabelVertical) {
        this.showLabelHorizontal = showLabelHorizontal;
        this.showLabelVertical = showLabelVertical;
    }

    public void setMargin(int left, int top, int rifght, int bottom) {
        topMargin = top;
        bottomMargin = bottom;
        leftMargin = left;
        rightMargin = rifght;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    private float tempPointGap;


    //tip 保存各种类型的数据源
    private DataSetBox dataSetBox;

    //tip 触摸高亮对象
    private HighLighter highLighter;
    private BaseAxis tempXAxis = new BaseAxis();
    //    private BaseAxis curXAxis;
    private float lastPx_1;
    private float lastPx_2;
    private boolean isTranslateRunning;
    private boolean isZoomRunning;
    private int zoomMinRange = 20;

    private Handler handler;
    private Runnable cleanHighLightRunnbale;
    private StaticLayout textLayout;
    private RectF markerFrame;
    private RectF frame;
    private int value;
    private float x;
    private float y;
    private float y_2;
    private DataType type;
    private float periousX;
    private float pX;
    private float pY;
    private float pY2;

    private float curRange;
    private LinkedList<Entry> entries;
    private int range;
    private int newXMin;
    private int newXMax;
    private float translationX;
    private float newScaleGap;
    private float x1Range;
    private float x2Range;
    private int pointerCount;
    private RectF rectF;
    private float nextX;
    private int index;
    private Map.Entry<String, DataSet> nextDataSet;
    private Iterator<Map.Entry<DataType, LinkedList<Entry>>> nearByEntriesIterator;
    private Iterator<Map.Entry<String, DataSet>> dataSetIterator;
    private Map.Entry<DataType, LinkedList<Entry>> nextNearbyEntries;
    private Path path;
    private int zoomRange;
    private float lastLinePx;
    private float lastLinePy;
    private StringBuilder markerSb;
    private List<Pair<Integer, String>> markerTextArr;
    private List<Pair<Integer, String>> lastMarkerTextArr;
    private float lastMarkerPx;
    private float firstFillLinePX;
    private float firstFillLinePY;
    private float firstFillLinePY2;
    private float secondFillLinePX;
    private float secondFillLinePY;
    private float secondFillLinePY2;
    private boolean isHighLeft;
    private float[] midYs;
    private float lastBottom;
    private int lastXMidCount;
    private int lastYMidCount;
    private int lastY2MidCount;
    private int nearRange;
    private float nearPx;
    private boolean needShowHighlight;
    private boolean isInterceptTouchEvent = true;
    private float markerTTop;
    private float markerTBottom;
    private boolean isShowSgChart = true;
    private float nearByTempDistance = 1440;
    private Set<DataType> touchTargetTypes;
    private Set<DataType> touchExceptTypes;
    private String curMarkerText;
    private boolean isUnderLine;
    private float downX;
    private float downY;

    /**
     * notice 是否可以触摸
     */
    private boolean touchEnable = true;

    public void setTouchEnable(boolean touchEnable) {
        this.touchEnable = touchEnable;
    }

    /**
     * notice 初始化数据排序工具
     */
    private PriorityHelper priorityHelper = new PriorityHelper();

    /**
     * 设置数据集合优先级
     *
     * @param priorityHelper
     */
    public void setPriorityHelper(PriorityHelper priorityHelper) {
        this.priorityHelper = priorityHelper;
    }

    public MChart(Context context) {
        super(context);
        init();
    }

    public MChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        handler = new Handler(Looper.getMainLooper());
        cleanHighLightRunnbale = new Runnable() {
            @Override
            public void run() {
                needShowHighlight = false;
                highLighter.clear();
                invalidate();
            }
        };

        touchTargetTypes = new ArraySet<>();
        touchTargetTypes.add(bitmap);
        touchTargetTypes.add(bar);
        touchTargetTypes.add(range_bar);
//        touchTargetTypes.add(line_rect);

        touchExceptTypes = new ArraySet<>();
        touchExceptTypes.add(bitmap);
        touchExceptTypes.add(bar);
        touchExceptTypes.add(range_bar);
//        touchExceptTypes.add(line_rect);

        markerSb = new StringBuilder();

        markerTextArr = new ArrayList<>();

        highLighter = new HighLighter(-1, new HashMap<>());
        frame = new RectF();
        borderPath = new Path();
        markerFrame = new RectF();

        //notice 边框画笔
        lPaint = new Paint();
        lPaint.setAntiAlias(true);//设置抗锯齿
        lPaint.setStyle(Paint.Style.STROKE);//实心
        lPaint.setStrokeWidth(2);//线条粗细
        lPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_grid_line));//线条粗细

        highLightPaint = new Paint();
        highLightPaint.setAntiAlias(true);//设置抗锯齿
        highLightPaint.setStyle(Paint.Style.STROKE);//实心
        highLightPaint.setStrokeWidth(3);//线条粗细
        highLightPaint.setColor(Color.BLACK);//线条粗细

        //notice 边框画笔
        gridPaint = new Paint();
        gridPaint.setAntiAlias(true);//设置抗锯齿
        gridPaint.setStyle(Paint.Style.STROKE);//实心
        gridPaint.setStrokeWidth(2);//线条粗细
        gridPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_grid_line));//线条粗细


        //notice 文字画笔
        tPaint = new Paint();
        tPaint.setAntiAlias(true);
        tPaint.setStrokeWidth(5);
        tPaint.setColor(Color.BLACK);
        tPaint.setTextSize(dp2px(10));

        //notice 文字画笔
        barTPaint = new Paint();
        barTPaint.setAntiAlias(true);
        barTPaint.setStrokeWidth(8);
        barTPaint.setColor(Color.WHITE);
        barTPaint.setTextSize(dp2px(15));

        //notice Marker背景画笔
        markerPaint = new Paint();
        markerPaint.setAntiAlias(true);
        markerPaint.setStrokeWidth(5);
        markerPaint.setStyle(Paint.Style.FILL);//实心
        markerPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_main));
        markerPaint.setTextSize(dp2px(10));

        //notice Marker文字画笔
        markerTPaint = new TextPaint();
        markerTPaint.setAntiAlias(true);
        markerTPaint.setStrokeWidth(5);
        markerTPaint.setColor(Color.WHITE);
        markerTPaint.setTextSize(dp2px(8));

        //notice Marker文字画笔
        markerTLPaint = new TextPaint();
        markerTLPaint.setAntiAlias(true);
        markerTLPaint.setStrokeWidth(5);
        markerTLPaint.setColor(Color.WHITE);
        markerTLPaint.setTextSize(dp2px(15));

        //notice Marker文字画笔
        markerTLUPaint = new TextPaint();
        markerTLUPaint.setAntiAlias(true);
        markerTLUPaint.setStrokeWidth(5);
        markerTLUPaint.setColor(Color.WHITE);
        markerTLUPaint.setUnderlineText(true);
        markerTLUPaint.setTextSize(dp2px(15));

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = getWidth();
                height = getHeight();

                left = 0 + leftMargin;
                right = width - rightMargin;

                top = 0 + topMargin;
                bottom = height - bottomMargin;

                realWidth = width - leftMargin - rightMargin;
                xLabelGap = realWidth / (xAxis.getMidCount() + 1);
                maxXCountGap = (int) ((xAxis.getMax() - xAxis.getMin()) / (xAxis.getMidCount() + 1));

                realHeight = height - topMargin - bottomMargin;
                yLabelGap = realHeight / (yAxis.getMidCount() + 1);
                maxYCountGap = (int) ((yAxis.getMax() - yAxis.getMin()) / (yAxis.getMidCount() + 1));

                y2LabelGap = realHeight / (y2Axis.getMidCount() + 1);
                maxY2CountGap = (int) ((y2Axis.getMax() - y2Axis.getMin()) / (y2Axis.getMidCount() + 1));

            }
        });

        setDataSetBox(new DataSetBox(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -1 * Integer.compare(priorityHelper.getTagPriority(o1), priorityHelper.getTagPriority(o2));
            }
        }));
    }

    /**
     * tip 添加数据
     *
     * @param dataSet
     */
    public void addDataSet(DataSet dataSet) {
        dataSetBox.addDataSet(dataSet);
    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (fullClickEnable) {
            return super.onTouchEvent(event);
        }
        if (!touchEnable) {
            return false;
        }
        cleanHighLight(5000);
        if (isInterceptTouchEvent()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        try {
            x = event.getX();
            y = event.getY();
            pointerCount = event.getPointerCount();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "onTouchEvent ACTION_DOWN: (" + x + ", " + y + ")");

                    downX = event.getX();
                    downY = event.getY();

                    findNearbyEntries(x, y, touchTargetTypes, null, true);
                    invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
//                    Log.d(TAG, "onTouchEvent ACTION_POINTER_DOWN: (" + x + ", " + y + ") pointerCount= " + pointerCount);
                    if (pointerCount == 2) {

//                        curXAxis = tempXAxis.clone();
                        lastPx_1 = event.getX(event.getPointerId(0));
                        lastPx_2 = event.getX(event.getPointerId(1));

                        tempPointGap = Math.abs(event.getX(event.getPointerId(0)) - event.getX(event.getPointerId(1)));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
//                    Log.d(TAG, "onTouchEvent ACTION_MOVE: (" + x + ", " + y + ") pointerCount= " + pointerCount);
                    if (pointerCount == 1) {
                        if (event.getX() == downX && event.getY() == downY) {
                            findNearbyEntries(x, y, touchTargetTypes, null, false);
                            invalidate();
                        } else {
                            findNearbyEntries(x, y, null, touchExceptTypes, false);
                            invalidate();
                        }
                    } else if (pointerCount == 2) {
                        highLighter.clear();

                        x1Range = event.getX(event.getPointerId(0)) - lastPx_1;
                        x2Range = event.getX(event.getPointerId(1)) - lastPx_2;
//                    Log.d(TAG, "onTouchEvent: x1 range = " + x1Range + ", x2 range = " + x2Range);

                        if (x1Range * x2Range < 0) {
                            //notice 1.如果双指滑动方向 不一致 -> 放大或者缩小

                            if (isTranslateRunning) {
                                return true;
                            }

//                            if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 5) {


                            isZoomRunning = true;

                            newScaleGap = Math.abs(event.getX(event.getPointerId(0)) - event.getX(event.getPointerId(1)));
//                        Log.d(TAG, "[1]: newScaleGap= " + newScaleGap + ", tempPointGap= " + tempPointGap);


                            zoomRange = 0;
                            //这里设置缩放的速度
                            if (newScaleGap > tempPointGap) {
                                zoomRange = buildZoomRange();
                                if (tempXAxis.getMax() - tempXAxis.getMin() < (xAxis.getMax() - xAxis.getMin()) / 3) {
                                    return true;
                                }
                            } else if (newScaleGap < tempPointGap) {
                                zoomRange = -1 * buildZoomRange();
                            }

                            //根据锚点和比例计算用于绘图的坐标信息
                            calculateZoomChartInfo(zoomRange);

//                            }
                        } else {
                            //notice 双指滑动在同一个方向 -> 拖动坐标轴

                            if (isZoomRunning) {
                                return true;
                            }

                            translationX = Math.max(x1Range, x2Range);
                            calculateTranslationChartInfo(translationX);
                        }


                        lastPx_1 = event.getX(event.getPointerId(0));
                        lastPx_2 = event.getX(event.getPointerId(1));
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isTranslateRunning = false;
                    isZoomRunning = false;
                    break;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
//        return false;
    }


    /**
     * tip 构建滑动速度
     *
     * @return
     */
    private int buildTranslationSpeed() {
        if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 3) {
            return 10;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 5) {
            return 6;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 10) {
            return 3;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 15) {
            return 2;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 20) {
            return 1;
        }
        return 1;
    }


    /**
     * tip 构建缩放速度
     *
     * @return
     */
    private int buildZoomRange() {
        //根据当前缩放的比例，来决定放大缩小的速度
        if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 3) {
            return 10;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 5) {
            return 7;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 10) {
            return 5;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 15) {
            return 3;
        } else if (tempXAxis.getMax() - tempXAxis.getMin() > (xAxis.getMax() - xAxis.getMin()) / 20) {
            return 1;
        }
        return 1;
    }

    /**
     * tip 根据锚点和比例计算用于绘图的坐标信息:缩放
     */
    private void calculateZoomChartInfo(int zoomRange) {
        Log.d(TAG, "calculateZoomChartInfo() called with: zoomRange = [" + zoomRange + "]");

        newXMin = tempXAxis.getMin() + zoomRange;
        newXMax = tempXAxis.getMax() - zoomRange;

        if (newXMax - newXMin < zoomMinRange) {
            return;
        }

        if (newXMin < xAxis.getMin() && newXMax > xAxis.getMax()) {
            return;
        }

        if (newXMin < xAxis.getMin()) newXMin = xAxis.getMin();
        if (newXMax > xAxis.getMax()) newXMax = xAxis.getMax();


        tempXAxis.setMin(newXMin);
        tempXAxis.setMax(newXMax);

        maxXCountGap = (tempXAxis.getMax() - tempXAxis.getMin()) / (tempXAxis.getMidCount() + 1);

//        Log.d(TAG, "calculateZoomChartInfo_after: tempXAxis = " + tempXAxis);
    }

    /**
     * tip 根据锚点和比例计算用于绘图的坐标信息：平移
     */
    private void calculateTranslationChartInfo(float translationX) {
//        Log.d(TAG, "calculateTranslationChartInfo() called with: curXAxis = [" + curXAxis + "], translationX = [" + translationX + "]");
        //首先计算平移对应了坐标轴的平移量
        range = translationX > 0 ? buildTranslationSpeed() : -1 * buildTranslationSpeed();
        newXMin = tempXAxis.getMin() - range;
        newXMax = tempXAxis.getMax() - range;

        if (newXMin < xAxis.getMin()) return;
        if (newXMax > xAxis.getMax()) return;

        tempXAxis.setMin(newXMin);
        tempXAxis.setMax(newXMax);

        maxXCountGap = (tempXAxis.getMax() - tempXAxis.getMin()) / (tempXAxis.getMidCount() + 1);

    }


    /**
     * 寻找坐标附近的数据
     *
     * @param x
     * @param y
     * @param targetTypes
     * @param exceptTypes
     * @param touchDown
     */
    private void findNearbyEntries(float x, float y, Set<DataType> targetTypes, Set<DataType> exceptTypes, boolean touchDown) {
//        Log.d(TAG, "findNearbyEntries() called with: x = [" + x + "], y = [" + y + "], touchDown = [" + touchDown + "]");
        nearRange = 10000;
        nearPx = -1;
        highLighter.getNearEntriesMap().clear();
        dataSetIterator = dataSetBox.getDataSetMap().entrySet().iterator();
        while (dataSetIterator.hasNext()) {
            nextDataSet = dataSetIterator.next();
            type = nextDataSet.getValue().getType();

            if (exceptTypes == null || !exceptTypes.contains(type)) {

                if (targetTypes == null || targetTypes.contains(type)) {

                    if (nextDataSet.getValue().isShowMarker()) {
                        if (highLighter.getNearEntriesMap().containsKey(type)) {
                            entries = highLighter.getNearEntriesMap().get(type);
                        } else {
                            entries = new LinkedList<>();
                        }
                        if (!nextDataSet.getValue().getEntries().isEmpty()) {
                            switch (type) {
                                case line_fill:
                                case line_fill2:
                                    nearRange = 50;
                                    for (int i = 0; i < nextDataSet.getValue().getEntries().size(); i++) {
                                        if (i > 2) {
                                            if ((nextDataSet.getValue().getEntries().get(i - 1).getX() < tempXAxis.getMin() && nextDataSet.getValue().getEntries().get(i + 1).getX() < tempXAxis.getMin())
                                                    || (nextDataSet.getValue().getEntries().get(i).getX() > tempXAxis.getMax() && nextDataSet.getValue().getEntries().get(i - 1).getX() > tempXAxis.getMax())) {
                                                continue;
                                            }
                                        }

                                        pX = left + realWidth * (nextDataSet.getValue().getEntries().get(i).getX() - tempXAxis.getMin()) / (tempXAxis.getMax() - tempXAxis.getMin());
                                        if (!nextDataSet.getValue().isY2()) {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / yAxis.getMax();
                                        } else {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / y2Axis.getMax();
                                        }

                                        if (nextDataSet.getValue().getEntries().get(i).getData() == null) {
                                            continue;
                                        }

                                        if (Math.abs(pX - x) < nearRange) {
                                            if (i == 0) {
                                                if (Math.abs(pX - x) < nearRange) {
                                                    entries.add(Entry.getNullEntry());
                                                    entries.add(nextDataSet.getValue().getEntries().get(i));
                                                    nearPx = pX;
                                                }
                                                continue;
                                            } else {
                                                if (Math.abs(pX - x) < nearRange) {
//                                                    entries.add(nextDataSet.getValue().getEntries().get(i - 1));
                                                    entries.add(nextDataSet.getValue().getEntries().get(i));
                                                    nearPx = pX;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    highLighter.getNearEntriesMap().put(type, entries);
                                    break;
                                case bar:
                                    nearByTempDistance = realWidth;
                                    for (int i = 0; i < nextDataSet.getValue().getEntries().size(); i++) {
                                        if (nextDataSet.getValue().getEntries().get(i).getX() < tempXAxis.getMin() || nextDataSet.getValue().getEntries().get(i).getX() > tempXAxis.getMax()) {
                                            continue;
                                        }
                                        pX = left + realWidth * (nextDataSet.getValue().getEntries().get(i).getX() - tempXAxis.getMin()) / (tempXAxis.getMax() - tempXAxis.getMin());
                                        if (!nextDataSet.getValue().isY2()) {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / yAxis.getMax();
                                        } else {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / y2Axis.getMax();
                                        }

                                        curRange = realWidth / (tempXAxis.getMax() - tempXAxis.getMin());

//                                    Log.d(TAG, "accept: curRange = " + curRange + ", static range = " + dataSet.getFoundNearRange());

                                        if (targetTypes.contains(bitmap)) {
                                            if (Math.abs(pX - x) <= realWidth) {
                                                if (nearByTempDistance > Math.abs(pX - x)) {
                                                    entries.clear();
                                                    entries.add(nextDataSet.getValue().getEntries().get(i));
                                                    nearByTempDistance = Math.abs(pX - x);
                                                    if (Math.abs(pX - x) < nearRange) {
                                                        nearPx = pX;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (Math.abs(pX - x) <= nextDataSet.getValue().getFoundNearRange()) {
                                                entries.add(nextDataSet.getValue().getEntries().get(i));

                                                if (Math.abs(pX - x) < nearRange) {
                                                    nearPx = pX;
                                                }
                                            }
                                        }
                                    }
                                    highLighter.getNearEntriesMap().put(type, entries);
                                    break;
                                case point:
                                case line:
                                    nearByTempDistance = 20;
                                    for (int i = 0; i < nextDataSet.getValue().getEntries().size(); i++) {
                                        if (nextDataSet.getValue().getEntries().get(i).getX() < tempXAxis.getMin() || nextDataSet.getValue().getEntries().get(i).getX() > tempXAxis.getMax()) {
                                            continue;
                                        }

                                        if (nextDataSet.getValue().getEntries().get(i).getData() == null) {
                                            continue;
                                        }

//                                        if (nextDataSet.getValue().getEntries().get(i).getData()==null) {
//                                            continue;
//                                        }

                                        pX = left + realWidth * (nextDataSet.getValue().getEntries().get(i).getX() - tempXAxis.getMin()) / (tempXAxis.getMax() - tempXAxis.getMin());
                                        if (!nextDataSet.getValue().isY2()) {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / yAxis.getMax();
                                        } else {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / y2Axis.getMax();
                                        }


                                        curRange = realWidth / (tempXAxis.getMax() - tempXAxis.getMin());

                                        if (Math.abs(pX - x) <= realWidth) {
                                            if (nearByTempDistance > Math.abs(pX - x)) {
//                                                entries.clear();
                                                nextDataSet.getValue().getEntries().get(i).setNearGap(Math.abs(pX - x));
                                                entries.add(nextDataSet.getValue().getEntries().get(i));

//                                                nearByTempDistance = Math.abs(pX - x);
                                                if (Math.abs(pX - x) < nearRange) {
                                                    nearPx = pX;
                                                }
                                            }
                                        }

//                                        if (Math.abs(pX - x) <= Math.min(nextDataSet.getValue().getFoundNearRange(), curRange / 2)) {
//                                            entries.add(nextDataSet.getValue().getEntries().get(i));
//
//                                            if (Math.abs(pX - x) < nearRange) {
//                                                nearPx = pX;
//                                            }
//                                        }
                                    }

//                                    Log.d(TAG, "findNearbyEntries: " + type + " - " + nextDataSet.getValue().getTag());
                                    highLighter.getNearEntriesMap().put(type, entries);

                                    break;
                                case bitmap:
                                case range_bar:
                                    nearByTempDistance = realWidth;
                                    for (int i = 0; i < nextDataSet.getValue().getEntries().size(); i++) {
                                        if (nextDataSet.getValue().getEntries().get(i).getX() < tempXAxis.getMin() || nextDataSet.getValue().getEntries().get(i).getX() > tempXAxis.getMax()) {
                                            continue;
                                        }
                                        pX = left + realWidth * (nextDataSet.getValue().getEntries().get(i).getX() - tempXAxis.getMin()) / (tempXAxis.getMax() - tempXAxis.getMin());
                                        if (!nextDataSet.getValue().isY2()) {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / yAxis.getMax();
                                        } else {
                                            pY = bottom - nextDataSet.getValue().getEntries().get(i).getY() * realHeight / y2Axis.getMax();
                                        }

                                        if (targetTypes.contains(bitmap)) {
                                            if (Math.abs(pX - x) <= realWidth) {
                                                if (nearByTempDistance > Math.abs(pX - x)) {
                                                    entries.clear();
                                                    entries.add(nextDataSet.getValue().getEntries().get(i));
                                                    nearByTempDistance = Math.abs(pX - x);
                                                    if (Math.abs(pX - x) < nearRange) {
                                                        nearPx = pX;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (Math.abs(pX - x) <= nextDataSet.getValue().getFoundNearRange()) {
                                                entries.add(nextDataSet.getValue().getEntries().get(i));
                                                if (Math.abs(pX - x) < nearRange) {
                                                    nearPx = pX;
                                                }
                                            }
                                        }
                                    }
                                    highLighter.getNearEntriesMap().put(type, entries);
                                    break;
                                default:
                            }
                        }
                    }
                }
            }
        }
        if (highLighter.getNearEntriesMap().isEmpty()) {
            highLighter.setpX(-1f);
        } else {
            Iterator<DataType> nearbyIterator = highLighter.getNearEntriesMap().keySet().iterator();
            nearbyFilterRange = nearRange;
            selectDataType = null;
            selectEntries = new LinkedList<>();
            nearestMap = new ConcurrentHashMap<>();
            while (nearbyIterator.hasNext()) {
                DataType nearbyDataType = nearbyIterator.next();
                for (int i = 0; i < highLighter.getNearEntriesMap().get(nearbyDataType).size(); i++) {

//                    synchronized (nearestMap) {
                    nearestMap = getNearestDataMap(nearestMap, highLighter.getNearEntriesMap().get(nearbyDataType));
//                    }

//                    pX = left + (highLighter.getNearEntriesMap().get(nearbyDataType).get(i).getX() - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
////                    if(Math.abs(highLighter.getNearEntriesMap().get(nearbyDataType).get(i).getX() - x)<nearbyFilterRange) {
//                    if (Math.abs(pX - x) < nearbyFilterRange) {
//                        selectDataType = nearbyDataType;
//                        nearbyFilterRange = Math.abs(pX - x);
//                        //notice 根据entry tag找出其中最少的
//                        nearestMap = getNearestDataMap();
//                        selectEntries.clear();
//                        selectEntries.add(highLighter.getNearEntriesMap().get(nearbyDataType).get(i));
//                    }
                }
            }

            highLighter.getNearEntriesMap().clear();
            highLighter.setNearEntriesMap(analysisNeedHighlightData(highLighter.getNearEntriesMap(), nearestMap));
            if (!highLighter.getNearEntriesMap().isEmpty()) {
                if (touchDown) {
                    highLighter.setpX(nearPx);
                } else {
                    highLighter.setpX(x);
                }
                needShowHighlight = true;
            }


//            highLighter.setpX(nearPx);
//            highLighter.getNearEntriesMap().clear();
//            if (selectDataType != null) {
//                highLighter.getNearEntriesMap().put(selectDataType, selectEntries);
////                pX = left + (selectEntries.getFirst().getX() - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
//                highLighter.setpX(x);
//                needShowHighlight = true;
//            }
//            highLighter.setpX(nearPx);
        }
    }

    private HashMap<DataType, LinkedList<Entry>> analysisNeedHighlightData(HashMap<DataType, LinkedList<Entry>> selectedMap, ConcurrentHashMap<String, Entry> nearestMap) {
        for (Entry entry : nearestMap.values()) {
            if (entry.getDataType() == null) continue;
            if (selectedMap.containsKey(entry.getDataType())) {
                if (selectedMap.get(entry.getDataType()).getFirst().getNearGap() > entry.getNearGap()) {
                    selectedMap.get(entry.getDataType()).set(0, entry);
                }
            } else {
                LinkedList<Entry> tempEntires = new LinkedList<>();
                tempEntires.add(entry);
                selectedMap.put(entry.getDataType(), tempEntires);
            }
        }
        return selectedMap;
    }

//    private ConcurrentHashMap<String, Entry> getNearestDataMap() {
//    }

    private ConcurrentHashMap<String, Entry> getNearestDataMap(ConcurrentHashMap<String, Entry> nearestMap, LinkedList<Entry> entries) {
        for (Entry entry : entries) {
            if (entry.getTag() == null) continue;
            if (nearestMap.containsKey(entry.getTag())) {
                if (nearestMap.get(entry.getTag()).getNearGap() > entry.getNearGap()) {
                    nearestMap.put(entry.getTag(), entry);
                }
            } else {
                nearestMap.put(entry.getTag(), entry);
            }
        }
        return nearestMap;
    }

    public void resetBaseInfo() {
        width = getWidth();
        height = getHeight();

        left = 0 + leftMargin;
        right = width - rightMargin;

        top = 0 + topMargin;
        bottom = height - bottomMargin;

        realWidth = width - leftMargin - rightMargin;
        xLabelGap = realWidth / (xAxis.getMidCount() + 1);
        maxXCountGap = (int) ((xAxis.getMax() - xAxis.getMin()) / (xAxis.getMidCount() + 1));

        realHeight = height - topMargin - bottomMargin;
        yLabelGap = realHeight / (yAxis.getMidCount() + 1);
        maxYCountGap = (int) ((yAxis.getMax() - yAxis.getMin()) / (yAxis.getMidCount() + 1));
        y2LabelGap = realHeight / (y2Axis.getMidCount() + 1);
        maxY2CountGap = (int) ((y2Axis.getMax() - y2Axis.getMin()) / (y2Axis.getMidCount() + 1));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bgColor = style == STYLE_LIGHT ? Color.WHITE : Color.alpha(0x333);

        canvas.drawColor(bgColor);

        //notice 计算图表参数
        if (width == 0 || height == 0) {
            width = getWidth();
            height = getHeight();
            left = 0 + leftMargin;
            right = width - rightMargin;
            top = 0 + topMargin;
            bottom = height - bottomMargin;
            realWidth = width - leftMargin - rightMargin;
            realHeight = height - topMargin - bottomMargin;
        }

        if (lastXMidCount != xAxis.getMidCount()) {
            lastXMidCount = xAxis.getMidCount();
            xLabelGap = realWidth / (xAxis.getMidCount() + 1);
            maxXCountGap = (int) ((xAxis.getMax() - xAxis.getMin()) / (xAxis.getMidCount() + 1));
        }

        if (lastYMidCount != yAxis.getMidCount()) {
            lastYMidCount = yAxis.getMidCount();
            yLabelGap = realHeight / (yAxis.getMidCount() + 1);
            maxYCountGap = (int) ((yAxis.getMax() - yAxis.getMin()) / (yAxis.getMidCount() + 1));
        }

        if (lastY2MidCount != y2Axis.getMidCount()) {
            lastY2MidCount = y2Axis.getMidCount();
            y2LabelGap = realHeight / (y2Axis.getMidCount() + 1);
            maxY2CountGap = (int) ((y2Axis.getMax() - y2Axis.getMin()) / (y2Axis.getMidCount() + 1));
        }

        frame.set(0 + leftMargin, 0 + topMargin, width - rightMargin, height - bottomMargin);

        //notice 绘制边框
        canvas.drawRect(frame, lPaint);


        drawableHorizontalElement(canvas);

        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;

        /**
         * notice 绘制Y轴label
         * notice 首先需要判断的是是否有指定数值，如果有那么就按照指定数值数组进行分割
         * notice 否则就按照分割数进行分割
         */
        if (yAxis.getLabelArrs() == null || yAxis.getLabelArrs().length == 0) {
            //notice 绘制y轴label
            //notice 绘制Y轴分割线
            if (showGridVertical) {
                for (int i = 1; i < yAxis.getMidCount() + 1; i++) {
                    x1 = left;
                    y1 = bottom - i * yLabelGap;
                    x2 = right;
                    y2 = y1;
                    canvas.drawLine(x1, y1, x2, y2, gridPaint);
                }
            }

            //notice 绘制纵向label
            if (showLabelVertical) {
                //notice 绘制起点和终点
                if (yAxis.getiLabelFormatter() != null) {

                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(yAxis.getMin(), -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, bottom - labelRectHeight / 2, tPaint);

                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(yAxis.getMax(), -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, top + labelRectHeight, tPaint);
                }

                //notice 绘制纵向网格lable
                for (int i = 1; i < yAxis.getMidCount() + 1; i++) {
                    x1 = left;
                    y1 = bottom - i * yLabelGap;
                    x2 = right;
                    y2 = y1;
                    value = yAxis.getMin() + i * maxYCountGap;
                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight / 2, tPaint);
                }
            }
        } else {
            //notice 绘制y轴label
            if (showGridVertical) {
                for (int i = 0; i < yAxis.getLabelArrs().length; i++) {
                    x1 = left;
                    y1 = bottom - realHeight * (yAxis.getLabelArrs()[i] - yAxis.getMin()) / (yAxis.getMax() - yAxis.getMin());
                    x2 = right;
                    y2 = y1;
                    canvas.drawLine(x1, y1, x2, y2, gridPaint);
                }
            }

            if (showLabelVertical) {
                for (int i = 0; i < yAxis.getLabelArrs().length; i++) {
                    x1 = left;
                    y1 = bottom - realHeight * (yAxis.getLabelArrs()[i] - yAxis.getMin()) / (yAxis.getMax() - yAxis.getMin());
                    x2 = right;
                    y2 = y1;
                    value = yAxis.getLabelArrs()[i];
                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    if (i == 0) {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 - labelRectHeight / 2, tPaint);
                    } else if (i == yAxis.getLabelArrs().length - 1) {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight, tPaint);
                    } else {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight / 2, tPaint);
                    }
                }
            }
        }


        if (y2Axis.getLabelArrs() == null || y2Axis.getLabelArrs().length == 0) {
            //notice 绘制y轴label 边沿label
            if (showLabelVertical) {
                if (y2Axis.getiLabelFormatter() != null) {
                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(y2Axis.getMin(), -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, right + 10, bottom - labelRectHeight / 2, tPaint);

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(y2Axis.getMax(), -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, right + 10, top + labelRectHeight, tPaint);
                }

                for (int i = 1; i < y2Axis.getMidCount() + 1; i++) {
                    x1 = left;
                    y1 = bottom - i * y2LabelGap;
                    x2 = right;
                    y2 = y1;
                    value = y2Axis.getMin() + i * maxY2CountGap;

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();

                    canvas.drawText(labelStr, right + 10, y1 + labelRectHeight / 2, tPaint);
                }
            }
        } else {
            //notice 绘制y轴label
            if (showLabelVertical) {
                for (int i = 0; i < y2Axis.getLabelArrs().length; i++) {
                    x1 = left;
                    y1 = bottom - realHeight * (y2Axis.getLabelArrs()[i] - y2Axis.getMin()) / (y2Axis.getMax() - y2Axis.getMin());
                    x2 = right;
                    y2 = y1;
                    value = y2Axis.getLabelArrs()[i];

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    if (i == 0) {
                        canvas.drawText(labelStr, right + 10, y1 - labelRectHeight / 2, tPaint);
                    } else if (i == yAxis.getLabelArrs().length - 1) {
                        canvas.drawText(labelStr, right + 10, y1 + labelRectHeight, tPaint);
                    } else {
                        canvas.drawText(labelStr, right + 10, y1 - labelRectHeight / 2, tPaint);
                    }
                }
            }
        }

        canvas.clipRect(frame);

        if (dataSetBox != null) {
            dataSetIterator = dataSetBox.getDataSetMap().entrySet().iterator();
            while (dataSetIterator.hasNext()) {
                nextDataSet = dataSetIterator.next();
                type = nextDataSet.getValue().getType();
                switch (type) {
                    case SinglePoint:
                        drawSinglePoint(canvas);
                        break;
                    case DoublePoint:
                        drawDoublePoint(canvas);
                        break;
                    case TriplePoint:
                        drawTriplePoint(canvas);
                        break;
                    case QuatraPoint:
                        drawQuatraPoint(canvas);
                        break;
                    default:
                        drawSinglePoint(canvas);
                }

            }
        }

        if (needShowHighlight) {
            //绘制marker
            if (highLighter.getpX() != -1f && highLighter.getNearEntriesMap() != null) {
                canvas.drawLine(highLighter.getpX(), bottom, highLighter.getpX(), top, highLightPaint);
                if (!highLighter.getNearEntriesMap().isEmpty()) {
                    markerTextArr.clear();
                    nearByEntriesIterator = highLighter.getNearEntriesMap().entrySet().iterator();
                    while (nearByEntriesIterator.hasNext()) {
                        nextNearbyEntries = nearByEntriesIterator.next();
                        entries = nextNearbyEntries.getValue();
                        switch (nextNearbyEntries.getKey()) {
                            case line:
                            case line_fill:
                            case line_fill2:
//                                if (nextNearbyEntries.getValue().size() > 0 && entries.size() % 2 == 0) {
//                                    for (int i = 0; i < entries.size(); i = i + 2) {
//                                        if (entries.get(i).getData() instanceof String && entries.get(i + 1).getData() instanceof String) {
//                                            if (((String) entries.get(i).getData()).isEmpty())
//                                                continue;
//                                            markerSb.setLength(0);
//                                            markerSb.append(entries.get(i).getData()).append(" - ").append(entries.get(i + 1).getData());
//                                            markerTextArr.add(markerSb.toString());
//                                        }
//                                    }
//                                }
//                                break;
                            case point:
                            case bar:
                            case bitmap:
                            case range_bar:
                                for (int i = 0; i < entries.size(); i++) {
                                    if (entries.get(i).getData() instanceof String) {
                                        if (((String) entries.get(i).getData()).isEmpty()) continue;
                                        markerSb.setLength(0);
                                        markerSb.append(entries.get(i).getData());
                                        markerTextArr.add(new Pair<>(entries.get(i).getHighlightColor(getContext()), markerSb.toString()));
                                    } else if (entries.get(i).getData() instanceof String[]) {
                                        if (((String[]) entries.get(i).getData()) == null) continue;
                                        for (int j = 0; j < ((String[]) entries.get(i).getData()).length; j++) {
                                            markerSb.setLength(0);
                                            markerSb.append(((String[]) entries.get(i).getData())[j]);
                                            markerTextArr.add(new Pair<>(entries.get(i).getHighlightColor(getContext()), markerSb.toString()));
                                        }
                                    }
                                }
                                break;
                            default:
                        }
                    }

                    isHighLeft = highLighter.getpX() <= left + realWidth / 2;

                    if (markerTextArr.isEmpty()) {
//                    Log.d(TAG, "onDraw: 01");
                        markerFrame.set(
                                highLighter.getpX() + (isHighLeft ? 0 : -maxMarkerWidth),
                                top,
                                highLighter.getpX() + (isHighLeft ? maxMarkerWidth : 0),
                                top + 120);
                        canvas.drawRect(markerFrame, markerPaint);
                    } else {
//                    Log.d(TAG, "onDraw: 02");
                        markerTTop = top;
                        markerTBottom = top;
                        for (int i = 0; i < markerTextArr.size(); i++) {
                            synchronized (MChart.this) {
                                if (i % 2 == 0) {
                                    markerTTop = markerTBottom;
                                    markerTBottom += isShowSgChart ? 40 : 60;
                                } else {
                                    markerTTop = markerTBottom;
                                    markerTBottom += 60;
                                }
                            }
//                            Log.d(TAG, "onDraw: isHIghLeft= " + isHighLeft);
//                            Log.d(TAG, "onDraw("+i+") called with: markerTTop = [" + markerTTop + "]"+" markerTBottom = [" + markerTBottom + "]");

                            maxMarkerWidth = 200;
                            for (int i1 = 0; i1 < markerTextArr.size(); i1++) {
                                Rect rect = new Rect();
                                markerTLPaint.getTextBounds(markerTextArr.get(i1).second, 0, markerTextArr.get(i1).second.length(), rect);
                                int w = rect.width();
                                if (rect.width() > maxMarkerWidth) {
                                    maxMarkerWidth = rect.width();
                                }
                            }

                            maxMarkerWidth += 20;

                            markerFrame.set(
                                    highLighter.getpX() + (isHighLeft ? 0 : -maxMarkerWidth),
//                                    i == 0 ? top : (top + 40 * (i + 1)),
                                    markerTTop,
                                    highLighter.getpX() + (isHighLeft ? maxMarkerWidth : 0),
                                    markerTBottom);
                            markerPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_main));
                            markerPaint.setColor(markerTextArr.get(i).first);
                            canvas.drawRect(markerFrame, markerPaint);
                        }
                    }


                    for (int i = 0; i < markerTextArr.size(); i++) {
                        curMarkerText = markerTextArr.get(i).second;
                        isUnderLine = curMarkerText.startsWith("[u]");
                        if (isUnderLine) curMarkerText = curMarkerText.replace("[u]", "");
                        canvas.drawText(curMarkerText, highLighter.getpX() + (isHighLeft ? 0 : -maxMarkerWidth), top + ((i + 1) * (isShowSgChart ? 40 : 50)), i % 2 == 0
                                ? (isShowSgChart
                                ? markerTPaint
                                : (isUnderLine ? markerTLUPaint : markerTLPaint))
                                : (isUnderLine ? markerTLUPaint : markerTLPaint));

                    }
                    lastMarkerTextArr = markerTextArr;
                    lastMarkerPx = highLighter.getpX();


//                Log.d(TAG, "onDraw: ");

                }
            } else {
                if (lastMarkerTextArr != null && lastMarkerTextArr.size() > 0) {

                    canvas.drawLine(lastMarkerPx, bottom, lastMarkerPx, top, lPaint);
                    if (lastMarkerTextArr.isEmpty()) {
//                    Log.d(TAG, "onDraw: 11");
                        markerFrame.set(
                                lastMarkerPx + (isHighLeft ? 0 : -maxMarkerWidth),
                                top,
                                lastMarkerPx + (isHighLeft ? maxMarkerWidth : 0),
                                top + 120);
                        canvas.drawRect(markerFrame, markerPaint);
                    } else {
//                    Log.d(TAG, "onDraw: 11");
                        markerTTop = top;
                        markerTBottom = top;
                        for (int i = 0; i < markerTextArr.size(); i++) {
                            if (i % 2 == 0) {
                                markerTTop = markerTBottom;
                                markerTBottom += isShowSgChart ? 40 : 60;
                            } else {
                                markerTTop = markerTBottom;
                                markerTBottom += 60;
                            }


                            maxMarkerWidth = 200;
                            for (int i1 = 0; i1 < lastMarkerTextArr.size(); i1++) {
                                Rect rect = new Rect();
                                markerTLPaint.getTextBounds(lastMarkerTextArr.get(i1).second, 0, lastMarkerTextArr.get(i1).second.length(), rect);
                                int w = rect.width();
                                if (rect.width() > maxMarkerWidth) {
                                    maxMarkerWidth = rect.width();
                                }
                            }

                            maxMarkerWidth += 20;

                            markerFrame.set(
                                    lastMarkerPx + (isHighLeft ? 0 : -maxMarkerWidth),
//                                    i == 0 ? top : (top + 40 * (i + 1)),
                                    markerTTop,
                                    lastMarkerPx + (isHighLeft ? maxMarkerWidth : 0),
                                    markerTBottom);
                            canvas.drawRect(markerFrame, markerPaint);
                        }
                    }

                    for (int i = 0; i < lastMarkerTextArr.size(); i++) {
                        curMarkerText = lastMarkerTextArr.get(i).second;
                        isUnderLine = curMarkerText.startsWith("[u]");
                        if (isUnderLine) curMarkerText = curMarkerText.replace("[u]", "");
                        canvas.drawText(curMarkerText, lastMarkerPx + (isHighLeft ? 0 : -maxMarkerWidth), top + ((i + 1) * (isShowSgChart ? 40 : 50)), i % 2 == 0 ?
                                (isShowSgChart ? markerTPaint : (isUnderLine ? markerTLUPaint : markerTLPaint))
                                : (isUnderLine ? markerTLUPaint : markerTLPaint));
                    }

                }
            }
        }
    }

    private void drawSinglePoint(Canvas canvas) {
        for (int i = 0; i < nextDataSet.getValue().getEntries().size(); i++) {

            x = nextDataSet.getValue().getEntries().get(i).getX();
            y = nextDataSet.getValue().getEntries().get(i).getY();
            if (x < tempXAxis.getMin() || x > tempXAxis.getMax()) {
                continue;
            }
            pX = left + (x - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            if (!nextDataSet.getValue().isY2()) {
                pY = bottom - (y - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            } else {
                pY = bottom - (y - y2Axis.getMin()) * realHeight / (y2Axis.getMax() - y2Axis.getMin());
            }

            if (nextDataSet.getValue().getEntryDrafter() != null)
                nextDataSet.getValue().getEntryDrafter().drawSingleEntry(canvas, new PXY(pX, pY), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawDoublePoint(Canvas canvas) {
        float x0, x1, y0, y1, pX0, pY0, pX1, pY1;
        int size = 2;
        for (int i = 0; i < nextDataSet.getValue().getEntries().size() - 1; i++) {
//            if(i%size!=0) continue;
            x0 = nextDataSet.getValue().getEntries().get(i).getX();
            y0 = nextDataSet.getValue().getEntries().get(i).getY();

            x1 = nextDataSet.getValue().getEntries().get(i + 1).getX();
            y1 = nextDataSet.getValue().getEntries().get(i + 1).getY();
            if (x0 < tempXAxis.getMin() && x1 < tempXAxis.getMin()) {
                continue;
            }
            if (x0 > tempXAxis.getMax() && x1 > tempXAxis.getMax()) {
                continue;
            }
            if (x0 < tempXAxis.getMin()) {
                x0 = tempXAxis.getMin();
            }
            if (x1 > tempXAxis.getMax()) {
                x1 = tempXAxis.getMax();
            }


            pX0 = left + (x0 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY0 = bottom - (y0 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX1 = left + (x1 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY1 = bottom - (y1 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());

            if (nextDataSet.getValue().getEntryDrafter() != null)
                nextDataSet.getValue().getEntryDrafter().drawDoubleEntry(canvas, new PXY(pX0, pY0), new PXY(pX1, pY1), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawTriplePoint(Canvas canvas) {
        float x0, x1, x2, y0, y1, y2, pX0, pY0, pX1, pY1, pX2, pY2;
        int size = 3;
        for (int i = 0; i < nextDataSet.getValue().getEntries().size() - 2; i++) {
//            if(i%size!=0) continue;
            x0 = nextDataSet.getValue().getEntries().get(i).getX();
            y0 = nextDataSet.getValue().getEntries().get(i).getY();

            x1 = nextDataSet.getValue().getEntries().get(i + 1).getX();
            y1 = nextDataSet.getValue().getEntries().get(i + 1).getY();

            x2 = nextDataSet.getValue().getEntries().get(i + 2).getX();
            y2 = nextDataSet.getValue().getEntries().get(i + 2).getY();
            if (x0 < tempXAxis.getMin() && x1 < tempXAxis.getMin() && x2 < tempXAxis.getMin()) {
                continue;
            }
            if (x0 > tempXAxis.getMax() && x1 > tempXAxis.getMax() && x2 > tempXAxis.getMax()) {
                continue;
            }
            if (x0 < tempXAxis.getMin()) {
                x0 = tempXAxis.getMin();
            }
            if (x1 > tempXAxis.getMax()) {
                x1 = tempXAxis.getMax();
            }
            if (x2 > tempXAxis.getMax()) {
                x2 = tempXAxis.getMax();
            }


            pX0 = left + (x0 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY0 = bottom - (y0 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX1 = left + (x1 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY1 = bottom - (y1 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX2 = left + (x2 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY2 = bottom - (y2 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());

            if (nextDataSet.getValue().getEntryDrafter() != null)
                nextDataSet.getValue().getEntryDrafter().drawTripleEntry(canvas, new PXY(pX0, pY0), new PXY(pX1, pY1),new PXY(pX2, pY2), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawQuatraPoint(Canvas canvas) {
        float x0, x1, x2,x3, y0, y1, y2,y3, pX0, pY0, pX1, pY1, pX2, pY2, pX3, pY3;
        int size = 3;
        for (int i = 0; i < nextDataSet.getValue().getEntries().size() - 3; i++) {
//            if(i%size!=0) continue;
            x0 = nextDataSet.getValue().getEntries().get(i).getX();
            y0 = nextDataSet.getValue().getEntries().get(i).getY();

            x1 = nextDataSet.getValue().getEntries().get(i + 1).getX();
            y1 = nextDataSet.getValue().getEntries().get(i + 1).getY();

            x2 = nextDataSet.getValue().getEntries().get(i + 2).getX();
            y2 = nextDataSet.getValue().getEntries().get(i + 2).getY();

            x3 = nextDataSet.getValue().getEntries().get(i + 3).getX();
            y3 = nextDataSet.getValue().getEntries().get(i + 3).getY();
            if (x0 < tempXAxis.getMin() && x1 < tempXAxis.getMin() && x2 < tempXAxis.getMin()&& x3 < tempXAxis.getMin()) {
                continue;
            }
            if (x0 > tempXAxis.getMax() && x1 > tempXAxis.getMax() && x2 > tempXAxis.getMax()&& x3 > tempXAxis.getMax()) {
                continue;
            }
            if (x0 < tempXAxis.getMin()) {
                x0 = tempXAxis.getMin();
            }
            if (x1 > tempXAxis.getMax()) {
                x1 = tempXAxis.getMax();
            }
            if (x2 > tempXAxis.getMax()) {
                x2 = tempXAxis.getMax();
            }
            if (x3 > tempXAxis.getMax()) {
                x3 = tempXAxis.getMax();
            }


            pX0 = left + (x0 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY0 = bottom - (y0 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX1 = left + (x1 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY1 = bottom - (y1 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX2 = left + (x2 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY2 = bottom - (y2 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());
            pX3 = left + (x3 - tempXAxis.getMin()) * realWidth / (tempXAxis.getMax() - tempXAxis.getMin());
            pY3 = bottom - (y3 - yAxis.getMin()) * realHeight / (yAxis.getMax() - yAxis.getMin());

            if (nextDataSet.getValue().getEntryDrafter() != null)
                nextDataSet.getValue().getEntryDrafter().drawQuatraEntry(canvas, new PXY(pX0, pY0), new PXY(pX1, pY1),new PXY(pX2, pY2),new PXY(pX3, pY3), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawableHorizontalElement(Canvas canvas) {
        //notice 绘制横向网格
        if (showGridHorizontal) {
            for (int i = 1; i < tempXAxis.getMidCount() + 1; i++) {
                x1 = left + i * xLabelGap;
                y1 = top;
                x2 = x1;
                y2 = bottom;
                canvas.drawLine(x1, y1, x2, y2, gridPaint);
            }
        }

        //notice 绘制横向网格label
        if (showLabelHorizontal) {
            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
            //notice 绘制x轴label 起点和终点
            if (tempXAxis.getiLabelFormatter() != null) {

                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(tempXAxis.getMin(), tempXAxis.getMin(), tempXAxis.getMax());
                tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, left, bottom + labelRectHeight + 10, tPaint);

                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(tempXAxis.getMax(), tempXAxis.getMin(), tempXAxis.getMax());
                tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, right - labelRectWidth, bottom + labelRectHeight + 10, tPaint);
            }

            for (int i = 1; i < tempXAxis.getMidCount() + 1; i++) {
                x1 = left + i * xLabelGap;
                y1 = top;
                x2 = x1;
                y2 = bottom;
                labelValue = tempXAxis.getMin() + i * maxXCountGap;
                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(labelValue, tempXAxis.getMin(), tempXAxis.getMax());
                tPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, x1 - labelRectWidth / 2, bottom + labelRectHeight + 10, tPaint);
            }
        }
    }

    public void cleanHighLight(long timeDelay) {
        handler.removeCallbacks(cleanHighLightRunnbale);
        handler.postDelayed(cleanHighLightRunnbale, timeDelay);
    }

    public BaseAxis getxAxis() {
        return xAxis;
    }


    public void setDataSetBox(DataSetBox dataSetBox) {
        this.dataSetBox = dataSetBox;
    }

    /**
     * view的大小控制
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));

    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 40;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 40;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    public void clearHighlight() {
        highLighter = new HighLighter(-1, new HashMap<>());
        lastMarkerTextArr = null;
        lastMarkerPx = 0;
        invalidate();
    }


    public void setxAxis(BaseAxis xAxis) {
        this.xAxis = xAxis;
        tempXAxis = xAxis.clone();
    }

    public void setyAxis(BaseAxis yAxis) {
        this.yAxis = yAxis;
    }

    public void sety2Axis(BaseAxis y2Axis) {
        this.y2Axis = y2Axis;
    }


    public void setTopMargin(float topMargin) {
        this.topMargin = topMargin;
    }

    public void setBottomMargin(float bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public void setLeftMargin(float leftMargin) {
        this.leftMargin = leftMargin;
    }

    public void setRightMargin(float rightMargin) {
        this.rightMargin = rightMargin;
    }

    public boolean isInterceptTouchEvent() {
        return isInterceptTouchEvent;
    }

    public void setInterceptTouchEvent(boolean interceptTouchEvent) {
        isInterceptTouchEvent = interceptTouchEvent;
    }


    public void setShowSgChart(boolean showSgChart) {
        isShowSgChart = showSgChart;
    }

}
