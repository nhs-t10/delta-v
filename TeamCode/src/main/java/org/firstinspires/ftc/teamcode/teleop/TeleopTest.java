package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import org.firstinspires.ftc.teamcode.ManipulationManager;

@TeleOp
public class TeleopTest extends OpMode {
    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    ManipulationManager hands;
    //    Servo sev;
    ColorSensor sensorDown;
    boolean sideGrab = false;
    boolean foundationGrabber = false;
    boolean incompleteLift = false;
    boolean completeLift = false;

    private static boolean toggleSpeed = false;

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

        sensor.startAsyncLoop();
        sensorDown.startAsyncLoop();

//        sev =  hardwareMap.get(Servo.class, "sev");
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber")
        );
        hands.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.resetAllEncoders();
        driver.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



    }

    public void loop() {
        driver.driveOmni(input.getMovementControls());
//        hands.setLiftState(input.getLiftControls());

        sensor.runSample();


        if (input.getGamepad().a) {
            if(!sideGrab) {
                hands.setSideGrabberPosition(1);
                sideGrab = true;
            } else {
                hands.setSideGrabberPosition(0);
                sideGrab = false;
            }
        }
        if (input.getGamepad().b) {
            if(!foundationGrabber) {
                hands.setFoundationGrabberPosition(1);
                sideGrab = true;
            } else {
                hands.setFoundationGrabberPosition(0);
                sideGrab = false;
            }
        }

        if (input.getGamepad().x) {
            if(!incompleteLift){
                hands.setSideLiftPosition(0.8);
                incompleteLift = true;
            } else {
                hands.setSideLiftPosition(0);
                incompleteLift = false;
            }
        }

        if (input.getGamepad().y) {
            if(!completeLift) {
                hands.setSideLiftPosition(1);
                completeLift = true;
            } else {
                hands.setSideLiftPosition(0);
                completeLift = false;
            }
        }



        if (gamepad1.left_bumper) {
            if (driver.getSpeed() == 0.25f && !toggleSpeed) {
                driver.setSpeed(1.0f);
                toggleSpeed = true;
            }
            if (driver.getSpeed() == 1.0f && !toggleSpeed) {
                driver.setSpeed(0.25f);
                toggleSpeed = true;
            } else {
                toggleSpeed = false;
            }
        }

        if (input.getGamepad().dpad_up) {
            driver.driveOmni(0f,-0.5f, 0f);
        } else if (input.getGamepad().dpad_down){
            driver.driveOmni(0f,0.5f, 0f);
        } else if (input.getGamepad().dpad_right) {
            driver.driveOmni(0.5f, 0f, 0f);
        } else if (input.getGamepad().dpad_left) {
            driver.driveOmni(-0.5f, 0f, 0f);
        }

        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
                driver.frontRight.getCurrentPosition()+
                driver.backLeft.getCurrentPosition()+
                driver.backRight.getCurrentPosition())/4);


        telemetry.addData("Input LX: ", input.getGamepad().left_stick_x);
        telemetry.addData("Input LY: ", input.getGamepad().left_stick_y);
        telemetry.addData("Input RX: ", input.getGamepad().right_stick_x);
        telemetry.addData("Skystone", sensor.isSkystone());
        telemetry.addData("Blue/Red", sensor.isBled());
        telemetry.addData("colorhsv",sensor.getHsv()[0] + "," + sensor.getHsv()[1] + "," + sensor.getHsv()[2]);
        telemetry.addData("runcount", sensor.runCount);
        telemetry.addData("Color Code", sensor.getHexCode());

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());
    }


}