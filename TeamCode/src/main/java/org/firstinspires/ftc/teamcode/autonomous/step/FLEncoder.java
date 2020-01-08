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
        driver.driveEncoder(0f, 0f, 0f, 0f);

        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fl"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "fr"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "bl"));
        driver.resetEncoders(hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap);
    }  

    
    public void loop() {
       switch(currentStep){
        case START:
            hands.setGrabbingState(false);
            driver.driveEncoder(-1f, 1f, -1f, 1f);
            nextStep(1900);
        break;
        case MOVE1:
            driver.resetAllEncoders();
            nextStep(1000);
        case MOVE2:
        if (sensor.isSkystone()) {
            currentStep = step.MOVE5;
        } else{
            driver.driveEncoder(0.5f, 0.5f, -0.5f, -0.5f);
        }

            nextStep(2000);
        break;
        case MOVE3:
//            if (sensor.isBled()) {
//                driver.driveOmni(0f, 0f, 0f);
//            } else{
                driver.driveOmni(-0.5f, 0f, 0f);
//            }
            
            nextStep(2000);
        break;
        case MOVE4:
            driver.driveOmni(0f, 0f, 0f);
        break;
        case MOVE5:
            driver.driveOmni(0f, -0.5f, 0f);
            nextStep(100);
        break;
        case MOVE6:
            hands.setGrabbingState(true);
            nextStep(1000);
        break;
        case MOVE7:
            driver.driveOmni(0f, 0.5f, 0f);
            nextStep(200);
        break;
        case MOVE8:
            driver.driveOmni(-0.5f, 0f, 0f);
            nextStep(2500);
        break;
        case MOVE9:
            driver.driveOmni(0f, 0f, 0f);
            hands.setGrabbingState(false);
            nextStep(500);
        break;
        case MOVE10:
            driver.driveOmni(0.5f, 0f, 0f);
            nextStep(2500);
        break;
        case MOVE11:
            driver.driveOmni(0f, -0.5f, 0f);
            nextStep(200);
        break;
        case MOVE12:
            driver.driveOmni(0.5f, 0f, 0f);
            nextStep(2000);
        break;
        case MOVE13:
            driver.driveOmni(0f, -0.1f, 0f);
            nextStep(100);
        break;
        case MOVE14:
           hands.setGrabbingState(true);
           nextStep(1000);
        break;
        case MOVE15:
            driver.driveOmni(-0.5f, 0f, 0f);
            nextStep(1200);
        case MOVE16:
            driver.driveOmni(0f, 0f, 0f);
            hands.setGrabbingState(false);
            nextStep(1000);
        break;
        case MOVE17:
//            if (sensor.isBled()) {
//                driver.driveOmni(0f, 0f, 0f);
//            }  else{
                driver.driveOmni(0.5f, 0f, 0f);
//            }
            nextStep(500);
        break;
        case MOVE18:
            driver.driveOmni(0f,0f,0f);
        break;
        default:
            driver.driveOmni(0f, 0f, 0f);
       }
        telemetry.addData("Grabbing State", hands.getServoPosition() );
        telemetry.addData("Skystone", sensor.isSkystone());
        telemetry.addData("State: ", currentStep);
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());


    }
}


