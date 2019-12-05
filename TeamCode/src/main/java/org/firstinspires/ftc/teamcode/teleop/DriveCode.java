package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

public class DriveCode extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    public void init() {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");
    }

    public void loop() {
        driveOmni(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

    public void driveRaw(float flp, float frp, float brp, float blp) {
        fl.setPower(flp);
        fr.setPower(frp);
        bl.setPower(blp);
        br.setPower(brp);
    }


    public void driveOmni(float horizontalPower, float verticalPower, float rotationalPower) {
        
        float lX = Range.clip(horizontalPower, -1, 1);
        float lY = Range.clip(verticalPower, -1, 1);
        float rX = Range.clip(rotationalPower, -1, 1);
        
        // Motor powers of fl, fr, br, bl
        float[] vertical = {0.7f * -lY, 0.7f * lY, 0.7f * lY, 0.7f * -lY};
        float[] horizontal = {lX, lX, -lX, -lX};
        float[] rotational = {1f * rX, 1f * rX, 1f * rX, 1f * rX};

        float[] sum = new float[4];
        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }
        //This makes sure that no value is greater than 1 by dividing all of them by the maximum
        float highest = Math.max(Math.max(sum[0], sum[1]), Math.max(sum[2], sum[3]));
        if (Math.abs(highest) > 1) {
            for (int i = 0; i < 4; i++) {
                sum[i] = sum[i] / highest;
            }
        }

        /* makes it go vroom*/
        driveRaw(sum[0], sum[1], sum[2], sum[3]);

    }
}