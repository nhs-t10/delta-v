package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.autonomous.step.BLEncoder;
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
    boolean driveStarted;
    public int iters = 0;

    /**
     * Create a MovementManager with four motors.
     * @param fl Front Left motor
     * @param fr Front Right motor
     * @param br Back Right motor
     * @param bl Back Left motor
     */

    private static float speed = 1.0f;
    private int avg;

    private LinearOpMode opMode;

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

    public MovementManager(DcMotor fl, DcMotor fr, DcMotor br, DcMotor bl, LinearOpMode _opMode) {
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backRight = br;
        this.backLeft = bl;

        this.cache = new TrigCache();
        this.currentLocation = new PointNd(0f,0f,0f);
        this.timer = new ElapsedTime();
        this.lastRecordTime = timer.milliseconds();

        this.opMode = _opMode;
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
        frontLeft.setPower(fl*speed);
        backRight.setPower(br*speed);   //Austin will defin "speed" later
        frontRight.setPower(fr*speed);
        backLeft.setPower(bl*speed);
    }





    /**
     * Drives based on inputs
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    public void driveOmni(float horizontalPower, float verticalPower, float rotationalPower) {
        float [] sum = PaulMath.omniCalc(verticalPower, horizontalPower, rotationalPower);

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
        float[] sum = PaulMath.omniCalc(horizontalPower, verticalPower, rotationalPower);
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
        float[] currentSum = PaulMath.omniCalc(horizontalPower, verticalPower, rotationalPower);
        float[] pastSum = powersHashMap.get(timer.milliseconds()-100 + "");
        for (int i = 0; i < 4; i++) {
            currentSum[i] = (currentSum[i] + pastSum[i])/2;
        }
        driveRaw(currentSum[0], currentSum[1], currentSum[2], currentSum[3]);
    }

    public void resetEncoders(DcMotor motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}
    public void resetEncoderMode(DcMotor motor) {
//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public  void resetAllEncoders() {
        this.resetEncoders(frontLeft);
        this.resetEncoders(frontRight);
        this.resetEncoders(backLeft);
        this.resetEncoders(backRight);
    }

    public void setAllEncoderModes(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    public void resetAllEncoderModes() {
        this.resetEncoderMode(frontLeft);
        this.resetEncoderMode(frontRight);
        this.resetEncoderMode(backLeft);
        this.resetEncoderMode(backRight);
    }


    private static float speedAuto = 0.5f;

    public void driveAuto(float fl, float fr, float br, float bl) {
        frontLeft.setPower(fl*speedAuto);
        backRight.setPower(br*speedAuto);
        frontRight.setPower(fr*speedAuto);
        backLeft.setPower(bl*speedAuto);
    }

    public void backLeftSetTargetPosition(int position) {
        backRight.setTargetPosition(position);
    }
    public void backRightSetTargetPosition(int position) {
        backLeft.setTargetPosition(position);
    }

    public boolean driveVertical(float power, float rotation, LinearOpMode logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");
        if(!driveStarted) {
            this.resetAllEncoders();

            frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
            frontRight.setTargetPosition(-(int) rotation * TICK_PER_ROT);
            backRightSetTargetPosition(-(int) rotation * TICK_PER_ROT);
            backLeftSetTargetPosition((int) rotation * TICK_PER_ROT);


            this.resetAllEncoderModes();
            driveStarted = true;

            logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));
        }
        else if(
                (Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) ||
                Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) ||
                Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) ||
                Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition()))
        ) {
            this.driveAuto(power, power, power, power);
            logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        } else {
            driveAuto(0f, 0f, 0f, 0f);
            logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
            driveStarted = false;
            return false;
        }

        return true;
    }

    public boolean driveHorizontal(float power, float rotation, BLEncoder logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");
        if(!driveStarted) {
            this.resetAllEncoders();



            frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
            frontRight.setTargetPosition((int) rotation * TICK_PER_ROT);
            backRightSetTargetPosition(-(int) rotation * TICK_PER_ROT);
            backLeftSetTargetPosition(-(int) rotation * TICK_PER_ROT);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);



            this.resetAllEncoderModes();
            driveStarted = true;

            logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));
        }
        else if(Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) ||
                Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) ||
                Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) ||
                Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition()) ) {
            this.driveAuto(power, power, power, power);
            logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        } else {
            driveAuto(0f, 0f, 0f, 0f);
            logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
            driveStarted = false;
            return false;
        }

        return true;
    }

    public void driveWhileHorizontal(float power, float rotation, LinearOpMode logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");

        this.resetAllEncoders();

        frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
        frontRight.setTargetPosition((int) rotation * TICK_PER_ROT);
        backRightSetTargetPosition(-(int) rotation * TICK_PER_ROT);
        backLeftSetTargetPosition(-(int) rotation * TICK_PER_ROT);



        this.resetAllEncoderModes();
        driveStarted = true;

       // logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));

        while(
                Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            this.driveAuto(power, power, -power, -power);
            iters++;
           // logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        }
        driveAuto(0f, 0f, 0f, 0f);
       // logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
        driveStarted = false;
    }

    public void driveWhileVertical(float power, float rotation, LinearOpMode logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");

        this.resetAllEncoders();

        frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
        frontRight.setTargetPosition(-(int) rotation * TICK_PER_ROT);
        backRightSetTargetPosition(-(int) rotation * TICK_PER_ROT);
        backLeftSetTargetPosition((int) rotation * TICK_PER_ROT);



        this.resetAllEncoderModes();
        driveStarted = true;

        logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));

        while(
                Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            this.driveAuto(power,-power, power, -power);
            logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        }
        driveAuto(0f, 0f, 0f, 0f);
        logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
        driveStarted = false;
    }
    public void driveWhileVerticalPid(float power, float rotation, OpMode logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");

        this.resetAllEncoders();

        frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
        frontRight.setTargetPosition((int) rotation * TICK_PER_ROT);
        backRightSetTargetPosition((int) rotation * TICK_PER_ROT);
        backLeftSetTargetPosition((int) rotation * TICK_PER_ROT);



        this.resetAllEncoderModes();
        driveStarted = true;

        logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));

        while(
                Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                        Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                        Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                        Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            avg = (frontLeft.getCurrentPosition() +
                    frontRight.getCurrentPosition() +
                    backRight.getCurrentPosition() +
                    backLeft.getCurrentPosition())/4;

            frontLeft.setPower(SPEED + (avg - frontLeft.getCurrentPosition()) * P);
            frontRight.setPower(-(SPEED + (avg - frontRight.getCurrentPosition()) * P));
            backRight.setPower(SPEED + (avg - backRight.getCurrentPosition()) * P);
            backLeft.setPower(-(SPEED + (avg - backLeft.getCurrentPosition()) * P));

            logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        }
        driveAuto(0f, 0f, 0f, 0f);
        logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
        driveStarted = false;
    }
    public void driveWhileHorizontalPid(float power, float rotation, OpMode logger) {

        logger.telemetry.addData("mvm encoder drive state init", "0");
        logger.telemetry.addData("mvm encoder drive state drive", "0");
        logger.telemetry.addData("mvm encoder drive state stop", "0");

        this.resetAllEncoders();

        frontLeft.setTargetPosition((int) rotation * TICK_PER_ROT);
        frontRight.setTargetPosition((int) rotation * TICK_PER_ROT);
        backRightSetTargetPosition((int) rotation * TICK_PER_ROT);
        backLeftSetTargetPosition((int) rotation * TICK_PER_ROT);



        this.resetAllEncoderModes();
        driveStarted = true;

        logger.telemetry.addData("mvm encoder drive state init", "init" + (System.currentTimeMillis() / 100000));

        while(
                Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                        Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                        Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                        Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            avg = (frontLeft.getCurrentPosition() +
                    frontRight.getCurrentPosition() +
                    backRight.getCurrentPosition() +
                    backLeft.getCurrentPosition())/4;

            frontLeft.setPower(SPEED + (avg - frontLeft.getCurrentPosition()) * P);
            frontRight.setPower(SPEED + (avg - frontRight.getCurrentPosition()) * P);
            backRight.setPower(-(SPEED + (avg - backRight.getCurrentPosition()) * P));
            backLeft.setPower(-(SPEED + (avg - backLeft.getCurrentPosition()) * P));

            logger.telemetry.addData("mvm encoder drive state drive", "drive" + (System.currentTimeMillis() / 100000));
            //Waiting for motor to finish
        }
        driveAuto(0f, 0f, 0f, 0f);
        logger.telemetry.addData("mvm encoder drive state stop", "stop" + (System.currentTimeMillis() / 100000));
        driveStarted = false;
    }

    public void resetBool(){
        driveStarted = false;
    }

    ElapsedTime moveTimer;

    public void encoderDrive(double speed,
                             double fl, double fr, double bl, double br,
                             double timeoutS, LinearOpMode opMode) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;
        int newBackLeftTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {



            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int)(fl * TICK_PER_ROT);
            newFrontRightTarget = frontRight.getCurrentPosition() + (int)(fr * TICK_PER_ROT);
            newBackLeftTarget = backRight.getCurrentPosition() + (int)(bl * TICK_PER_ROT);
            newBackRightTarget = backLeft.getCurrentPosition() + (int)(br * TICK_PER_ROT);
            frontLeft.setTargetPosition(newFrontLeftTarget);
            frontRight.setTargetPosition(newFrontRightTarget);
            backLeft.setTargetPosition(newBackRightTarget);
            backRight.setTargetPosition(newBackLeftTarget);

            // Turn On RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // reset the timeout time and start motion.
            moveTimer = new ElapsedTime();

            frontLeft.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (moveTimer.milliseconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy() && backLeft.isBusy())) {

                // Display it for the driver.
                opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget, newBackLeftTarget, newBackRightTarget);
                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(),
                        backLeft.getCurrentPosition(),
                        backRight.getCurrentPosition());
                opMode.telemetry.update();
            }

            // Stop all motion;
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            //  sleep(250);   // optional pause after each move
        }
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }
    public float getIterations(){
        return iters;
    }


}
