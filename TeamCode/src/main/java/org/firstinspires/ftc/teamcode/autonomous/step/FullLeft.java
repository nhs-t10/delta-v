package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ColorSensor;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.TelemetryManager;
import org.firstinspires.ftc.teamcode.data.RobotState;

@Autonomous(group = "Step")
public class FullLeft extends LinearOpMode {
    MovementManager driver;
    ManipulationManager hands;
    ElapsedTime runtime = new ElapsedTime();
    ColorSensor sensor;
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
                hardwareMap.get(DcMotor.class, "lift")
        );
        driver.resetAllEncoders();
        driver.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sensor = new ColorSensor(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        /*1*/driver.driveWhileHorizontal(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*2*/driver.driveWhileVertical(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*3*/driver.driveWhileVertical(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*4*/ driver.driveAuto(0f, 0f, 0f,0f);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*5*/ //TODO: Grabber code DOWN
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*6*/ //TODO: Grabber code GRAB
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*7*/ //TODO: Grabber code UP INCOMPLETE
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*8*/driver.driveWhileHorizontal(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*9*/driver.driveWhileVertical(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*10*/ //TODO: Grabber code DOWN
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*11*/ //TODO: Grabber code RELEASE
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*12*/ //TODO: Grabber code UP
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*13*/driver.driveWhileVertical(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*14*/driver.driveWhileVertical(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*15*/driver.driveWhileHorizontal(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*16*/ //TODO: Grabber code DOWN
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*17*/ //TODO: Grabber code GRAB
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*18*/ //TODO: Grabber code UP INCOMPLETE
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*19*/driver.driveWhileHorizontal(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*20*/driver.driveWhileVertical(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*21*/driver.driveWhileVertical(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*22*/ //TODO: Grabber code DOWN
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*23*/ //TODO: Grabber code RELEASE
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*24*/ //TODO: Grabber code UP
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*25*/driver.driveWhileVertical(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*26*/driver.driveWhileHorizontal(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*27*/ //TODO: Foundation DOWN
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*28*/driver.driveWhileHorizontal(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*29*/ //TODO: Foundation UP
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*30*/driver.driveWhileVertical(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*31*/driver.driveWhileHorizontal(0.5f, -1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*32*/driver.driveWhileVertical(0.5f, 1f, this);
        try{Thread.sleep(10); } catch(InterruptedException e){}

        /*33*/ driver.driveAuto(0f, 0f, 0f,0f);
        try{Thread.sleep(10); } catch(InterruptedException e){}






    }


}


