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
public class Teleop extends OpMode {
    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    ManipulationManager hands;
//    Servo sev;
    ColorSensor sensorDown;

    private static boolean toggleSpeed = false;

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

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
        //hands.setLiftState(input.getLiftControls());

        sensor.runSample();

        

        if (input.gamepad.right_trigger > 0.01f) {
            hands.grabServo.setServoPosition(0);
        }
        if (input.gamepad.left_trigger > 0.01f) {
            hands.grabServo.setServoPosition(1);
        }
        if (input.getGamepad().b) {
           // manip.setServoPosition(0);
        }

        //this is the speed toggle code
        /*
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
        */

        //this is the speed single pushbutton code
        if (gamepad1.left_bumper) {
            driver.setSpeed(0.25f);
        } else {
            driver.setSpeed(1.00f);
        }


        if (gamepad1.dpad_up) {
            hands.liftMotor.setPower(-1);
        } else if (gamepad1.dpad_down) {
            hands.liftMotor.setPower(1);
        }else {
            hands.liftMotor.setPower(0);
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