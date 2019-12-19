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

    public void setLiftState(float[] powers) {

        this.setServoPosition(powers[1]);
        liftMotor.setPower(powers[0]);

    }

    /**
     * Sets power of servo
     * @param power power of choice
     */
    public void setServoPower(double power) {
        grabServo.setServoPosition(grabServo.getServoPosition() + 0.1f * power);
    }

    /**
     * Sets position of servo
     * @param position position of choice
     */
    public void setServoPosition(double position) {
        grabServo.setServoPosition(position);
    }

    /**
     * Raises lift
     */
    public void raiseLift() {
        liftMotor.setPower(0.16);
    }

    /**
     * Lowers lift
     */
    public void lowerLift() {
        liftMotor.setPower(-0.16);
    }

    /**
     * Stops lift
     */
    public void stopLift() {
        liftMotor.setPower(0);
    }

}