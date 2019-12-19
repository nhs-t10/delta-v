package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.auxillary.*;
import java.util.HashMap;

/**
 * Handle all movement of the chassis.
 */
public class MovementManager extends FeatureManager {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

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
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backRight = br;
        this.backLeft = bl;

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
    public void driveRaw(float fl, float fr, float br, float bl) {
        frontLeft.setPower(fl*SPEED);
        backRight.setPower(br*SPEED);
        frontRight.setPower(fr*SPEED);
        backLeft.setPower(bl*SPEED);
    }

    /**
     * Drives using vert, hor, rot inputs.
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    //confusing names we are trubleshooting
    public float[] omniCalc(float verticalPower, float horizontalPower, float rotationalPower) {
        float lx = Range.clip(horizontalPower, -1, 1);
        float lY = Range.clip(verticalPower, -1, 1);
        float rx = Range.clip(rotationalPower, -1, 1);
        
        // Motor powers of fl, fr, br, bl
        // Motor powers used to be 0.4f for all motors other than fl
        float[] vertical = {-lY, lY, -lY, lY};
        float[] horizontal = {lx, 0.7f*lx, -0.7f*lx, -0.7f*lx};
        float[] rotational = {rx, rx, rx, rx};

        float[] sum = new float[4];
        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }
        //This makes sure that no value is greater than 1 by dividing all of them by the maximum
        float highest = Math.max(Math.max(sum[0], sum[1]), Math.max(sum[2], sum[3]));
        if (Math.abs(highest) > 1) {
            for (int i = 0; i < 4; i++) {
                sum[i] = sum[i] / highest;
            }
        }
        return sum;
    }

    /**
     * Drives based on inputs
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    public void driveOmni(float horizontalPower, float verticalPower, float rotationalPower) {
        float [] sum = omniCalc(verticalPower, horizontalPower, rotationalPower);

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
    /**
     * 
     * @param move The direction to move in.
     */
    public void driveOmni(MovementOrder move) {
        this.driveOmni(move.getHor(), move.getVer(), move.getRot());
    }

    /**
     * Slows down the robot via a rational function
     * Works only if all inputs are 0
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
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
    /**
     * Activates if all readings are = 0
     * Records motor values
     * Decreases motor values with an exponential function
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
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
    /**
     * Creates a hashmap of all the motors powers and lines them up by time. 
     * @return a map where the key is the time and the value is an array of motor powers.
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    public HashMap<String,float[]> powersHashMap(float horizontalPower, float verticalPower, float rotationalPower) {
        float[] sum = omniCalc(horizontalPower, verticalPower, rotationalPower);
        HashMap<String, float[]> powersHashMap = new HashMap<String, float[]>();
        powersHashMap.put(timer.milliseconds() + "", sum);
        return powersHashMap;
    }
    /**
     * Calls sum array from 100 miliseconds ago
     * Takes the average of the current sum array and the past sum array 
     * Applies average sum to motor powers
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    public void moveDriftingAverage(float horizontalPower, float verticalPower, float rotationalPower) {
        HashMap<String, float[]> powersHashMap = powersHashMap(horizontalPower, verticalPower, rotationalPower);
        float[] currentSum = omniCalc(horizontalPower, verticalPower, rotationalPower);
        float[] pastSum = powersHashMap.get(timer.milliseconds()-100 + "");
        for (int i = 0; i < 4; i++) {
            currentSum[i] = (currentSum[i] + pastSum[i])/2;
        }
        driveRaw(currentSum[0], currentSum[1], currentSum[2], currentSum[3]);
    }

    public void resetEncoders(DcMotor motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void driveEncoder(float rotFL, float rotFR, float rotBL, float rotBR) {
        
        frontLeft.setTargetPosition((int)rotFL*1680);
        frontRight.setTargetPosition((int)rotFR*1680);
        backLeft.setTargetPosition((int)rotBL*1680);
        backRight.setTargetPosition((int)rotBR*1680);


    }
}


