package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@Autonomous
public class AutoFR extends StepAuto {
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
             driver.driveOmni(1f, 0f, 0f);
             nextStep(1000);
         break;
         case MOVE1:
             driver.driveOmni(0f, 1f, 0f);
             nextStep(500);
         case MOVE2:
             //sampling
         break;
         case MOVE3:
             //picking up a sky stone
         break;
         case MOVE4:
             driver.driveOmni(-1f, 0f, 0f);
             nextStep(1000);
         break;
         case MOVE5:
             driver.driveOmni(0f, -1f, 0f);
             nextStep(1000);
         break;
         case MOVE6:
             driver.driveOmni(0f, 1f, 0f);
             nextStep(1000);
         break;
         case MOVE7:
             driver.driveOmni(1f, 0f, 0f);
             nextStep(500);
         break;
         case MOVE8:
             //finding last skystone
         break;
         case MOVE9:
         driver.driveOmni(-1f, 0f, 0f);
         nextStep(1000);
         break;
         case MOVE10:
         driver.driveOmni(0f, -1f, 0f);
         nextStep(1000);
     break;
         default:
             driver.driveOmni(0f, 0f, 0f);
        }



    }
}


