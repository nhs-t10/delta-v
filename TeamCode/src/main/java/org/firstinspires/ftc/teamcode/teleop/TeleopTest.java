package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import org.firstinspires.ftc.teamcode.ManipulationManager;

@TeleOp
public class TeleopTest extends OpMode {
    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    ManipulationManager hands;
    ImuManager imu;
    TelemetryManager logger;
    //    Servo sev;
    ColorSensor sensorDown;
    boolean sideGrab = false;
    boolean foundationGrabber = false;
    boolean incompleteLift = false;
    boolean completeLift = false;

    private static boolean toggleSpeed = false;

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

        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber"),
                hardwareMap.get(Servo.class, "dropper")
        );
        hands.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.resetAllEncoders();
        driver.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        logger = new TelemetryManager(this);

    }

    public void loop() {
        driver.driveOmni(input.getMovementControls());
//        hands.setLiftState(input.getLiftControls());

        sensor.runSample();
        sensorDown.runSample();

        if (input.getGamepad().a) {
            sideGrab = true;
        }

        /*            } else {
                hands.setSideGrabberPosition(0);
                sideGrab = false;
            }
        }
        if (input.getGamepad().b) {
            if(!foundationGrabber) {
                hands.setFoundationGrabberPosition(1);
                sideGrab = true;
            }   sideGrab = false;
          else {
                toggleSpeed = false;
        }
        */

        if (gamepad1.left_bumper && driver.getSpeed() < 1) driver.setSpeed(driver.getSpeed() + 0.001f);
        else if (gamepad1.left_bumper && driver.getSpeed() > 0.25f) {
            driver.setSpeed(driver.getSpeed() - 0.001f);
        }

        if (input.getGamepad().x) {
            if(!incompleteLift){
                hands.setSideLiftPosition(0.8);
                incompleteLift = true;
            } else {
                hands.setSideLiftPosition(0);
                incompleteLift = false;
            }
        }

        if (input.getGamepad().y) {
            if(!completeLift) {
                hands.setSideLiftPosition(1);
                completeLift = true;
            } else {
                hands.setSideLiftPosition(0);
                completeLift = false;
            }
        }



//        if (gamepad1.left_bumper) {
//            if (driver.getSpeed() == 0.25f && !toggleSpeed) {
//                driver.setSpeed(1.0f);
//                toggleSpeed = true;
//            }
//            if (driver.getSpeed() == 1.0f && !toggleSpeed) {
//                driver.setSpeed(0.25f);
//                     toggleSpeed = false;
//            }
//        } else {
//            toggleSpeed = false;
//        }
        if (gamepad1.left_bumper) {
            driver.setSpeed(0.25f);
        } else {
            driver.setSpeed(1.00f);
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
        //and this particular part is incremental increase and decrease in speed.
        /*
        if(gamepad1.left_bumper){
            driver.setSpeed(driver.getSpeed() - speedIncrement);

        }*/

        if (input.getGamepad().dpad_up) {
            driver.driveOmni(0f,-0.5f, 0f);
        } else if (input.getGamepad().dpad_down){
            driver.driveOmni(0f,0.5f, 0f);
        } else if (input.getGamepad().dpad_right) {
            driver.driveOmni(0.5f, 0f, 0f);
        } else if (input.getGamepad().dpad_left) {
            driver.driveOmni(-0.5f, 0f, 0f);
        }

        RobotState state = input.getState();

        logger.addData("tickFl", driver.frontLeft.getCurrentPosition()+ "");
        logger.addData("tickFr", driver.frontRight.getCurrentPosition()+ "");
        logger.addData("tickBl", driver.backLeft.getCurrentPosition()+ "");
        logger.addData("tickBr", driver.backRight.getCurrentPosition()+ "");

        logger.switchTab(input.getLogTabSwitchDelta());

        logger.addData("FL Ticks:", driver.frontLeft.getCurrentPosition() + "");
        logger.addData("FR Ticks:", driver.frontRight.getCurrentPosition() + "");
        logger.addData("BL Ticks:", driver.backRight.getCurrentPosition() + "");
        logger.addData("BR Ticks:", driver.backLeft.getCurrentPosition() + "");
        logger.addData("Average Ticks:", ((Math.abs(driver.frontLeft.getCurrentPosition())+
                Math.abs(driver.frontRight.getCurrentPosition())+
                Math.abs(driver.backLeft.getCurrentPosition())+
                Math.abs(driver.backRight.getCurrentPosition()))/4) + "");


        logger.addData("Input LX: ", input.getGamepad().left_stick_x + "");
        logger.addData("Input LY: ", input.getGamepad().left_stick_y + "");
        logger.addData("Input RX: ", input.getGamepad().right_stick_x + "");
        logger.addData("Skystone", sensor.isSkystone() + "");
        logger.addData("Blue/Red", sensor.isBled() + "");
        logger.addData("colorhsv",sensor.getHsv()[0] + "," + sensor.getHsv()[1] + "," + sensor.getHsv()[2]);
        logger.addData("colorhsv_down",sensorDown.getHsv()[0] + "," + sensorDown.getHsv()[1] + "," + sensorDown.getHsv()[2]);
        logger.addData("runcount", sensor.runCount + "");
        logger.addData("Color Code", sensor.getHexCode());

        logger.addData("FL Power: ", driver.frontLeft.getPower() + "");
        logger.addData("FL Port: ", driver.frontLeft.getPortNumber() + "");

        logger.addData("FR Power: ", driver.frontRight.getPower() + "");
        logger.addData("FR Port: ", driver.frontRight.getPortNumber() + "");

        logger.addData("BL Power: ", driver.backLeft.getPower() + "");
        logger.addData("BL Port: ", driver.backLeft.getPortNumber() + "");

        logger.addData("BR Power: ", driver.backRight.getPower() + "");
        logger.addData("BR Port: ", driver.backRight.getPortNumber() + "");

        logger.update(state);
    }


}