package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import java.util.HashMap;

/**
 * Handles all attachments to the robot
 */
public class ManipulationManager extends FeatureManager {

    public ManagedServo upServo;
    public ManagedServo downServo;

    private PointNd points;
    private TrigCache cache;
    private ElapsedTime timer;
    private double lastRecordTime;

    public ManipulationManager(Servo servo, DcMotor lift) {
        //this.servo = new Servo();

        this.cache = new TrigCache();
        this.points = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();
    }

    /**
     * Sets power of servo
     * @param servo servo of choice
     * @param power power of choice
     */
    public void setServoPower(Servo servo, double power) {
        servo.setPosition(servo.getPosition() + 0.1);
    }

    /**
     * Sets position of servo
     * @param servo servo of choice
     * @param position position of choice
     */
    public void setServoPosition(Servo servo, double position) {
        servo.setPosition(position);
    }

    /**
     * Raises lift
     * @param lift for lift
     */
    public void raiseLift(DcMotor lift) {
        lift.setPower(.5);
    }

    /**
     * Lowers lift
     * @param lift for lift
     */
    public void lowerLift(DcMotor lift) {
        lift.setPower(-.5);
    }

}