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
        if(timer.milliseconds() % FeatureManager.MIMING_MS_PER_SAMPLE == 0) {
            float[] drivable = new float[3];
            String[] split = instructions.get(currentMimeIndex).split(",");
            for(int i = 0; i < split.length; i++) {
                drivable[i] = Float.parseFloat(split[i]);
            }
            driver.driveOmni(drivable);
        }
    }
}
