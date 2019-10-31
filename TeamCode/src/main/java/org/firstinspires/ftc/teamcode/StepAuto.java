package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Timer;

enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5, MOVE6, MOVE7, MOVE8, MOVE9, MOVE10; 
}

@Autonomous
public class StepAuto extends OpMode {
    ElapsedTime timer;
    step currentStep;

    int numberCalled = 0;
    double referPoint = timer.milliseconds();

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


