package org.firstinspires.ftc.teamcode.teleop;

import android.graphics.Point;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.auxillary.*;
import org.firstinspires.ftc.teamcode.*;
@TeleOp
public class TeleopTest extends OpMode {
    float[] velocity_x;
    float[] velocity_y;
    InputManager input;
    MovementManager driver;
    ManipulationManager hands;
    ImuManager imu;
    int counter;
    PointNd location;

    TelemetryManager logger;
    //Servo sev;

    private static boolean toggleSpeed = false;
    //private static final float speedIncrement = 0.0001

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        //driver.setAllEncoderModes(DcMotor.RunMode.RUN_USING_ENCODER);

        //sev = new Servo(hardwareMap.get(Servo.class, "sev"))
        imu = new ImuManager(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.calibrate();
//        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fl"));
//        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fr"));
//        driver.resetEncoders(hardwareMap.get(DcMotor.class, "bl"));
//        driver.resetEncoders(hardwareMap.get(DcMotor.class, "br"));
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift")
        );

        logger = new TelemetryManager(this);
    }

    public void loop() {
        driver.driveOmni(input.getMovementControls());
        hands.setLiftState(input.getLiftControls());
        //Creates an array of all previous velocities and calculates the location of the robot
        // velocity_x[counter] = (float)imu.getVelocityX();
        // velocity_y[counter] = (float)imu.getVelocityY();
        // location = PaulMath.location(velocity_x, velocity_y);

        if (gamepad1.a) {
//            driver.driveEncoder(2f, 2f, 2f, 2f);
        }

        RobotState state = input.getState();
        state.setRawMotors(driver);
        state.setSpeed(driver.getSpeed());
        state.setOrientation(imu.getOrientation());
        state.setPosition(imu.getPosition());

        logger.addData("tickFl", driver.frontLeft.getCurrentPosition()+ "");

        logger.switchTab(input.getLogTabSwitchDelta());
        logger.update(state);

        // telemetry.addData("Velocity X: " , imu.getVelocityX());
        // telemetry.addData("Velocity Y: " , imu.getVelocityY());
        // telemetry.addData("Velocity Z: " , imu.getVelocityZ());

        /*
        This is Austin's code for speed switching. It will probably delete itself for no reason.
        */
        //this particular part is instantaneous toggling between 0.2 and 0.6
        if (gamepad1.left_bumper) {
            if (driver.getSpeed() == 0.25f && !toggleSpeed) {
                driver.setSpeed(1.0f);
                toggleSpeed = true;
            }
            if (driver.getSpeed() == 1.0f && !toggleSpeed) {
                driver.setSpeed(0.25f);
                toggleSpeed = true;
            }
        } else {
            toggleSpeed = false;
        }

        if(gamepad1.dpad_right) {
            MovementOrder left = MovementOrder.HVR(1f, 0f, 0f);
            driver.driveOmni(left);
        }
        if(gamepad1.dpad_left) {
            MovementOrder right = MovementOrder.HVR(-1f, 0f, 0f);
            driver.driveOmni(right);
        }
        if(gamepad1.dpad_up) {
            MovementOrder left = MovementOrder.HVR(0f, 1f, 0f);
            driver.driveOmni(left);
        }
        if(gamepad1.dpad_down) {
            MovementOrder right = MovementOrder.HVR(0f, -1f, 0f);
            driver.driveOmni(right);
        }
    }
}
        //and this particular part is incremental increase and decrease in speed.
        /*
        if(gamepad1.left_bumper){
            driver.setSpeed(driver.getSpeed() - speedIncrement);
        }
        if(gamepad1.right_bumper){
            driver.setSpeed(driver.getSpeed() + speedIncrement);
        }
        */
