package org.firstinspires.ftc.teamcode;

import android.view.VelocityTracker;

import java.beans.FeatureDescriptor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;



public class ImuManager extends FeatureManager {
    private BNO055IMU imu;
    Orientation angle = new Orientation();
    Velocity timeSpeed = new Velocity();

    public ImuManager(BNO055IMU imu_) {
        this.imu = imu_;
    }
    public void calabrate(){
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;
        params.calibrationDataFile = "BNO055IMUCalibration.json";

        this.imu.initialize(params);
        // IDK if we really ned to calibrate it or if we have to, some examples have some don't, let's play it safe
        while(!this.imu.isGyroCalibrated()){
            try{
                Thread.sleep(50);
            } catch(InterruptedException e){}
            Telemetry.addData("INITIALIZING: ", this.imu.getCalibrationStatus());
        }
    }
    public float getVelocity(){
        timeSpeed = this.imu.getVelocity();
        return timeSpeed //need to be turned into float/int/double
    }
    public Acceleration getAccelertation(){
        return this.imu.getAcceleration(); //need to be turned into float/int/double
    }
    public float getAngle(){
        this.imu.getAngularOrientation(); //need to be turned into float/int/double
    }
}