package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ColorSensor;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.TelemetryManager;
import org.firstinspires.ftc.teamcode.auxillary.PaulMath;
import org.firstinspires.ftc.teamcode.data.RobotState;

@Autonomous(group = "Step")
public class FullLeft extends LinearOpMode {
    MovementManager driver;
    ManipulationManager hands;
    ElapsedTime runtime = new ElapsedTime();
    ColorSensor sensor;
    ColorSensor sensorDown;
    boolean driveStarted;
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    TelemetryManager logger;
    private long delayRunTime;

    private ElapsedTime timer;

    private boolean delaying = false;

    public void wait(int delay) {
        if(!delaying) {
            timer = new ElapsedTime();
        }
        while(timer.milliseconds() <= delay && opModeIsActive()) {}

        delaying = false;
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber")
        );
        driver.resetAllEncoders();
        driver.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hands.sideLift.setPosition(0);

        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        //Step 1: Drive sideways to the row of blocks
        driver.driveWhileHorizontal(0.5f, PaulMath.encoderDistance(21), this);
        wait(10);

        //Step 2: scan along the line for a SkyStone(tm)
        double msStart = System.currentTimeMillis();
        driver.driveVertical(0.5f, 1f, this);

        while(!(sensor.isSkystone() || (System.currentTimeMillis() - msStart) > 1500) && opModeIsActive()) {}
        driver.resetAllEncoders();
        driver.driveAuto(0,0,0,0);
        //branching fallback path--
        if(!sensor.isSkystone()) {
            //Step 3 (fallback) go back and try again -only retries once
            msStart = System.currentTimeMillis();
            driver.driveRaw(1, -1, -1, 1);
            while(!(sensorDown.isBled() || (System.currentTimeMillis() - msStart) > 1000) && opModeIsActive()) {}

            //Step 4 (fallback stop)
            driver.driveAuto(0f, 0f, 0f,0f);
            wait(10);
            stop();
        }


        /*5*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(100);

        /*6*/ //TODO: Grabber code GRAB
        hands.setSideGrabberPosition(1);
        wait(100);

        /*7*/ //TODO: Grabber code UP
        hands.setSideLiftPosition(0.7);
        wait(100);

        //8 Drive back a bit so we don't disturb the other SkyStone(tm)s
        driver.driveWhileHorizontal(0.5f, -4f, this);
        wait(20);

        //9 Go to the other side of the blue/red line
        driver.driveVertical(0.5f, -PaulMath.encoderDistance(72), this);
        //10
        while(!(sensor.isBled() || (System.currentTimeMillis() - msStart) > 3000) && opModeIsActive()) {}
        driver.resetAllEncoders();
        driver.driveAuto(0,0,0,0);
        //branching fallback path-- go forward until we see the line
        if(!sensor.isBled()) {
            //fallback stop
            driver.driveAuto(0f, 0f, 0f,0f);
            wait(10);
            stop();
        }

        wait(10);

        /*10*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(100);

        /*11*/ //TODO: Grabber code RELEASE
        hands.setSideGrabberPosition(0);
        wait(100);

        /*12*/ //TODO: Grabber code UP
        hands.setSideLiftPosition(0);
        wait(100);

        //13: move backwards to other SkyStone(tm)
        msStart = System.currentTimeMillis();
        driver.driveVertical(0.5f, -PaulMath.encoderDistance(72), this);
        while(!(sensor.isSkystone() || (System.currentTimeMillis() - msStart) > 3000) && opModeIsActive()) {}
        driver.resetAllEncoders();
        driver.driveAuto(0,0,0,0);
        //branching fallback path-- go forward until we see the line

        if(!sensor.isSkystone()) {
            //Step 14 (fallback) go forward until we detect the line
            msStart = System.currentTimeMillis();
            driver.driveRaw(1, -1, -1, 1);
            while(!(sensorDown.isBled() || (System.currentTimeMillis() - msStart) > 2000) && opModeIsActive()) {}

            //Step 15 (fallback stop)
            driver.driveAuto(0f, 0f, 0f,0f);
            wait(10);
            stop();
        }

        //13 Go to the other side of the blue/red line
        driver.driveVertical(0.5f, 10f, this);
        while(!(sensor.isBled() || (System.currentTimeMillis() - msStart) > 3000) && opModeIsActive()) {}
        driver.resetAllEncoders();
        //branching fallback path-- go forward until we see the line
        if(!sensor.isBled()) {
            //fallback stop
            driver.driveAuto(0f, 0f, 0f,0f);
            wait(10);
            stop();
        }
        /*14*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(10);

        /*15*/ //TODO: Grabber code RELEASE
        hands.setSideGrabberPosition(0);

        wait(10);

        /*16*/ //TODO: Grabber code UP
        hands.setSideLiftPosition(0);
        wait(10);
    }


}


