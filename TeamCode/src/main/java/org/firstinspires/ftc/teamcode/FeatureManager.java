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
        public final static Control MOVE_HORIZONTAL = new Control("left_stick_x");
        public static final Control MOVE_VERTICAL = new Control("left_stick_y");
        public static final Control MOVE_ROTATIONAL = new Control("right_stick_x");

        public static final Control CLAMP_MAIN = new Control("right_trigger", 1f, 0f);
        public static final Control CLAMP_SIDE = new Control(0f);

        public static final Control LIFT_MAIN = new Control("b", "a", -1f, 1f, 0f);
        public static final Control LIFT_SIDE = new Control(0f);

        public static final Control FOUNDATION_MOVER = new Control(1f);

        public static final Control TELE_TAB = new Control("dpad_left", "dpad_right", -1f, 1f, 0f);

        public static final Control DROPPER = new Control("x","y",1f,0f, 0f);

        public static final Control SPEED_TOGGLE = new Control("left_bumper",0.25f,1f);
    }
}
enum ControlType {
    TOGGLE,
    SCALAR,
    PUSHBETWEEN
}
class Control {
    public String on;
    public String off;

    public boolean isScalar;
    public float value1;
    public float value2;
    public float value3;

    ControlType type;

    boolean isNothing;

    public Control(String _on, String _off, float v1, float v2, float v3) {
        this.on = _on;
        this.off = _off;

        this.value1 = v1;
        this.value2 = v2;
        this.value3 = v3;
        this.isScalar = false;
    }
    public Control(String _on, float v1, float v2) {
        this.on = _on;
        this.off = "";

        this.value1 = v1;
        this.value2 = v2;
        this.isScalar = false;
    }

    public Control(String _on) {
        this.on = _on;
        this.off = "";
        this.isScalar = true;
    }

    public Control(float val) {
        this.on = "";
        this.off = "";

        this.isNothing = true;
        this.value1 = val;
    }


}