package org.firstinspires.ftc.teamcode.teleop;

import android.graphics.Point;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.auxillary.*;
import org.firstinspires.ftc.teamcode.*;

@TeleOp
public class TeleopEncoder extends OpMode {

    InputManager input;
    MovementManager driver;
    int counter;
    PointNd location;
    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

//        driver.driveEncoder(0f, 0f, 0f, 0f);

        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fl"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fr"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "bl"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "br"));
    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());

        if(gamepad1.a) {
            driver.driveVertical(0.5f, 1f);

        }

        if(gamepad1.b) {
            driver.driveHorizontal(0.5f, 1f);
        }



        






        telemetry.addData("Target Position FL: ", driver.frontLeft.getTargetPosition());
        telemetry.addData("Target Position FR: ", driver.frontRight.getTargetPosition());
        telemetry.addData("Target Position BL: ", driver.backLeft.getTargetPosition());
        telemetry.addData("Target Position BR: ", driver.backRight.getTargetPosition());
        telemetry.addData("Current Position FL: ", driver.frontLeft.getCurrentPosition());
        telemetry.addData("Current Position FR: ", driver.frontRight.getCurrentPosition());
        telemetry.addData("Current Position BL: ", driver.backLeft.getCurrentPosition());
        telemetry.addData("Current Position BR: ", driver.backRight.getCurrentPosition());
        telemetry.addData("A Pressed: ", gamepad1.a );
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());
    }
}
