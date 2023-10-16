package com.bboyuglyk.chart_sdk;


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

import com.bboyuglyk.chart_sdk.dataset.AnimDataSet;
import com.bboyuglyk.chart_sdk.dataset.DataSet;
import com.bboyuglyk.chart_sdk.dataset.DataSetBox;
import com.bboyuglyk.chart_sdk.dataset.OnAnimDataChangeListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class MPieChart extends View implements OnAnimDataChangeListener {
    private static final String TAG = "MChart";


    private Paint borderPaint;
    private Paint highLightPaint;
    private Paint gridPaint;
    private Paint tPaint;
    private Paint xPaint;
    private Paint y1Paint;
    private Paint y2Paint;
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
    private float rightMargin = 100;
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

    private MarkerBuilder markerBuilder;

    private IHighlightBuilder highlightBuilder;
    //notice 饼图触摸坐标
    private PXY touchPXY;

    public void setHighlightBuilder(IHighlightBuilder highlightBuilder) {
        this.highlightBuilder = highlightBuilder;
    }

    private float markerHeight;
    private Entry findEntry;

    //notice 触摸吸附模式
    private HightlightMode hightlightMode = HightlightMode.desorption;

    public void setHightlightMode(HightlightMode hightlightMode) {
        this.hightlightMode = hightlightMode;
    }

    public void setMarkerBuilder(MarkerBuilder markerBuilder) {
        this.markerBuilder = markerBuilder;
    }

    public static final int STYLE_LIGHT = 0;
    public static final int STYLE_DARK = 1;

    private int style = STYLE_LIGHT;

    //notice:  是否显示边框
    private boolean showBorder = true;

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
    private int bgColor = Color.WHITE;
    private int xLabelColor;
    private int y1LabelColor;
    private int y2LabelColor;

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    private int borderColor = Color.GRAY;

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    private int gridColor = Color.GRAY;

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public void setFullClickEnable(boolean fullClickEnable) {
        this.fullClickEnable = fullClickEnable;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public void setGridVisibility(boolean showGridHorizontal, boolean showGridVertical) {
        this.showGridHorizontal = showGridHorizontal;
        this.showGridVertical = showGridVertical;
    }

    public void setLabelVisibility(boolean showLabelHorizontal, boolean showLabelVertical) {
        this.showLabelHorizontal = showLabelHorizontal;
        this.showLabelVertical = showLabelVertical;
    }


    public void setLabelColor(int xLabelColor, int y1LabelColor, int y2LabelColor) {
        this.xLabelColor = xLabelColor;
        this.y1LabelColor = y1LabelColor;
        this.y2LabelColor = y2LabelColor;
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
    //notice 设置ACTION_DOWN识别的数据类型
    private Set<DataType> actionDownTypes;
    //notice 设置ACTION_MOVE识别的数据类型
    private Set<DataType> actionMoveTypes;
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

    public MPieChart(Context context) {
        super(context);
        init();
    }

    public MPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        markerBuilder = new MarkerBuilder();

        highlightBuilder = new HighlightBuilder();

        actionDownTypes = new ArraySet<>();
//        actionDownTypes.add(bitmap);
//        actionDownTypes.add(bar);
//        actionDownTypes.add(range_bar);

        actionMoveTypes = new ArraySet<>();
//        actionMoveTypes.add(bitmap);
//        actionMoveTypes.add(bar);
//        actionMoveTypes.add(range_bar);

        markerSb = new StringBuilder();

        markerTextArr = new ArrayList<>();

        highLighter = new HighLighter(-1, new HashMap<>());
        frame = new RectF();
        borderPath = new Path();
        markerFrame = new RectF();

        //notice 边框画笔
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);//设置抗锯齿
        borderPaint.setStyle(Paint.Style.STROKE);//实心
        borderPaint.setStrokeWidth(2);//线条粗细
        borderPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_grid_line));//线条粗细

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
        xPaint = new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setStrokeWidth(5);
        xPaint.setColor(Color.BLACK);
        xPaint.setTextSize(dp2px(10));

        //notice 文字画笔
        y1Paint = new Paint();
        y1Paint.setAntiAlias(true);
        y1Paint.setStrokeWidth(5);
        y1Paint.setColor(Color.BLACK);
        y1Paint.setTextSize(dp2px(10));

        //notice 文字画笔
        y2Paint = new Paint();
        y2Paint.setAntiAlias(true);
        y2Paint.setStrokeWidth(5);
        y2Paint.setColor(Color.BLACK);
        y2Paint.setTextSize(dp2px(10));

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

    /**
     * tip 添加数据
     *
     * @param dataSet
     */
    public void addAnimDataSet(AnimDataSet dataSet) {
        dataSetBox.addAnimDataSet(dataSet);
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

                    findNearbyEntries(x, y, true);
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
                            findNearbyEntries(x, y, false);
                            invalidate();
                        } else {
                            findNearbyEntries(x, y, false);
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
     * @param touchDown
     */
    private void findNearbyEntries(float x, float y, boolean touchDown) {
        needShowHighlight = true;
//        Log.d(TAG, "findNearbyEntries() called with: x = [" + x + "], y = [" + y + "], touchDown = [" + touchDown + "]");
        this.touchPXY = new PXY(x, y);
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

        borderPaint.setColor(borderColor);
        frame.set(0 + leftMargin, 0 + topMargin, width - rightMargin, height - bottomMargin);
        if (showBorder) {
            //notice 绘制边框
            canvas.drawRect(frame, borderPaint);
        }

        gridPaint.setColor(gridColor);
        //notice 绘制横向元素
        drawableHorizontalElement(canvas);

        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        //notice 绘制纵向元素
        drawableVerticalElement(canvas);

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
                    case CurveSingle:
                        drawSinglePoint(canvas);
                        break;
                    case CurveDouble:
                        drawDoublePoint(canvas);
                        break;
                    case Pie:
                        drawPiesPoint(canvas);
                        break;
                    default:
                        drawSinglePoint(canvas);
                }

            }
        }

        drawHighlightAndMarker(canvas);
    }

    public void setNeedShowHighlight(boolean needShowHighlight) {
        this.needShowHighlight = needShowHighlight;
    }

    private void drawHighlightAndMarker(Canvas canvas) {
        markerHeight = 0;
        if (needShowHighlight) {
            //绘制marker
            highlightBuilder.drawHighlight(canvas, this.touchPXY, new ViewportInfo(left, top, right, bottom));
        }
    }

    /**
     * notice 绘制纵向元素
     *
     * @param canvas
     */
    private void drawableVerticalElement(Canvas canvas) {
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
                y1Paint.setColor(y1LabelColor);
                if (yAxis.getiLabelFormatter() != null) {

                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(yAxis.getMin(), -1, -1);
                    y1Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, bottom - labelRectHeight / 2, y1Paint);

                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(yAxis.getMax(), -1, -1);
                    y1Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, top + labelRectHeight, y1Paint);
                }

                //notice 绘制纵向网格lable
                for (int i = 1; i < yAxis.getMidCount() + 1; i++) {
                    x1 = left;
                    y1 = bottom - i * yLabelGap;
                    x2 = right;
                    y2 = y1;
                    value = yAxis.getMin() + i * maxYCountGap;
                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    y1Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight / 2, y1Paint);
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
                y1Paint.setColor(y1LabelColor);
                for (int i = 0; i < yAxis.getLabelArrs().length; i++) {
                    x1 = left;
                    y1 = bottom - realHeight * (yAxis.getLabelArrs()[i] - yAxis.getMin()) / (yAxis.getMax() - yAxis.getMin());
                    x2 = right;
                    y2 = y1;
                    value = yAxis.getLabelArrs()[i];
                    labelStr = yAxis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    y1Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    if (i == 0) {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 - labelRectHeight / 2, y1Paint);
                    } else if (i == yAxis.getLabelArrs().length - 1) {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight, y1Paint);
                    } else {
                        canvas.drawText(labelStr, left - labelRectWidth - 10, y1 + labelRectHeight / 2, y1Paint);
                    }
                }
            }
        }

        /**
         * notice 绘制Y2轴label
         * notice 首先需要判断的是是否有指定数值，如果有那么就按照指定数值数组进行分割
         * notice 否则就按照分割数进行分割
         */
        if (y2Axis.getLabelArrs() == null || y2Axis.getLabelArrs().length == 0) {
            //notice 绘制y轴label 边沿label
            if (showLabelVertical) {
                y2Paint.setColor(y2LabelColor);
                if (y2Axis.getiLabelFormatter() != null) {
                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(y2Axis.getMin(), -1, -1);
                    y2Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, right + 10, bottom - labelRectHeight / 2, y2Paint);

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(y2Axis.getMax(), -1, -1);
                    y2Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    canvas.drawText(labelStr, right + 10, top + labelRectHeight, y2Paint);
                }

                for (int i = 1; i < y2Axis.getMidCount() + 1; i++) {
                    x1 = left;
                    y1 = bottom - i * y2LabelGap;
                    x2 = right;
                    y2 = y1;
                    value = y2Axis.getMin() + i * maxY2CountGap;

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    y2Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();

                    canvas.drawText(labelStr, right + 10, y1 + labelRectHeight / 2, y2Paint);
                }
            }
        } else {
            //notice 绘制y轴label
            if (showLabelVertical) {
                y2Paint.setColor(y2LabelColor);
                for (int i = 0; i < y2Axis.getLabelArrs().length; i++) {
                    x1 = left;
                    y1 = bottom - realHeight * (y2Axis.getLabelArrs()[i] - y2Axis.getMin()) / (y2Axis.getMax() - y2Axis.getMin());
                    x2 = right;
                    y2 = y1;
                    value = y2Axis.getLabelArrs()[i];

                    labelStr = y2Axis.getiLabelFormatter().getLabelFormat(value, -1, -1);
                    y2Paint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                    labelRectHeight = labelRect.height();
                    labelRectWidth = labelRect.width();
                    if (i == 0) {
                        canvas.drawText(labelStr, right + 10, y1 - labelRectHeight / 2, y2Paint);
                    } else if (i == yAxis.getLabelArrs().length - 1) {
                        canvas.drawText(labelStr, right + 10, y1 + labelRectHeight, y2Paint);
                    } else {
                        canvas.drawText(labelStr, right + 10, y1 - labelRectHeight / 2, y2Paint);
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
                nextDataSet.getValue().getEntryDrafter().drawSingleEntry(canvas, i, new PXY(pX, pY), new ViewportInfo(left, top, right, bottom));

            checkNeedDrawHighlightEntry(canvas, i, nextDataSet.getValue(), nextDataSet.getValue().getEntries().get(i), new PXY(pX, pY), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void checkNeedDrawHighlightEntry(Canvas canvas, int index, DataSet dataSet, Entry entry, PXY pxy, ViewportInfo viewportInfo) {
        if (needShowHighlight) {
            for (DataType dataType : highLighter.getNearEntriesMap().keySet()) {
                LinkedList<Entry> highLightEntries = highLighter.getNearEntriesMap().get(dataType);
                if (highLightEntries == null) continue;
                for (Entry highLightEntry : highLightEntries) {
                    if (entry.equals(highLightEntry)) {
                        if (dataSet.getEntryDrafter() != null)
                            dataSet.drawHighlightEntry(canvas, index, pxy, viewportInfo);
                    }
                }

            }
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
                nextDataSet.getValue().getEntryDrafter().drawDoubleEntry(canvas, i, new PXY(pX0, pY0), new PXY(pX1, pY1), new ViewportInfo(left, top, right, bottom));

            checkNeedDrawHighlightEntry(canvas, i, nextDataSet.getValue(), nextDataSet.getValue().getEntries().get(i), new PXY(pX0, pY0), new ViewportInfo(left, top, right, bottom));
            if (i == nextDataSet.getValue().getEntries().size() - 2) {
                checkNeedDrawHighlightEntry(canvas, i + 1, nextDataSet.getValue(), nextDataSet.getValue().getEntries().get(i), new PXY(pX1, pY1), new ViewportInfo(left, top, right, bottom));
            }
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
                nextDataSet.getValue().getEntryDrafter().drawTripleEntry(canvas, i, new PXY(pX0, pY0), new PXY(pX1, pY1), new PXY(pX2, pY2), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawQuatraPoint(Canvas canvas) {
        float x0, x1, x2, x3, y0, y1, y2, y3, pX0, pY0, pX1, pY1, pX2, pY2, pX3, pY3;
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
            if (x0 < tempXAxis.getMin() && x1 < tempXAxis.getMin() && x2 < tempXAxis.getMin() && x3 < tempXAxis.getMin()) {
                continue;
            }
            if (x0 > tempXAxis.getMax() && x1 > tempXAxis.getMax() && x2 > tempXAxis.getMax() && x3 > tempXAxis.getMax()) {
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
                nextDataSet.getValue().getEntryDrafter().drawQuatraEntry(canvas, i, new PXY(pX0, pY0), new PXY(pX1, pY1), new PXY(pX2, pY2), new PXY(pX3, pY3), new ViewportInfo(left, top, right, bottom));

        }
    }

    private void drawPiesPoint(Canvas canvas) {
        Entry[] entryArrs = nextDataSet.getValue().getEntries().toArray(new Entry[]{});
        if (nextDataSet.getValue().getEntryDrafter() != null)
            nextDataSet.getValue().getEntryDrafter().drawPieEntries(
                    canvas,
                    new ViewportInfo(left, top, right, bottom),
                    this.touchPXY
                    , entryArrs);

    }

    /**
     * notice 绘制横向元素
     *
     * @param canvas
     */
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
            xPaint.setColor(xLabelColor);
            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
            //notice 绘制x轴label 起点和终点
            if (tempXAxis.getiLabelFormatter() != null) {

                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(tempXAxis.getMin(), tempXAxis.getMin(), tempXAxis.getMax());
                xPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, left, bottom + labelRectHeight + 10, xPaint);

                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(tempXAxis.getMax(), tempXAxis.getMin(), tempXAxis.getMax());
                xPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, right - labelRectWidth, bottom + labelRectHeight + 10, xPaint);
            }

            for (int i = 1; i < tempXAxis.getMidCount() + 1; i++) {
                x1 = left + i * xLabelGap;
                y1 = top;
                x2 = x1;
                y2 = bottom;
                labelValue = tempXAxis.getMin() + i * maxXCountGap;
                labelStr = tempXAxis.getiLabelFormatter().getLabelFormat(labelValue, tempXAxis.getMin(), tempXAxis.getMax());
                xPaint.getTextBounds(labelStr, 0, labelStr.length(), labelRect);
                labelRectHeight = labelRect.height();
                labelRectWidth = labelRect.width();
                canvas.drawText(labelStr, x1 - labelRectWidth / 2, bottom + labelRectHeight + 10, xPaint);
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
        this.dataSetBox.setOnAnimDataChangeListener(this);
    }

    /**
     * view的大小控制
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

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

    @Override
    public void onAnimDataChanged() {
        invalidate();
    }

}
