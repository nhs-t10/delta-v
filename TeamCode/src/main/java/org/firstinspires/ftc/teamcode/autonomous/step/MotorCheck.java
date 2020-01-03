package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.autonomous.step.StepAuto;

@Autonomous
public class MotorCheck extends StepAuto {
    DcMotor one;
    DcMotor two;
    DcMotor three;
    DcMotor four;


    public void init() {
         one = hardwareMap.get(DcMotor.class, "fl");
         two = hardwareMap.get(DcMotor.class, "fr");
         three = hardwareMap.get(DcMotor.class, "bl");
         four = hardwareMap.get(DcMotor.class, "br");
    }

    void mStop(){
        one.setPower(0);
        two.setPower(0);
        three.setPower(0);
        four.setPower(0);

    }
    
    public void loop() {
       switch(currentStep){
        case START:
           nextStep(1000);
        break;
        case MOVE1:

           nextStep(2500);
        case MOVE2:
          
        break;
        case MOVE3:
            
        break;
        case MOVE4:
            
        break;
        default:

       }

    }
}


