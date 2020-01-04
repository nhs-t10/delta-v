package org.firstinspires.ftc.teamcode.autonomous.miming;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;

@Autonomous(group = "Miming")
public class AutoMiming extends OpMode {
    ArrayList<String> instructions;
    MovementManager driver;
    ElapsedTime timer;
    ManipulationManager hands;
    RobotState state;

    int currentMimeIndex = 0;

    public void init() {
        instructions = (new FileSaver(FeatureManager.MIMING_FILENAME)).readLines();
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"));
    }
    public void loop() {

            currentMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

            if(currentMimeIndex < instructions.size()) {
                state = RobotState.fromString(instructions.get(currentMimeIndex));

                driver.driveOmni(state.movement);

                float[] liftPowers = new float[] {state.liftMotorPower, state.liftServoPos};
                hands.setLiftState(liftPowers);
                
                telemetry.addData("latestThing", instructions.get(currentMimeIndex));
            }

            if(state != null){
                telemetry.addData("LastState", state.toString());
                telemetry.addData("Instructions Completed", currentMimeIndex + "/" + instructions.size());
                telemetry.addData("State: ", state);

            }
    }
}
