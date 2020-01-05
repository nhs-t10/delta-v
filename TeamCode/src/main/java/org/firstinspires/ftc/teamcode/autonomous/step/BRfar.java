package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MovementManager;


@Autonomous(group = "Step")
public class BRfar extends StepAuto {
    MovementManager driver;
    

    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br")); 
    }  



    
    public void loop() {
       switch(currentStep){
       case START:
           driver.driveOmni(0f, -0.5f, 0f);
           nextStep(500);
       break;
       case MOVE1:
           driver.driveOmni(-0.5f, 0f, 0f);
           nextStep(1000);
       break;
//        case MOVE1:
//            //Drop bar thing left
//            // driver.driveOmni(0f, 0f, 0f);
//            nextStep(1000);
//        case MOVE2:
//            driver.driveOmni(1f, 0f, 0f);
//            nextStep(1000);
//        break;
//        case MOVE3:
//            driver.driveOmni(0f, -1f, 0f);
//            nextStep(500);
//        break;
//        case MOVE4:
//            driver.driveOmni(-1f, 0f, 0f);
//            nextStep(1000);
//        break;
        default:
            driver.driveOmni(0f, 0f, 0f);
       }
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());


    }
}


