package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5; 
}

@Autonomous
public class AutoOne extends OpMode {
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
            //Drop bar thing
            nextStep(1500)
        case MOVE2:
            driver.driveOmni(-1f, 0f, 0f);
            nextStep(2500);
        break;
        case MOVE3:
            driver.driveOmni(0f, 1f, 0f);
            nextStep(3000);
        break;
        case MOVE4:
            driver.driveOmni(-1f, 0f, 0f);
            nextStep(3500);
        break:
        case MOVE5:
            driver.driveOmni(0f, -1f, 0f);
        default:
            driver.driveOmni(0f, 0f, 0f);
       }



    }
}


