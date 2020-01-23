package org.firstinspires.ftc.teamcode;

public class FeatureManager {
    public final static float SPEED = 0.6f;
    public final static int TICK_PER_ROT = 1680;

    public final static float INPUT_FINETUNE_SCALE = 2f;
    public final static int INPUT_DOUBLECLICK_TIME = 400;

    public final static float LIFT_RAISE_LOWER_SPEED = 0.4f;
    public final static float LIFT_CLAMP_OPEN_POS = 0f;
    public final static float LIFT_CLAMP_CLOSE_POS = 1f;

    public final static int MIMING_MS_PER_SAMPLE = 10;
    public final static String MIMING_FILENAME = "miming.txt";

    public final static float P = 0.03f;
    public final static String MIMING_FR = "mimingfr.txt";
    public final static String MIMING_FL = "mimingfr.txt";
    public final static String MIMING_BL = "mimingbl.txt";
    public final static String MIMING_BR = "mimingbr.txt";

    public boolean isInputType;
    public FeatureManager() {
    
    }
}