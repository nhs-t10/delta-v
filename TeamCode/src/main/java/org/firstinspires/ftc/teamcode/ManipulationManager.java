package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.firstinspires.ftc.teamcode.FeatureManager;
import org.firstinspires.ftc.teamcode.ManagedServo;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.HashMap;

/**
 * Handles all attachments to the robot
 */
public class ManipulationManager extends FeatureManager {

    public ManagedServo grabServo;

    public DcMotor liftMotor;

    public Servo sideGrab;
    public Servo sideLift;

    public Servo foundationGrabber;

    private PointNd points;
    private TrigCache cache;
    private ElapsedTime timer;
    private double lastRecordTime;

    public ManipulationManager(Servo servo, DcMotor lift) {
        this.grabServo = new ManagedServo(servo);
        this.liftMotor = lift;

        this.cache = new TrigCache();
        this.points = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();
    }

    public ManipulationManager(Servo _grabServo, DcMotor lift, Servo _sideGrab, Servo _sideLift, Servo _foundationGrabber) {
        this.grabServo = new ManagedServo(_grabServo);
        this.liftMotor = lift;

        this.sideGrab = _sideGrab;
        this.sideLift = _sideLift;

        this.foundationGrabber = _foundationGrabber;

        this.cache = new TrigCache();
        this.points = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();
    }

    public void setLiftState(float[] powers) {

        this.setServoPosition(powers[1]);
        liftMotor.setPower(powers[0]);
    }

    public void setSideLiftState(float[] powers) {

        this.setSideLiftPosition(powers[0]);
        this.setSideGrabberPosition(powers[1]);
    }

    /**
     * Sets position of servo
     * @param position position of choice
     */
    public void setServoPosition(double position) {
        grabServo.setServoPosition(position);
    }

    public void setSideLiftPosition(double position) {sideLift.setPosition(position);}
    public void setSideGrabberPosition(double position) {sideGrab.setPosition(position);}

    public double getSideLiftPosition() {return sideLift.getPosition();}
    public double getSideGrabberPosition() {return sideGrab.getPosition();}

    public void setFoundationGrabberPosition(double position) {sideGrab.setPosition(position);}

    public void setMotorPower(double power) { liftMotor.setPower(power); }

    public double getServoPosition() {return grabServo.getServoPosition();}
    public double getMotorPower() {return liftMotor.getPower();}

    public void setGrabbingState(boolean state) {
        if(state) grabServo.setServoPosition(1);
        else grabServo.setServoPosition(0);
    }
}