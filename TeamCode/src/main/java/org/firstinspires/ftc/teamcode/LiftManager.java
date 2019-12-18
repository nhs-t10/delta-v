package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class LiftManager extends FeatureManager {
    private DcMotor motor;
    
    private int currentLiftLevel;


    public LiftManager(DcMotor _motor) {
        this.motor = _motor;
    }

    /**
     * Raise or lower the lift to the given level
     * @param level Level to move to.
     */
    public void raiseLift(int level) {
        
    }

    /**
     * Close the pinchy-pinchies
     */
    public void grab() {
        
    }

    /**
     * Open the pinchy-pinchies
     */
    public void letGo() {

    }
}