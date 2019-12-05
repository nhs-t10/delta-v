package org.firstinspires.ftc.teamcode.data;

import android.os.Build;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;

public class RobotState {

    public final static String version = "1";
    public static RobotState current;

    public float time;
    public ElapsedTime timer;

    public MovementOrder movement;

    public float[] servoPos;

    public RobotState (ElapsedTime tmr, float[] _servoPos, MovementOrder _movement) {
        this.servoPos = _servoPos;
        this.timer = tmr;

        this.time = (float)timer.milliseconds();

        this.movement = _movement;
    }

    public float get(String key) {
        this.time = (float)timer.milliseconds();
        if(key == "TIME") return this.time;
        return 0f;
    }

    public String toString() {
        return RobotState.version + "," + movement.toString() + "," + servoPos.toString().substring(1, servoPos.toString().length() - 1);
    }

    public static RobotState fromString(String str) {
        String[] strSplit = str.split(",");
        if(strSplit[0] != RobotState.version) return new RobotState(new ElapsedTime(), new float[1], new MovementOrder());

        String[] strMove = Arrays.copyOfRange(strSplit, 1, 3);
        String[] strServo = Arrays.copyOfRange(strSplit, 4, strSplit.length - 1);

        MovementOrder order = MovementOrder.fromString(String.join(",", strMove));

        float[] servoPosFloats = new float[strServo.length];
        for(int i = 0; i < strServo.length; i++) {
            servoPosFloats[i] = Float.parseFloat(strServo[i]);
        }

        return new RobotState(new ElapsedTime(), servoPosFloats, order);
    }
}
