//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
//import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
//
//import org.firstinspires.ftc.teamcode.*;
//import org.firstinspires.ftc.teamcode.data.*;
//import org.firstinspires.ftc.teamcode.auxillary.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Make Telemetry pretty
// */
//public class TelemetryManager extends FeatureManager {
//
//    public OpMode opMode;
//
//    public int currentTab;
//
//    public ArrayList<String> keys;
//    public ArrayList<String> values;
//
//    public HashMap<String, boolean> otflags;
//
//    private RobotState lastState;
//    /**
//     * Create a TelemetryManager with four motors.
//     * @param fl Front Left motor
//     * @param fr Front Right motor
//     * @param br Back Right motor
//     * @param bl Back Left motor
//     */
//    public TelemetryManager(OpMode opmode) {
//
//        this.opMode = _opmode;
//        this.currentTab = 0;
//
//        keys = new ArrayList<String>();
//        values = new ArrayList<String>();
//        otflags = new HashMap<String, boolean>();
//    }
//
//
//    public void update(RobotState state) {
//        this.lastState = state;
//        this.update();
//    }
//
//    public void oneTimeFlag(String flag) {
//        otflags.put(flag, true);
//    }
//
//    public void update() {
//        String[] arrKeys = keys.toArray();
//
//        if(currentTab == 1) {
//            //OpMode-inserted values
//            for(int i = 0; i < arrKeys.length; i++) {
//                String key = arrKeys[i];
//                String val = values.get(i);
//
//                opMode.telemetry.addData(key, val);
//            }
//            //one-time flags
//            for()
//        } else if(currentTab == 0) {
//            telemetry
//        }
//
//        opMode.telemetry.update();
//    }
//
//
//
//    public TelemetryManager(){ }
//
//}