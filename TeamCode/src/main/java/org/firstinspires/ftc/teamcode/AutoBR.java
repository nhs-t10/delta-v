package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;


@Autonomous
public class AutoBR extends StepAuto {
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
            driver.driveOmni(0f, -1f, 0f);
            nextStep(1000);
        break;
        case MOVE1:
            //Drop bar thing left
            nextStep(1000);
        case MOVE2:
            driver.driveOmni(0f, 1f, 0f);
            nextStep(1000);
        break;
        case MOVE3:
            driver.driveOmni(-1f, 0f, 0f);
            nextStep(500);
        break;
        case MOVE4:
            driver.driveOmni(0f, 1f, 0f);
            nextStep(1000);
        break;
        case MOVE5:
            driver.driveOmni(1f, 0f, 0f);
        break;
        default:
            driver.driveOmni(0f, 0f, 0f);
       }
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());


    }
}


