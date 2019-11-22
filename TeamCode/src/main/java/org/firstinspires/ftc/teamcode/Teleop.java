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
    Servo sev;

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap);
       sev = hardwareMap.get(Servo.class, "ml");
        ManipulationManager grab = new ManipulationManager(sev, hardwareMap.get(DcMotor.class, "fr"));
    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());
        sensor.runSample();

        if(input.getGamepad().a){
            ManipulationManager.setServoPosition(sev, 1);
        }
        if(input.getGamepad().b){
            ManipulationManager.setServoPosition(sev, 0);
        }
        telemetry.addData("Input LX: ", input.getGamepad().left_stick_x);
        telemetry.addData("Input LY: ", input.getGamepad().left_stick_y);
        telemetry.addData("Input RX: ", input.getGamepad().right_stick_x);

        telemetry.addData("Color Code", sensor.getHexCode());

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("Servo Rot ", sev.getPosition());

    }
}
