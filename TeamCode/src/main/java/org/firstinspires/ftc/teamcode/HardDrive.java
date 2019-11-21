package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class HardDrive extends OpMode {
    Gamepad input;
    ColorSensor sensor;
    
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    public Gamepad controls;

    public void init() {
         fl = hardwareMap.get(DcMotor.class, "fl");
         fr = hardwareMap.get(DcMotor.class, "fr");
         bl = hardwareMap.get(DcMotor.class, "bl");
         br = hardwareMap.get(DcMotor.class, "br");
    }
    public void loop() {
        float lx = controls.left_stick_x;
        float ly = controls.left_stick_y;
        float rx = controls.right_stick_x;

        //FL, FR, BL, BR
        float[] vertical = {ly, -ly, ly, -ly};
        float[] horizontal = {lx, lx, lx, lx};
        float[] rotational = {-rx, rx, rx, -rx};

        float[] sum = new float[4];

        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }
        
        fl.setPower(Range.clip(sum[0], -1, 1));
        fr.setPower(Range.clip(sum[1], -1, 1));
        bl.setPower(Range.clip(sum[2], -1, 1));
        br.setPower(Range.clip(sum[3], -1, 1));



        telemetry.addData("Input LX: ", controls.left_stick_x);
        telemetry.addData("Input LY: ", controls.left_stick_y);
        telemetry.addData("Input RX: ", controls.right_stick_x);

        telemetry.addData("FL Power: ", fl.getPower());
        telemetry.addData("FR Power: ", fr.getPower());
        telemetry.addData("BL Power: ", bl.getPower());
        telemetry.addData("BR Power: ", br.getPower());
   }
}