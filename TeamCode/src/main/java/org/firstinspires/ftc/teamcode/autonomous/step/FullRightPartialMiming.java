package org.firstinspires.ftc.teamcode.autonomous.step;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ColorSensor;
import org.firstinspires.ftc.teamcode.ManipulationManager;
import org.firstinspires.ftc.teamcode.MovementManager;
import org.firstinspires.ftc.teamcode.TelemetryManager;
import org.firstinspires.ftc.teamcode.auxillary.PaulMath;
import org.firstinspires.ftc.teamcode.auxillary.ProfessionalMime;

@Autonomous(group = "Step")
public class FullRightPartialMiming extends LinearOpMode {
    MovementManager driver;
    ManipulationManager hands;
    ElapsedTime runtime = new ElapsedTime();
    ColorSensor sensor;
    ColorSensor sensorDown;
    boolean driveStarted;
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    TelemetryManager logger;
    private long delayRunTime;

    private ElapsedTime timer;

    private boolean delaying = false;

    public void wait(int delay) {
        if(!delaying) {
            timer = new ElapsedTime();
        }
        while(timer.milliseconds() <= delay && opModeIsActive()) {}

        delaying = false;
    }

    final ProfessionalMime mimeTimeToSkyStone = new ProfessionalMime("mime-auto-competition-final-sceneone-right.txt", driver, hands, new Runnable() {
        @Override
        public void run() {
            if(!opModeIsActive()) mimeTimeToSkyStone.setAbort(true);
        }
    }, new Runnable() {
        @Override
        public void run() {
        }
    });
    final ProfessionalMime mimeTimeFromSkyStone = new ProfessionalMime("mime-auto-competition-final-scenetwo-right.txt", driver, hands, new Runnable() {
        @Override
        public void run() {
            if(!opModeIsActive()) mimeTimeFromSkyStone.setAbort(true);
        }
    }, new Runnable() {
        @Override
        public void run() {
        }
    });

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber"),
                hardwareMap.get(Servo.class, "dropper")
        );
        driver.resetAllEncoders();
        driver.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hands.sideLift.setPosition(0);

        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        //Step 1: Drive sideways to the row of blocks
        mimeTimeToSkyStone.start();

        //Step 2: scan along the line for a SkyStone(tm)
        double msStart = System.currentTimeMillis();
        driver.driveVertical(0.5f, -1f, this);

        while (!(sensor.isSkystone() || (System.currentTimeMillis() - msStart) > 1500) && opModeIsActive()) {
        }
        driver.resetAllEncoders();
        driver.driveAuto(0, 0, 0, 0);
        //branching fallback path--
        if (!sensor.isSkystone()) {
            //Step 3 (fallback) go back and try again -only retries once
            msStart = System.currentTimeMillis();
            driver.driveRaw(-1, 1, 1, -1);
            while (!(sensorDown.isBled() || (System.currentTimeMillis() - msStart) > 1000) && opModeIsActive()) {
            }

            //Step 4 (fallback stop)
            driver.driveAuto(0f, 0f, 0f, 0f);
            wait(10);
            stop();
        }


        mimeTimeFromSkyStone.start();



    }


}


