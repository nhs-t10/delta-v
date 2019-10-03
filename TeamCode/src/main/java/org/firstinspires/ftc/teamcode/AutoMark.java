package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
enum step {
    START, MOVE1, MOVE2, MOVE3; 
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
     step[] x = step.value(); 
     int i = 0;
     for (; x[i] != this; i++);
    i++;
    i %= x.length;
    return x[i];
    }
    
int drivelength = [4000,6000,-2000,-4000] //lengths that the bot drives
String direction = ["horizontal","vertical","horizontal","vertical"] //directions that the bot drives

    public void loop() {
       switch(step){
        case(step.START):
           for (int s = 0; s < 4; i++) {
                driver.omnidrive(direction[s])
                nextStep(drivelength[s]);
           } //autonomous that moves fondation into building zo
        break;
        case step.MOVE1:
        if(timer.milliseconds() == 0) currentStep = step.MOVE2;    
        break;
        case step.MOVE2:
    if(timer.milliseconds() == 0) currentStep = step.MOVE3;     
        break;

       }



    }
}


