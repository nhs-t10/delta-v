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
public class BLEncoder extends LinearOpMode {
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

            driver.encoderDrive(0.1f, 1f, -1f, 1f, -1f, 1000, this);
            wait(30);
            driver.encoderDrive(0.1f, 1f, 1f, -1f, -1f, 1000, this);
            wait(30);
            driver.driveWhileVerticalPid(-0.1f, 1f, this);
            wait(30);
            driver.driveWhileHorizontalPid(-0.1f, 1f, this);

//            telemetry.addData("FL Direction: ", driver.frontLeft.getDirection());
//            telemetry.addData("FR Direction: ", driver.frontRight.getDirection());
//            telemetry.addData("BR Direction: ", driver.backLeft.getDirection());
//            telemetry.addData("BL Direction: ", driver.backRight.getDirection());
//
//            telemetry.addData("DriveStarted: ", driveStarted);
//            telemetry.addData("Grabbing State", hands.getServoPosition());
//            telemetry.addData("Skystone", sensor.isSkystone());
//            telemetry.addData("Target Position FL: ", driver.frontLeft.getTargetPosition());
//            telemetry.addData("Target Position FR: ", driver.frontRight.getTargetPosition());
//            telemetry.addData("Target Position BR: ", driver.backLeft.getTargetPosition());
//            telemetry.addData("Target Position BL: ", driver.backRight.getTargetPosition());
//            telemetry.addData("Current Position FL: ", driver.frontLeft.getCurrentPosition());
//            telemetry.addData("Current Position FR: ", driver.frontRight.getCurrentPosition());
//            telemetry.addData("Current Position BR: ", driver.backLeft.getCurrentPosition());
//            telemetry.addData("Current Position BL: ", driver.backRight.getCurrentPosition());
//            telemetry.addData("FL Power: ", driver.frontLeft.getPower());
//            telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());
//
//            telemetry.addData("FR Power: ", driver.frontRight.getPower());
//            telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());
//
//            telemetry.addData("BR Power: ", driver.backLeft.getPower());
//            telemetry.addData("BR Port: ", driver.backLeft.getPortNumber());
//
//            telemetry.addData("BL Power: ", driver.backRight.getPower());
//            telemetry.addData("BL Port: ", driver.backRight.getPortNumber());
//            telemetry.update();


    }


}


