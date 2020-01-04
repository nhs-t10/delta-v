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

@TeleOp
public class FlMiming extends OpMode {
    MovementManager driver;
    ElapsedTime timer;
    FileSaver file;
    InputManager controller;
    ManipulationManager hands;
    int currentMimeIndex = 0;

    public void init() {
        controller = new InputManager(gamepad1);
        file = new FileSaver(FeatureManager.MIMING_FL);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
        hands = new ManipulationManager(
            hardwareMap.get(Servo.class, "sev"),
            hardwareMap.get(DcMotor.class, "lift"));

        file.deleteFile();
    }
    public void loop() {
        driver.driveOmni(controller.getMovementControls());
        hands.setLiftState(controller.getLiftControls());
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
