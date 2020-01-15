package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ColorSensor;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;

@Autonomous(group = "Step")
public class FLEncoder extends StepAuto {
    MovementManager driver;
    ManipulationManager hands;
    ColorSensor sensor;

    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift")
                );
        driver.resetAllEncoders();
        sensor = new ColorSensor(hardwareMap);
    }  

    
    public void loop() {
        switch(currentStep){
            case START:
                //The robot moves towards the sky stones
                hands.setGrabbingState(false);
                driver.driveHorizontal(-0.5f, -1f);
                nextStep(2000);
            break;
            case MOVE1:
                //The robot looks for the SkyStone with color sensor
                if(sensor.isSkystone()) {
                    currentStep = step.MOVE5;
                } else {
                    driver.driveVertical(0.5f, 1f);
                }
                nextStep(1000);
            break;
            case MOVE2:
                //If robot fails to see a sky stone it moves until it sees a line
                if(sensor.isBled()){
                    driver.resetAllEncoders();
                } else {
                    driver.driveVertical(-0.5f, -1f);
                }
                nextStep(1000);
            break;
            case MOVE3:
                //Fail-safe stop
                driver.resetAllEncoders();
            break;
            case MOVE4:
                //TODO: General grabber code DOWN
                nextStep(1000);
            break;
            case MOVE5:
                //TODO: General grabber code GRAB
                nextStep(1000);
            break;
            case MOVE6:
                //TODO: General grabber code UP INCOMPLETE
                nextStep(1000);
            break;
            case MOVE7:
                //Moving to a safe distance to go under bridge
                driver.driveHorizontal(-0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE8:
                //Moving towards the line
                driver.driveVertical(-0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE9:
                //TODO: General grabber code DOWN
                nextStep(1000);
            break;
            case MOVE10:
                //TODO: General grabber code RELEASE
                nextStep(1000);
            break;
            case MOVE11:
                //TODO: General grabber code UP
                nextStep(1000);
            break;
            case MOVE12:
                //Move back to the SkyStones
                driver.driveVertical(0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE13:
                //Move the distance of 2 SkyStones
                driver.driveVertical(0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE14:
                //Move back to stand infront of SkyStones
                driver.driveHorizontal(-0.5f, -1f);
                nextStep(1000);
            break;
            case MOVE15:
                //TODO: General grabber code DOWN
                nextStep(1000);
            break;
            case MOVE16:
                //TODO: General grabber code GRAB
                nextStep(1000);
            break;
            case MOVE17:
                //TODO: General grabber code UP INCOMPLETE
                nextStep(1000);
            break;
            case MOVE18:
                //Move to a safe distance to go under bridge
                driver.driveHorizontal(0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE19:
                //Move until it sees a line
                if(sensor.isBled()){
                    driver.resetAllEncoders();
                } else {
                driver.driveVertical(0.5f, 1f);
                }
                nextStep(1000);
            break;
            case MOVE20:
                //Move a controlled distance into the foundation zone for dropping the block
            driver.driveVertical(-0.5f, -1f);
                nextStep(1000);
            break;
            case MOVE21:
                //TODO: General grabber code DOWN
                nextStep(1000);
            break;
            case MOVE22:
                //TODO: General grabber code RELEASE
                nextStep(1000);
            break;
            case MOVE23:
                //TODO: General grabber code UP
                nextStep(1000);
            break;
            //This is where the robot starts oving towards the foundation
            case MOVE24:
                //Move to be in line with the foundation
            driver.driveVertical(-0.5f, -1f);
                nextStep(1000);
            break;
            case MOVE25:
                //Move up to the foundation
            driver.driveHorizontal(-0.5f, -1f);
                nextStep(1000);
            break;
            case MOVE26:
                //TODO: General foundation claw code DOWN
                nextStep(1000);
            break;
            case MOVE27:
                //Drag foundation into building zone
            driver.driveHorizontal(0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE28:
                //TODO: General foundation claw code UP
                nextStep(1000);
            break;
            case MOVE29:
                //Move forward, away from the foundation
            driver.driveVertical(0.5f, 1f);
                nextStep(1000);
            break;
            case MOVE30:
                //Move to line up with parking
            driver.driveHorizontal(-0.5f, -1f);
                nextStep(1000);
            break;
            case MOVE31:
                //Move back to the to the line for parking
                if(sensor.isBled()){
                    driver.resetAllEncoders();
                } else {
                    driver.driveVertical(0.5f, 1f);
                }
                nextStep(1000);
            break;
            case MOVE32:
                //Final Stop
                driver.resetAllEncoders();
            break;
            default:
                //If we get here, something very bad has happened.
                driver.resetAllEncoders();
       }
        telemetry.addData("Grabbing State", hands.getServoPosition() );
        telemetry.addData("Skystone", sensor.isSkystone());
        telemetry.addData("State: ", currentStep);
        telemetry.addData("Target Position FL: ", driver.frontLeft.getTargetPosition());
        telemetry.addData("Target Position FR: ", driver.frontRight.getTargetPosition());
        telemetry.addData("Target Position BL: ", driver.backLeft.getTargetPosition());
        telemetry.addData("Target Position BR: ", driver.backRight.getTargetPosition());
        telemetry.addData("Current Position FL: ", driver.frontLeft.getCurrentPosition());
        telemetry.addData("Current Position FR: ", driver.frontRight.getCurrentPosition());
        telemetry.addData("Current Position BL: ", driver.backLeft.getCurrentPosition());
        telemetry.addData("Current Position BR: ", driver.backRight.getCurrentPosition());
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());


    }
}


