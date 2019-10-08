package org.firstinspires.ftc.teamcode;

import android.graphics.Point;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleopTest extends OpMode {
    private BNO055IMU imu;
    float[] velocity_x;
    float[] velocity_y;
    InputManager input;
    MovementManager driver;
    ImuManager velocity;
    int counter;
    PointNd location;
    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));
        velocity = new ImuManager(imu);
        counter = 0;

    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());
        //Creates an array of all previous velocities and calculates the location of the robot
        velocity_x[counter] = (float)velocity.getVelocityX();
        velocity_y[counter] = (float)velocity.getVelocityY();
        location = PaulMath.location(velocity_x, velocity_y);
        
        telemetry.addData("Velocity X: " , velocity.getVelocityX());
        telemetry.addData("Velocity Y: " , velocity.getVelocityY());
        telemetry.addData("Location: " , location);
    }
}
