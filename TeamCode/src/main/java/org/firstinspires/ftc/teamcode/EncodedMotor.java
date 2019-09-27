package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class EncodedMotor {
    private DcMotor motor;
    private int lastTickCount;
    public EncodedMotor(DcMotor _motor) {
        this.motor = motor;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.lastTickCount = 0;


    }
    public EncodedMotor() {}

    public int getTicks() {
        return motor.getCurrentPosition();
    }
    public float getRots() {
        return motor.getCurrentPosition() / FeatureManager.TICK_PER_ROT;
    }
    public void setPower(double power) {
        motor.setPower(power);
    }
    public double getPower() {
        return motor.getPower();
    }
}
