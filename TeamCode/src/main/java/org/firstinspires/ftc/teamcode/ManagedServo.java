package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import java.util.HashMap;

/**
 * Handles all servos
 */
public class ManagedServo {

    private PointNd points;
    private TrigCache cache;
    private ElapsedTime timer;
    private Servo servo;
    private double lastRecordTime;

    public ManagedServo(Servo _servo) {
        this.servo = _servo;

        this.cache = new TrigCache();
        this.points = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();
    }

    public void setServoPosition(double position) {
       servo.setPosition(position);
    }
    public double getServoPosition() {
        return servo.getPosition();
    }
}