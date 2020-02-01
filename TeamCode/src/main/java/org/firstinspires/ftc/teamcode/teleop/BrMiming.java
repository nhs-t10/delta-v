package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FeatureManager;
import org.firstinspires.ftc.teamcode.InputManager;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.auxillary.FileSaver;

@TeleOp
public class BrMiming extends OpMode {
    MovementManager driver;
    ElapsedTime timer;
    FileSaver file;
    InputManager controller;
    ManipulationManager hands;
    int currentMimeIndex = 0;

    public void init() {
        controller = new InputManager(gamepad1);
        file = new FileSaver(FeatureManager.MIMING_BR);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber"),
                hardwareMap.get(Servo.class, "dropper")
        );
        file.deleteFile();
    }
    public void loop() {
        driver.driveOmni(controller.getMovementControls());
//        hands.setLiftState(controller.getLiftControls());

        if (gamepad1.right_trigger >= 0.01) {
            hands.grabServo.setServoPosition(0);
        } else {
            hands.grabServo.setServoPosition(1);
        }
        if (gamepad1.dpad_down) {
            hands.setFoundationGrabberPosition(0);
        }
        if (gamepad1.dpad_up) {
            hands.setFoundationGrabberPosition(1);
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


        if (gamepad1.b) {
            hands.liftMotor.setPower(-1);
        } else if (gamepad1.a) {
            hands.liftMotor.setPower(1);
        }else {
            hands.liftMotor.setPower(0);
        }

        if (gamepad1.x) {
            hands.setDropperPosition(1);
        }
        if (gamepad1.y) {
            hands.setDropperPosition(0);
        }

//        if (gamepad1.left_trigger >= 0.01) {
//            if (!sideLift) {
//                hands.setSideLiftPosition(1);
//                sideLift = true;
//            } else if (sideLift) {
//                hands.setSideLiftPosition(0);
//                sideLift = false;
//            }
//
//        }
//        if (gamepad1.right_trigger >= 0.01) {
//            if (!sideGrab) {
//                hands.setSideGrabberPosition(1);
//                sideGrab = true;
//            } else if (sideGrab) {
//                hands.setSideGrabberPosition(0);
//                sideGrab = false;
//            }
//
//        }
        int realMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

        if(realMimeIndex > currentMimeIndex) {
            currentMimeIndex = realMimeIndex;
            file.appendLine(controller.getState().toString());
            telemetry.addData("lastData", controller.getState().toString());
        }

        telemetry.addData("lastData", "----");
        telemetry.addData("Readdir: " , file.getDirectory());
        telemetry.addData("Instrcount: " , currentMimeIndex);
    }
}
