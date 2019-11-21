package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@Autonomous
public class AutoMiming extends OpMode {
    ParsedScript script;
    FileSaver file;
    MovementManager driver;
    ElapsedTime timer;
    int currentLine = 0;

    public void init() {
        file = new FileSaver(FeatureManager.MIMING_FILENAME);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
        script = new ParsedScript(instructions).readLines();
    }
    public void loop() {

            MovementOrder currentOrder = script.getStatement(currentLine).order;
            if(script.getStatement(currentLine).finished(RobotState.current)) currentLine++;

            telemetry.addData("Instructions Completed", currentLine + "/" + script.statements.length);
    }
}
