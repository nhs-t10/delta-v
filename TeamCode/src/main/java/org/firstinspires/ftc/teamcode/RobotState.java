package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class RobotState {
    public static RobotState current;

    public float time;
    public HardwareMap hardwareMap;

    public RobotState (HardwareMap hwm, ElapsedTime timer) {
        this.hardwareMap = hwm;
        this.time = timer.milliseconds();
    }

    public float get(String key) {
        this.time = timer.milliseconds();
        if(key == "TIME") return this.time;
    }
}
