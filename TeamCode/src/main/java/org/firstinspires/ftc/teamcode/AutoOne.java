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
    
    public void loop() {
       switch(step){
        case(step.START):
        if(timer.milliseconds() == 0) currentStep = step.MOVE1;
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


