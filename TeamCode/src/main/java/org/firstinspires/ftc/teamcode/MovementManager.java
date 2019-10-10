package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

/**
 * Handle all movement of the chassis.
 */
public class MovementManager extends FeatureManager {
    public EncodedMotor frontLeft;
    public EncodedMotor frontRight;
    public EncodedMotor backLeft;
    public EncodedMotor backRight;

    private PointNd currentLocation;
    private TrigCache cache;
    private ElapsedTime timer;
    private double lastRecordTime;
    /**
     * Create a MovementManager with four motors.
     * @param fl Front Left motor
     * @param fr Front Right motor
     * @param br Back Right motor
     * @param bl Back Left motor
     */
    public MovementManager(DcMotor fl, DcMotor fr, DcMotor br, DcMotor bl) {
        this.frontLeft = new EncodedMotor(fl);
        this.frontRight = new EncodedMotor(fr);
        this.backRight = new EncodedMotor(br);
        this.backLeft = new EncodedMotor(bl);

        this.cache = new TrigCache();
        this.currentLocation = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();
    }
    public MovementManager(){ }

    /**
     * Set raw motor powers. Likely not needed to be used publicly
     * @param fl Front left motor power
     * @param fr Front right motor power
     * @param br Back right motor power
     * @param bl Back left motor power
     */
    private void driveRaw(float fl, float fr, float br, float bl) {
        frontLeft.setPower(Range.clip(-fl, -1, 1)*SPEED);
        backRight.setPower(Range.clip(br, -1, 1)*SPEED);
        frontRight.setPower(Range.clip(fr, -1, 1)*SPEED);
        backLeft.setPower(Range.clip(-bl, -1, 1)*SPEED);
    }

    /**
     * Drives using vert, hor, rot inputs.
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    public void driveOmni(float horizontalPower, float verticalPower, float rotationalPower) {
        float lX = Range.clip(horizontalPower, -1, 1);
        float lY = Range.clip(verticalPower, -1, 1);
        float rX = Range.clip(rotationalPower, -1, 1);

        float[] vertical = {0.7f * lY, 0.7f * lY, 0.7f * lY, 0.7f * lY};
        float[] horizontal = {lX, -lX, lX, -lX};
        float[] rotational = {-0.7f * rX, -0.7f * rX, 0.7f * rX, 0.7f * rX};

        float[] sum = new float[4];
        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }

        float highest = Math.max(Math.max(sum[0], sum[1]), Math.max(sum[2], sum[3]));
        if (Math.abs(highest) > 1) {
            for (int i = 0; i < 4; i++) {
                sum[i] = sum[i] / highest;
            }


        }

        //record current position
        double timeSinceLastRecordTime = timer.milliseconds() - lastRecordTime;
        float diffHor = horizontalPower * (float)timeSinceLastRecordTime;
        float diffVer = verticalPower * (float)timeSinceLastRecordTime;
        float diffRot = horizontalPower * (float)timeSinceLastRecordTime;

        currentLocation.transform(new PointNd(diffHor, diffVer, 0f));

        /* makes it go vroom*/
        driveRaw(sum[0], sum[1], sum[2], sum[3]);
    }

    /**
     * Alias for driveOmni() to make code prettier
     * @param powers Array with 3 items: horizontal power, vertical power, rotational power.
     * @see #driveOmni(float, float, float)
     */
    public void driveOmni(float[] powers) {
        this.driveOmni(powers[0], powers[1], powers[2]);
    }

    public void moveDriftingRational(float horizontalPower, float verticalPower, float rotationalPower) {
        float stopFl = (float)frontLeft.getPower();
        float stopFr = (float)frontRight.getPower();
        float stopBl = (float)backLeft.getPower();
        float stopBr = (float)backRight.getPower();
        int counter = 1;
        if (horizontalPower == 0 && verticalPower == 0 ) {
            while(Math.abs(frontLeft.getPower()) < 0.0001 && Math.abs(frontRight.getPower()) < 0.0001 && Math.abs(backLeft.getPower()) < 0.0001 && backRight.getPower() < 0.0001) {
                frontLeft.setPower(stopFl/counter);
                frontRight.setPower(stopFr/counter);
                backLeft.setPower(stopBl/counter);
                backRight.setPower(stopBr/counter);
                counter++;
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

        }

    }
    public void moveDriftingExponential(float horizontalPower, float verticalPower, float rotationalPower) {
        float stopFl = (float)frontLeft.getPower();
        float stopFr = (float)frontRight.getPower();
        float stopBl = (float)backLeft.getPower();
        float stopBr = (float)backRight.getPower();
        int counter = 1;
        if (horizontalPower == 0 && verticalPower == 0 ) {
            while(Math.abs(frontLeft.getPower()) < 0.0001 && Math.abs(frontRight.getPower()) < 0.0001 && Math.abs(backLeft.getPower()) < 0.0001 && backRight.getPower() < 0.0001) {
                frontLeft.setPower(Math.pow(stopFl, counter));
                frontRight.setPower(Math.pow(stopFr, counter));
                backLeft.setPower(Math.pow(stopBl, counter));
                backRight.setPower(Math.pow(stopBr, counter));
                counter++;
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

        }

    }
}
