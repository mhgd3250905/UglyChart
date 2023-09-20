package com.bboyuglyk.chart_sdk;

import java.util.HashMap;
import java.util.LinkedList;

public class HighLighter {
    private float pX;
    private HashMap<DataType, LinkedList<Entry>> nearEntriesMap;

    public HighLighter(float pX, HashMap<DataType, LinkedList<Entry>> nearEntriesMap) {
        this.pX = pX;
        this.nearEntriesMap = nearEntriesMap;
    }

    public float getpX() {
        return pX;
    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public HashMap<DataType, LinkedList<Entry>> getNearEntriesMap() {
        return nearEntriesMap;
    }

    public void setNearEntriesMap(HashMap<DataType, LinkedList<Entry>> nearEntriesMap) {
        this.nearEntriesMap = nearEntriesMap;
    }

    public void clear(){
        this.pX=-1f;
        if (this.nearEntriesMap!=null) {
            this.nearEntriesMap.clear();
        }
    }

    @Override
    public String toString() {
        return "HighLighter{" +
                "pX=" + pX +
                ", nearEntriesMap=" + nearEntriesMap +
                '}';
    }
}
