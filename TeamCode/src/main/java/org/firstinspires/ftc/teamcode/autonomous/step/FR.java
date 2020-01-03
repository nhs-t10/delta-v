package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.autonomous.step.StepAuto;

@Autonomous(group = "Step")
public class FR extends StepAuto {
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
             driver.driveOmni(0f, -0.4f, 0f);
             nextStep(2500);
         break;
         case MOVE1:
             driver.driveOmni(0f, 0f, 0f);
             nextStep(1000);
         case MOVE2:
 //            if (sensor.isGold()) {
 //                 currentStep = step.MOVE5;
 //            } else {
 //                driver.driveOmni(0f, 1f, 0f);
 //            }
             driver.driveOmni(0.5f, 0f, 0f);
             nextStep(2000);
         break;
         case MOVE3:
             // Takes in information from a color sensor on the bottom of the robot in order to see if there is a line
 //            if (sensorDown.park){
 //                driver.driveOmni(0f, 0f, 0f);
 //            } else {
 //                driver.driveOmni(0f, 1f, 0f);
 //            }
             nextStep(1000);
         break;
         case MOVE4:
             driver.driveOmni(0f, 0f, 0f);
         break;
         case MOVE5:
             driver.driveOmni(0f, 0f, 0f);
             // General grab code
             nextStep(1000);
         break;
         case MOVE6:
             //General lift code
             nextStep(1000);
         break;
         case MOVE7:
             driver.driveOmni(0f, 1f, 0f);
             nextStep(200);
         break;
         case MOVE8:
             driver.driveOmni(1f, 0f, 0f);
             nextStep(1500);
         break;
         case MOVE9:
             driver.driveOmni(0f, 0f, 0f);
             //general release claw code
             nextStep(500);
         break;
         case MOVE10:
             driver.driveOmni(-1f, 0f, 0f);
             nextStep(1500);
         break;
         case MOVE11:
             driver.driveOmni(0f, -1f, 0f);
             nextStep(200);
         break;
         case MOVE12:
             driver.driveOmni(-1f, 0f, 0f);
             nextStep(1000);
         break;
         case MOVE13:
             driver.driveOmni(0f, 0f, 0f);
             //general claw code
             nextStep(1000);
         break;
         case MOVE14:
            //General lift code
            nextStep(1000);
         break;
         case MOVE15:
             // Takes in information from a color sensor on the bottom of the robot in order to see if there is a line
 //            if (sensorDown.park){
 //                driver.driveOmni(0f, 0f, 0f);
 //            } else {
 //                driver.driveOmni(0f, 1f, 0f);
 //            }
             nextStep(1000);
         case MOVE16:
             driver.driveOmni(0f, 0f, 0f);
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


