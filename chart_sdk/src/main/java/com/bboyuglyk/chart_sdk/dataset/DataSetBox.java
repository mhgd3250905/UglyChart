package com.bboyuglyk.chart_sdk.dataset;

import android.animation.ValueAnimator;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bboyuglyk.chart_sdk.BaseAxis;
import com.bboyuglyk.chart_sdk.CurveUtils;
import com.bboyuglyk.chart_sdk.Entry;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class DataSetBox {
    private static final String TAG = "DataSetBox";
    //tip 保存各种类型的数据源
    private ConcurrentSkipListMap<String, DataSet> dataSetMap;

    private OnAnimDataChangeListener onAnimDataChangeListener;

    private BaseAxis xAxis;
    private BaseAxis yAxis;

    public void setAxis(BaseAxis xAxis, BaseAxis yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }


    public void setOnAnimDataChangeListener(OnAnimDataChangeListener onAnimDataChangeListener) {
        this.onAnimDataChangeListener = onAnimDataChangeListener;
    }

    public DataSetBox(Comparator<String> tagComparator) {
        this.dataSetMap = new ConcurrentSkipListMap<>(tagComparator);
    }

    public SortedMap<String, DataSet> getDataSetMap() {
        return dataSetMap;
    }

    public void setDataSetMap(ConcurrentSkipListMap<String, DataSet> dataSetMap) {
        this.dataSetMap = dataSetMap;
    }


    public void addDataSet(DataSet dataSet) {
        dataSetMap.put(dataSet.getTag(), dataSet);
    }

    /**
     * 添加数据：动画
     */
    public void addAnimDataSet(AnimDataSet newDataSet) {
        // step: 首先获取tag，查找是否存在
        String tag = newDataSet.getTag();
        DataSet oldDataSet = dataSetMap.get(tag);
        if (oldDataSet == null) {
            dataSetMap.put(newDataSet.getTag(), newDataSet);
            return;
        }
        // step: 对比数据内容 old->new
        int oldSize = oldDataSet.baseEntries.size();
        int newSize = newDataSet.baseEntries.size();
        int moveSize = Math.min(oldSize, newSize);

        DataSet tempDataSet = newDataSet.clone();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
//                Log.d(TAG, "onAnimationUpdate: "+animation.getAnimatedValue());
                LinkedList<Entry> tempEntries = new LinkedList<>();
                for (int i = 0; i < moveSize; i++) {
                    Entry oldEntry = oldDataSet.baseEntries.get(i);
                    Entry newEntry = newDataSet.baseEntries.get(i);
                    float translationX = newEntry.getX() - oldEntry.getX();
                    float translationY = newEntry.getY() - oldEntry.getY();
                    Entry tempEntry = new Entry(oldEntry.getX() + (float) (animation.getAnimatedValue()) * translationX,
                            oldEntry.getY() + (float) (animation.getAnimatedValue()) * translationY);
//                    Log.d(TAG, "onAnimationUpdate: "+tempEntry);
                    tempEntries.add(tempEntry);
                }
                // step: 如果是增多
                if (moveSize < newSize) {
                    float leftX = oldDataSet.baseEntries.get(moveSize - 1).getX();
                    float leftY = oldDataSet.baseEntries.get(moveSize - 1).getY();
                    for (int i = moveSize; i < newSize; i++) {
                        Entry newEntry = newDataSet.baseEntries.get(i);
                        float translationX = newEntry.getX() - leftX;
                        float translationY = newEntry.getY() - leftY;
                        Entry tempEntry = new Entry(leftX + (float) (animation.getAnimatedValue()) * translationX
                                , leftY + (float) (animation.getAnimatedValue()) * translationY);
//                    Log.d(TAG, "onAnimationUpdate: "+tempEntry);
                        tempEntries.add(tempEntry);
                    }
                }

                tempDataSet.setEntries(DataConverter.convertToCurve(tempEntries));
                dataSetMap.put(newDataSet.getTag(), tempDataSet);
                if (onAnimDataChangeListener != null) {
                    onAnimDataChangeListener.onAnimDataChanged();
                }
            }
        });
        animator.setDuration(500);
        animator.start();

    }
}
