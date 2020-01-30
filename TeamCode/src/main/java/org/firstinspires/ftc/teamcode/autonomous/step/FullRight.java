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
import org.firstinspires.ftc.teamcode.data.RobotState;

@Autonomous(group = "Step")
public class FullRight extends LinearOpMode {
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

    private boolean delaying = false;
    public void wait(int delay) {
        if(!delaying) {
            delaying = true;
            delayRunTime = System.currentTimeMillis();
        }
        while(System.currentTimeMillis() - delayRunTime <= delay && opModeIsActive()) {}

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

        hands.setSideLiftPosition(0);
        hands.setSideGrabberPosition(0);
        hands.setFoundationGrabberPosition(0);

        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        /*1*/driver.driveWhileHorizontalPid(0.5f, 1f, this);
        wait(10);
        /*2*/
        double msStart = System.currentTimeMillis();
        driver.driveVertical(0.5f, -1f);
        while(!(sensor.isSkystone() || (System.currentTimeMillis() - msStart) > 1000)) {}
        driver.resetAllEncoders();
        //branching fallback path-- go forward until we see the line
        if(!sensor.isSkystone()) {
            //3-- go forward until we detect the line
            msStart = System.currentTimeMillis();
            driver.driveRaw(-1, 1, 1, -1);
            while(!(sensor.isBled() || (System.currentTimeMillis() - msStart) > 1000)) {}

            //4-- stop
            driver.driveAuto(0f, 0f, 0f,0f);
            wait(10);
            stop();
        }


        /*5*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(10);

        /*6*/ //TODO: Grabber code GRAB
        hands.setSideGrabberPosition(1);
        wait(10);

        /*7*/ //TODO: Grabber code UP INCOMPLETE
        hands.setSideLiftPosition(0.7);
        wait(10);

        /*8*/driver.driveWhileHorizontalPid(0.5f, -1f, this);
        wait(10);

        /*9*/driver.driveWhileVerticalPid(0.5f, 1f, this);
        wait(10);

        /*10*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(10);

        /*11*/ //TODO: Grabber code RELEASE
        hands.setSideGrabberPosition(0);
        wait(10);

        /*12*/ //TODO: Grabber code UP
        hands.setSideLiftPosition(0);
        wait(10);

        /*13*/driver.driveWhileVerticalPid(0.5f, -1f, this);
        wait(10);

        /*14*/driver.driveWhileVerticalPid(0.5f, -1f, this);
        wait(10);

        /*15*/driver.driveWhileHorizontalPid(0.5f, 1f, this);
        wait(10);

        /*16*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(10);

        /*17*/ //TODO: Grabber code GRAB
        hands.setSideGrabberPosition(1);
        wait(10);

        /*18*/ //TODO: Grabber code UP INCOMPLETE
        hands.setSideLiftPosition(0.7);
        wait(10);

        /*19*/driver.driveWhileHorizontalPid(0.5f, 1f, this);
        wait(10);

        /*20*/driver.driveWhileVerticalPid(0.5f, 1f, this);
        wait(10);

        /*21*/driver.driveWhileVerticalPid(0.5f, 1f, this);
        wait(10);

        /*22*/ //TODO: Grabber code DOWN
        hands.setSideLiftPosition(1);
        wait(10);

        /*23*/ //TODO: Grabber code RELEASE
        hands.setSideGrabberPosition(0);
        wait(10);

        /*24*/ //TODO: Grabber code UP
        hands.setSideLiftPosition(0);
        wait(10);

        /*25*/driver.driveWhileVerticalPid(0.5f, 1f, this);
        wait(10);

        /*26*/driver.driveWhileHorizontalPid(0.5f, 1f, this);
        wait(10);

        /*26.5*/ //TODO: Foundation UP
        hands.setFoundationGrabberPosition(1);
        wait(10);

        /*27*/ //TODO: Foundation DOWN
        hands.setFoundationGrabberPosition(0);
        wait(10);

        /*28*/driver.driveWhileHorizontalPid(0.5f, -1f, this);
        wait(10);

        /*29*/ //TODO: Foundation UP
        hands.setFoundationGrabberPosition(1);
        wait(10);

        /*30*/driver.driveWhileVerticalPid(0.5f, -1f, this);
        wait(10);

        /*31*/driver.driveWhileHorizontalPid(0.5f, 1f, this);
        wait(10);

        /*32*/driver.driveWhileVerticalPid(0.5f, -1f, this);
        wait(10);

        /*33*/ driver.driveAuto(0f, 0f, 0f,0f);
        wait(10);






    }


}


