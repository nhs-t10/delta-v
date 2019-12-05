package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@TeleOp
public class TeleMiming extends OpMode {
    MovementManager driver;
    ElapsedTime timer;
    FileSaver file;
    InputManager controller;
    int currentMimeIndex = 0;

    public void init() {
        controller = new InputManager(gamepad1);
        file = new FileSaver(FeatureManager.MIMING_FILENAME);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();

        file.deleteFile();
    }
    public void loop() {
        driver.driveOmni(controller.getMovementControls());

        int realMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

        if(realMimeIndex > currentMimeIndex) {
            currentMimeIndex = realMimeIndex;
            file.appendLine(controller.getMovementControls().toString());
            telemetry.addData("lastData", controller.getMovementControls().toString());
        }

        telemetry.addData("lastData", "----");
        telemetry.addData("Readdir: " , file.getDirectory());
        telemetry.addData("Instrcount: " , currentMimeIndex);
    }
}
