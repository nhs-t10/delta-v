package org.firstinspires.ftc.teamcode.autonomous.miming;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;

@Autonomous(group = "Miming")
public class BL extends OpMode {
    ArrayList<String> instructions;
    MovementManager driver;
    ElapsedTime timer;
    int currentMimeIndex = 0;

    public void init() {
        instructions = (new FileSaver(FeatureManager.MIMING_BL)).readLines();
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
    }
    public void loop() {

            currentMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

            if(currentMimeIndex < instructions.size()) {
                MovementOrder order = MovementOrder.fromString(instructions.get(currentMimeIndex));
                driver.driveOmni(order);
                telemetry.addData("latestThing", instructions.get(currentMimeIndex));
            }

            telemetry.addData("Instructions Completed", currentMimeIndex + "/" + instructions.size());
    }
}
