package org.firstinspires.ftc.teamcode.auxillary;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FeatureManager;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.data.RobotState;

import java.util.ArrayList;

public class ProfessionalMime {

    ArrayList<String> instructions;
    MovementManager driver;
    ElapsedTime timer;
    ManipulationManager hands;
    RobotState state;

    Runnable iterationCallback;
    Runnable endCallback;

    int currentMimeIndex = 0;
    boolean firstLoopRun = true;

    public ProfessionalMime(String fileName, MovementManager _driver, ManipulationManager _hands, Runnable _iterationCallback, Runnable _endCallback) {
        instructions = (new FileSaver(fileName)).readLines();
        this.driver = _driver;
        this.hands = _hands;

        iterationCallback = _iterationCallback;
        endCallback = _endCallback;
    }

    public void start() {
        timer = new ElapsedTime();

        while(currentMimeIndex < instructions.size()) {
            currentMimeIndex = (int) Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

            state = RobotState.fromString(instructions.get(currentMimeIndex));

            driver.driveOmni(state.movement);

            float[] liftPowers = new float[] {state.liftMotorPower, state.liftServoPos};
            hands.setLiftState(liftPowers);

            hands.setSideGrabberPosition(state.sideGrabPos);
            hands.setSideLiftPosition(state.sideLiftPos);

            hands.setFoundationGrabberPosition(state.foundationMoverPos);

            iterationCallback.run();
        }
        endCallback.run();
    }

}
