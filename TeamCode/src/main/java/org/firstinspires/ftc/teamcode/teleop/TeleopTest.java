package org.firstinspires.ftc.teamcode;

import android.graphics.Point;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class TeleopTest extends OpMode {
    float[] velocity_x;
    float[] velocity_y;
    InputManager input;
    MovementManager driver;
    ImuManager imu; 
    int counter;
    PointNd location;
    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                                     hardwareMap.get(DcMotor.class, "fr"),
                                     hardwareMap.get(DcMotor.class, "bl"),
                                     hardwareMap.get(DcMotor.class, "br"));

        imu = new ImuManager(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.calibrate();
    }
    public void loop() {
        driver.driveOmni(input.getMovementControls());
        //Creates an array of all previous velocities and calculates the location of the robot
        velocity_x[counter] = (float)imu.getVelocityX();
        velocity_y[counter] = (float)imu.getVelocityY();
        location = PaulMath.location(velocity_x, velocity_y);
        
        telemetry.addData("Velocity X: " , imu.getVelocityX());
        telemetry.addData("Velocity Y: " , imu.getVelocityY());
        telemetry.addData("Velocity Z: " , imu.getVelocityZ());
        telemetry.addData("Location: " , location);
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());
    }
}
