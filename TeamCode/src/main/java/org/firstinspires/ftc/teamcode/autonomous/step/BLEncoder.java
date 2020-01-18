package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ColorSensor;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;

@Autonomous(group = "Step")
public class BLEncoder extends StepAuto {
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
            if(!driver.driveHorizontal(0.1f, -3f)){
                nextStep(0);
            }
            nextStep(10000);
        break;
//        case MOVE1:
//            if(!driver.driveHorizontal(0.1f, 4f)){
//                nextStep(0);
//            }
//            nextStep(10000);
//        break;
//        case MOVE2:
//            if(!driver.driveVertical(0.1f, -3f)){
//                nextStep(0);
//            }
//            nextStep(10000);
//        break;
//        case MOVE3:
//            if(!driver.driveHorizontal(0.1f, -4f)){
//                nextStep(0);
//            }
//            nextStep(10000);
//        break;
//        case MOVE4:
//           driver.driveAuto(1f, 1f, 1f, 1f);
//           nextStep(10000);
//        break;
        default:
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
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());



    }
}


