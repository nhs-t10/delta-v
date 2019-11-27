package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Teleop extends OpMode {
    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    ManipulationManager manip;

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap);
        manip = new ManipulationManager(
                hardwareMap.get(Servo.class, "ml"),
                hardwareMap.get(DcMotor.class, "lift")
        );
    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());
        sensor.runSample();

        if(input.getGamepad().a){
            manip.setServoPosition(1);
        }
        if(input.getGamepad().b){
            manip.setServoPosition(0);
        }
        telemetry.addData("Input LX: ", input.getGamepad().left_stick_x);
        telemetry.addData("Input LY: ", input.getGamepad().left_stick_y);
        telemetry.addData("Input RX: ", input.getGamepad().right_stick_x);

        telemetry.addData("Color Code", sensor.getHexCode());

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());

    }
}
