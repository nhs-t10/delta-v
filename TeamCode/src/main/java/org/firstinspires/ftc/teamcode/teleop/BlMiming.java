package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;

@TeleOp
public class BlMiming extends OpMode {
    MovementManager driver;
    ElapsedTime timer;
    FileSaver file;
    InputManager controller;
    ManipulationManager hands;
    boolean sideGrab = false;
    boolean sideLift = false;

    int currentMimeIndex = 0;
    boolean firstLoopRun = true;
    public void init() {
        controller = new InputManager(gamepad1);
        file = new FileSaver(FeatureManager.MIMING_BL);
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
        if(firstLoopRun) timer = new ElapsedTime();
        firstLoopRun = false;

        driver.driveOmni(controller.getMovementControls());

        hands.setLiftState(controller.getLiftControls());

        hands.setFoundationGrabberPosition(controller.getFoundationMoverPos());

        hands.setDropperPosition(controller.getDropperPosition());

        driver.setSpeed(controller.getCurrentSpeed());

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
