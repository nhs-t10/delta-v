package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5, MOVE6, MOVE7, MOVE8, MOVE9, MOVE10; 
}

@Autonomous
public class AutoFL extends OpMode {
    MovementManager driver;
    ElapsedTime timer;
    step currentStep;
    

    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime(); 
    currentStep = step.START;    
    }  

    

    public step getNext() {
        step[] x = step.values();
        int i = 0;
        for (; x[i] != currentStep; i++);
        i++;
        i %= x.length;
        return x[i];
    }

    //TODO: Implement nextStep
    void nextStep(int milliseconds) {
        
    }
    
    public void loop() {
       switch(currentStep){
        case START:
            driver.driveOmni(1f, 0f, 0f);
            nextStep(1000)
            ge
        break;
        case MOVE1:
            driver.driveOmni(0f, -1f, 0f)
            nextStep(1500)
        case MOVE2:
            //sampling
            nextStep(2500);
        break;
        case MOVE3:
            //picking up a sky stone
            nextStep(3000);
        break;
        case MOVE4:
            driver.driveOmni(-1f, 0f, 0f);
            nextStep(3500);
        break;
        case MOVE5:
            driver.driveOmni(0f, 1f, 0f);
            nextStep(4500);
        break;
        case MOVE6:
            driver.driveOmni(0f, -1f, 0f);
            nextStep(5500);
        break;
        case MOVE7:
            driver.driveOmni(1f, 0f, 0f);
            nextStep(6000);
        break;
        case MOVE8:
            //finding last skystone
            nextStep(7000);
        break;
        case MOVE9:
        driver.driveOmni(-1f, 0f, 0f);
        nextStep(7500);
        break;
        case MOVE10:
        driver.driveOmni(0f, 1f, 0f);
        nextStep(8500);
    break;
        default:
            driver.driveOmni(0f, 0f, 0f);
       }



    }
}


