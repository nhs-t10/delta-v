package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@Autonomous
public class AutoMiming extends OpMode {
    ArrayList<String> instructions;
    MovementManager driver;
    ElapsedTime timer;
    int currentMimeIndex = 0;

    public void init() {
        instructions = (new FileSaver(FeatureManager.MIMING_FILENAME)).readLines();
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
    }
    public void loop() {

            currentMimeIndex = (int)Math.floor(timer.milliseconds() / FeatureManager.MIMING_MS_PER_SAMPLE);

            if(currentMimeIndex < instructions.size()) {
                float[] drivable = new float[3];
                String temp = instructions.get(currentMimeIndex);
                String[] splitInstructions = temp.substring(1, temp.length() - 1).split(",");
                for (int i = 0; i < splitInstructions.length; i++) {
                    drivable[i] = Float.parseFloat(splitInstructions[i]);
                }
                driver.driveOmni(drivable);
                telemetry.addData("latestThing", instructions.get(currentMimeIndex));
            }

            telemetry.addData("Instructions Completed", currentMimeIndex + "/" + instructions.size());
    }
}
