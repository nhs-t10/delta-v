
package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MovementManager;

import java.util.ArrayList;

@Autonomous(group = "Step")
public class AutoMark extends OpMode {
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
    
int[] drivelength = {4000,6000,-2000,-4000}; //lengths that the bot drives
String[] direction = {"horizontal","vertical","horizontal","vertical"}; //directions that the bot drives

    public void loop() {
       switch(currentStep){
        case START:
           for (int s = 0; s < 4; s++) {
                //driver.driveOmni(direction[s]);
           } //autonomous that moves fondation into building zo
        break;
        case MOVE1:
        if(timer.milliseconds() == 0) currentStep = step.MOVE2;    
        break;
        case MOVE2:
    if(timer.milliseconds() == 0) currentStep = step.MOVE3;     
        break;

       }



    }
}


