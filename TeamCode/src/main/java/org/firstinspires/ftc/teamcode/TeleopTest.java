package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import ImuManager;
import PaulMath;

@TeleOp
public class TeleopTest extends OpMode {
    private BNO055IMU imu
    float[] velocity_x
    float[] velocity_y
    InputManager input;
    MovementManager driver;
    ImuManager velocity;
    PaulMath calculation;
    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));
        velocity = new ImuManager(imu);
        calculation = new PaulMath();
        counter = 0;

    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());
        //Creates an array of all previous velocities and calculates the location of the robot
        velocity_x[counter] = velocity.getVelocityX();
        velocity_y[counter] = velocity.getVelocityY();
        location = calculation.location(velocity_x, velocity_y);
        
        telemetry.addData("Velocity X: " , velocity.getVelocityX());
        telemetry.addData("Velocity Y: " , velocity.getVelocityY());
        telemetry.addData("Location: " , location);
        telemetry.addData("FL Power: ", frontLeft.getPower());
        telemetry.addData("FR Power: ", frontRight.getPower());
        telemetry.addData("BL Power: ", backLeft.getPower());
        telemetry.addData("BR Power: ", backRight.getPower());
    }
}
