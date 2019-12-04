package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MovementManager;

enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5, MOVE6, MOVE7, MOVE8, MOVE9, MOVE10, MOVE11, MOVE12, MOVE13, MOVE14, MOVE15,MOVE16; 
}

@Autonomous
public class StepAuto extends OpMode {
    ElapsedTime timer;
    step currentStep = step.START;
    MovementManager driver;

    int numberCalled = 0;
    double referPoint = 0;

    public step getNext() {
        step[] x = step.values();
        int i = 0;
        for (; x[i] != currentStep; i++);
        i++;
        i %= x.length;
        driver.driveOmni(0f, 0f, 0f);
        return x[i];
    }

    public void init() {

    }
    public void loop() {

    }

    void nextStep(int milliseconds) {
        if(timer == null) timer = new ElapsedTime();
        if(currentStep == null) currentStep = step.START;

        

        numberCalled++;
        if(numberCalled == 1) {
            referPoint = timer.milliseconds();
        }
        
        if (timer.milliseconds() - referPoint >= milliseconds) {
            numberCalled = 0;
            currentStep = getNext();
        }
    }
}


