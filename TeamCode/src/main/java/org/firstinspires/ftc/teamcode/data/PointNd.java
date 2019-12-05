package org.firstinspires.ftc.teamcode.data;

import java.util.ArrayList;

public class PointNd {
    public ArrayList<Float> dimensions;
    public int dimensionCount;

    /**
     * Make a 3-dimensional point.
     */
    public PointNd (float x, float y, float z) {
        this.dimensionCount = 3;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
        this.dimensions.add(z);
    }
    /**
     * Make a 2-dimensional point.
     */
    public PointNd (float x, float y) {
        this.dimensionCount = 2;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
    }
    /**
     * Make a 4-dimensional point.
     */
    public PointNd (float x, float y, float z, float w) {
        this.dimensionCount = 4;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
        this.dimensions.add(z);
        this.dimensions.add(w);
    }
    /**
     * Make an n-dimensional point.
     */
    public PointNd(float[] initialVals) {
        this.dimensionCount = initialVals.length;

        this.dimensions = new ArrayList<Float>();
        for(int i = 0; i < initialVals.length; i++) {
            this.dimensions.add(initialVals[i]);
        }
    }
    public PointNd() {}

    public float getDim(int dimIndex) {
        return dimensions.get(dimIndex);
    }
    public void transform (PointNd transform) {
        for(int i = 0; i < dimensionCount; i++) {
            float temp = dimensions.get(i);
            dimensions.set(i, temp + transform.getDim(i));
        }
    }
    public void setDim(int dimIndex, float newValue) {
        dimensions.set(dimIndex, newValue);
    }
    public float getX() {
        return dimensions.get(0);
    }
    public float getY() {
        return dimensions.get(1);
    }
    public float getZ() {
        return dimensions.get(2);
    }
}
