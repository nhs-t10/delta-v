package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Make Telemetry pretty
 */
public class TelemetryManager extends FeatureManager {

    public OpMode opMode;

    public int currentTab;

    public HashMap<String, String> opModeDatas;

    public static String[] tabNames = {"Driver", "OpMode", "Robot"};

    private RobotState lastState;
    private double lastUpdateTime;
    /**
     * Create a TelemetryManager.
     */
    public TelemetryManager(OpMode _opMode) {

        this.opMode = _opMode;
        this.currentTab = 0;

        this.opModeDatas = new HashMap<String, String>();
    }


    public void update(RobotState state) {
        this.lastState = state;
        this.lastUpdateTime = System.currentTimeMillis();
        this.update();
    }

    public void switchTab(int switchTabDelta) {
        int _tab = currentTab;

        _tab += switchTabDelta;
        if(_tab < 0) _tab = 2;
        if(_tab > 2) _tab = 0;

        this.currentTab = _tab;
    }


    private void update() {

        //display current tab
        opMode.telemetry.addData("", TelemetryManager.tabDisplay(tabNames, currentTab));

        if(currentTab == 0) {
            //competition-useful driver data
            opMode.telemetry.addData("Speed", lastState.speed * 100 + "%");
            opMode.telemetry.addData("Last Updated", TelemetryManager.durationFormat((float)(System.currentTimeMillis() - this.lastUpdateTime)));
            opMode.telemetry.addData("Horizontal Movement",TelemetryManager.meter(lastState.movement.getHor(), 1f, 8));
            opMode.telemetry.addData("Have we moved the foundation?", "No (50% accurate)");
        } if(currentTab == 1) {
            //OpMode-inserted values
            for (Map.Entry<String, String> entry : opModeDatas.entrySet()) {
                opMode.telemetry.addData(entry.getKey(), entry.getValue());
            }
        } else if(currentTab == 2) {
            //Robot state
            opMode.telemetry.addData("FL Power: ", lastState.flDrivePower);
            opMode.telemetry.addData("FR Power: ", lastState.frDrivePower);
            opMode.telemetry.addData("BL Power: ", lastState.blDrivePower);
            opMode.telemetry.addData("BR Power: ", lastState.brDrivePower);

            opMode.telemetry.addData("Horizontal", lastState.movement.getHor());
            opMode.telemetry.addData("Vertical", lastState.movement.getVer());
            opMode.telemetry.addData("Rotational", lastState.movement.getRot());

            opMode.telemetry.addData("Lift Motor", lastState.liftMotorPower);
            opMode.telemetry.addData("Lift Servo Grabber", lastState.liftServoPos);

            opMode.telemetry.addData("IMU Orientation", lastState.orientation.toString());
            opMode.telemetry.addData("IMU Orientation", lastState.position.toString());
        }
        opMode.telemetry.update();
    }

    public void addData(String key, String val) {
        opModeDatas.put(key, val);
    }

    public static String meter(float current, float full, int length) {
        return "[" + TelemetryManager.repeatStr("|", Math.round((current/full) * length)) + "]";
    }

    public static String durationFormat(float durationMs) {
        if(durationMs < 1000) return "just now";
        else if(durationMs < 5000) return "a few seconds ago";
        else if(durationMs < 10000) return "a little while ago";
        else return "an eternity ago in computer terms";
    }
    public static String repeatStr(String str, int count) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < count; i++) res.append(str);
        return res.toString();
    }

    public static String tabDisplay(String[] options, int current) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < options.length; i++) {
            if(current == i) res.append("<").append(options[i]).append(">");
            else res.append("[").append(options[i]).append("]");

            if(i + 1 < options.length) res.append("|");
        }
        return res.toString();
    }
    public TelemetryManager(){ }

}