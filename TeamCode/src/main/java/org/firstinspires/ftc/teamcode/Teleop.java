package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Teleop extends OpMode {
    InputManager input;
    MovementManager driver;
    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));
    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());

        
    }
}