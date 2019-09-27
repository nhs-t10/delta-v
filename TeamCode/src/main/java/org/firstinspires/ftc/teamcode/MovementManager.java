package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

/**
 * Handle all movement of the chassis.
 */
public class MovementManager extends FeatureManager {
    private EncodedMotor frontLeft;
    private EncodedMotor frontRight;
    private EncodedMotor backLeft;
    private EncodedMotor backRight;

    private Point3d currentLocation;
    private TrigCache cache;

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
        this.currentLocation = new Point3d(0f,0f,0f);
    }
    public MovementManager(){ }

    /**
     * Set raw motor powers. Likely not needed to be used publicly
     * @param fl Front left motor power
     * @param fr Front right motor power
     * @param br Back right motor power
     * @param bl Back left motor power
     */
    public void driveRaw(float fl, float fr, float br, float bl) {
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
}
