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
    }
    public void loop() {
        driver.driveOmni(controller.getMovementControls());

        if(timer.milliseconds() % FeatureManager.MIMING_MS_PER_SAMPLE == 0) {
            file.appendLine(controller.getMovementControls().toString());
        }
    }
}