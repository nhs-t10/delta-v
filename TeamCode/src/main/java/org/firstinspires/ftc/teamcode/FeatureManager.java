package org.firstinspires.ftc.teamcode;

import java.util.HashMap;

public class FeatureManager {
    public final static float SPEED = 0.6f;
    public final static int TICK_PER_ROT = 1680;

    public final static float INPUT_FINETUNE_SCALE = 2f;
    public final static int INPUT_DOUBLECLICK_TIME = 400;

    public final static float LIFT_RAISE_LOWER_SPEED = 0.4f;
    public final static float LIFT_CLAMP_OPEN_POS = 0f;
    public final static float LIFT_CLAMP_CLOSE_POS = 1f;

    public final static int MIMING_MS_PER_SAMPLE = 10;
    public final static String MIMING_FILENAME = "mimingFoundation.txt";

    public final static float P = 0.03f;
    public final static String MIMING_FR = "mimingfr.txt";
    public final static String MIMING_FL = "mimingfr.txt";
    public final static String MIMING_BL = "mimingbl.txt";
    public final static String MIMING_BR = "mimingbr.txt";

    public boolean isInputType;
    public FeatureManager() {
    
    }
    public static class ControlMap {
        public final static String MOVE_HORIZONTAL = "left_stick_x";
        public static final String MOVE_VERTICAL = "left_stick_y";
        public static final String MOVE_ROTATIONAL = "right_stick_x";

        public static final String CLAMP_MAIN = "a";
        public static final String CLAMP_SIDE = "x";

        public static final String LIFT_MAIN = "b";
        public static final String LIFT_SIDE = "y";

        public static final String FOUNDATION_MOVER = "left_bumper";

        public static final String TAB_POSITIVE = "right_bumper";
        public static final String TAB_NEGATIVE = "";

        public static final String DROPPER = "dpad_left";
    }
}