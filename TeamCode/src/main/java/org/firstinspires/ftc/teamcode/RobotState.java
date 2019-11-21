package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class RobotState {
    public static RobotState current;

    public float time;
    public ElapsedTime timer;
    public HardwareMap hardwareMap;

    public RobotState (HardwareMap hwm, ElapsedTime tmr) {
        this.hardwareMap = hwm;
        this.timer = tmr;
        this.time = (float)timer.milliseconds();
    }

    public float get(String key) {
        this.time = (float)timer.milliseconds();
        if(key == "TIME") return this.time;
        return 0f;
    }
}
