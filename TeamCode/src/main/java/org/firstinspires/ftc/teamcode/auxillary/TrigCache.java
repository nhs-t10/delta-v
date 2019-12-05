package org.firstinspires.ftc.teamcode.auxillary;

import java.util.HashMap;

public class TrigCache {
    public HashMap<Float, Float> sincache;
    public HashMap<Float, Float> coscache;
    public HashMap<Float, Float> tancache;

    public TrigCache () {
        sincache = new HashMap<Float, Float>();
        coscache = new HashMap<Float, Float>();
        tancache = new HashMap<Float, Float>();
    }

    public float sin(float in) {
        if(sincache.containsKey(in)) {
            return sincache.get(in);
        } else {
            float result = (float)Math.sin(in);
            sincache.put(in, result);
            return result;
        }
    }
    public float cos(float in) {
        if(sincache.containsKey(in)) {
            return sincache.get(in);
        } else {
            float result = (float)Math.cos(in);
            sincache.put(in, result);
            return result;
        }

    }
    public float tan(float in) {
        if(sincache.containsKey(in)) {
            return sincache.get(in);
        } else {
            float result = (float)Math.tan(in);
            sincache.put(in, result);
            return result;
        }

    }
}
