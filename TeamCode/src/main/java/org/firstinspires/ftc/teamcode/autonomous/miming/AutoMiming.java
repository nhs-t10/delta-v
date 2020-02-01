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
    boolean firstLoopRun = true;

    public void init() {
        instructions = (new FileSaver(FeatureManager.MIMING_FILENAME)).readLines();
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber")
        );
        driver.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driver.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driver.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        driver.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }
    public void loop() {

            if(firstLoopRun) timer = new ElapsedTime();
            firstLoopRun = false;

            currentMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

            if(currentMimeIndex < instructions.size()) {
                for(int i = 0; i < 5; i++) if(instructions.get(currentMimeIndex).isEmpty()) currentMimeIndex++;

                state = RobotState.fromString(instructions.get(currentMimeIndex));

                driver.driveOmni(state.movement);

                float[] liftPowers = new float[] {state.liftMotorPower, state.liftServoPos};
                hands.setLiftState(liftPowers);

                hands.setSideGrabberPosition(state.sideGrabPos);
                hands.setSideLiftPosition(state.sideLiftPos);

                hands.setFoundationGrabberPosition(state.foundationMoverPos);
                
                telemetry.addData("latestThing", instructions.get(currentMimeIndex));
            }

            if(state != null){
                telemetry.addData("LastState", state.toString());
                telemetry.addData("Instructions Completed", currentMimeIndex + "/" + instructions.size());
                telemetry.addData("State: ", state);

                telemetry.addData("Servo Pos", hands.getServoPosition());

            }
    }
}
