package org.firstinspires.ftc.teamcode.scripting;

enum Operation {
    LESS, LESS_EQUAL, EQUAL, MORE_EQUAL, MORE
}
public class ScriptConstraint {
    public final static String TOKEN = "";

    public String operator;
    public org.firstinspires.ftc.teamcode.scripting.Operation operation;
    public float target;

    public static boolean process(float current, org.firstinspires.ftc.teamcode.scripting.Operation operation, float target) {
        switch(operation) {
            case LESS: return current < target;
            case LESS_EQUAL: return current <= target;
            case EQUAL: return current == target;
            case MORE_EQUAL: return current >= target;
            case MORE: return current > target;
        }
        return false;
    }

    public boolean test() {
        return false;
    }
    public boolean test(float target) {
        return false;
    }
    public ScriptConstraint() {}
}

class TimeScriptConstraint extends ScriptConstraint {
    public final static String TOKEN = "TIME";

    private float initialTime = -1f;

    public boolean test(float time) {
        if(initialTime == -1) initialTime = time;
        return org.firstinspires.ftc.teamcode.scripting.ScriptConstraint.process(time - initialTime, this.operation, this.target);
    }

    public TimeScriptConstraint(String arg) {

        this.operator = "TIME";
        this.operation = org.firstinspires.ftc.teamcode.scripting.Operation.MORE_EQUAL;
        this.target = Float.parseFloat(arg);
    }
}