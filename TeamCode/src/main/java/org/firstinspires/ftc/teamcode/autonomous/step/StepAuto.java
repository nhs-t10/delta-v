package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MovementManager;

enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5, MOVE6, MOVE7, MOVE8, MOVE9, MOVE10, MOVE11, MOVE12, MOVE13, MOVE14, MOVE15, MOVE16, MOVE17, MOVE18, MOVE19, MOVE20, MOVE21, MOVE22, MOVE23, MOVE24, MOVE25, MOVE26, MOVE27, MOVE28, MOVE29, MOVE30, MOVE31, MOVE32;
}

@Autonomous
public class StepAuto extends OpMode {
    ElapsedTime timer;
    step currentStep = step.START;

    int numberCalled = 0;
    double referPoint = 0;

    public step getNext() {
        step[] x = step.values();
        int i = 0;
        for (; x[i] != currentStep; i++);
        i++;
        i %= x.length;
        return x[i];
    }

    public void init() {

    }
    public void loop() {

    }

    void nextStep(int milliseconds) {
        if(timer == null)
            timer = new ElapsedTime();
        if(currentStep == null)
            currentStep = step.START;

        

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


