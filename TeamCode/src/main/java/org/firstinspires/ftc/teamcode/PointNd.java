package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class PointNd {
    public ArrayList<Float> dimensions;
    public int dimensionCount; 
    
    public PointNd Point3d(float x, float y, float z) {
        this.dimensionCount = initialVals.length;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
        this.dimensions.add(z);
    }
    public PointNd Point2d(float x, float y) {
        this.dimensionCount = initialVals.length;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
    }
    public PointNd Point4d(float x, float y, float z, float w) {
        this.dimensionCount = initialVals.length;
        
        this.dimensions = new ArrayList<Float>();
        this.dimensions.add(x);
        this.dimensions.add(y);
        this.dimensions.add(z);
        this.dimensions.add(w);
    }
    public PointNd(float[] initialVals) {
        this.dimensionCount = initialVals.length;

        this.dimensions = new ArrayList<Float>();
        for(var i = 0; i < initialVals.length; i++) {
            this.dimensions.add(initialVals[i]);
        }
    }
    public Point3d() {}

    public float getDim(int dimIndex) {
        return dimensions.get(dimIndex);
    }
}
