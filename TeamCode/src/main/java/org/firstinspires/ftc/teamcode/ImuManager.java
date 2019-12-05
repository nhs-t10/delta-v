package org.firstinspires.ftc.teamcode;

import android.view.VelocityTracker;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.NaiveAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;



public class ImuManager extends FeatureManager {
    private BNO055IMU imu;
    Orientation angle = new Orientation();
    Velocity timeSpeed = new Velocity();
    public static String status;

    public ImuManager(){
        this.imu = null;
    }
    public ImuManager(BNO055IMU imu_) {
        this.imu = imu_;
    }
    public void calibrate(){
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();

        //defining 
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;
        params.calibrationDataFile = "BNO055IMUCalibration.json";
//        params.accelerationIntegrationAlgorithm = new PositionSensorIntegrator();


        this.imu.initialize(params);
        // IDK if we really ned to calibrate it or if we have to, some examples have some don't, let's play it safe

        //however, this causes it to be stuck in init, so i'm taking it out for now.
//        while(!this.imu.isGyroCalibrated()){
//            try{
//                Thread.sleep(50);
//            } catch(InterruptedException e){}
//            status = imu.getCalibrationStatus().toString();
//        }
    }
    public double getVelocityX(){
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.xVeloc;
    }
    public double getVelocityY(){
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.yVeloc;
    }
    public double getVelocityZ(){
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.zVeloc;
    }
    public double  getAccelertationX(){
        return this.imu.getAcceleration().xAccel;
    }
    public double  getAccelertationY(){
        return this.imu.getAcceleration().yAccel;
    }
    public double  getAccelertationZ(){
        return this.imu.getAcceleration().zAccel;
    }

    public float getAngleOne(){
       return this.imu.getAngularOrientation().firstAngle;
    }
    public float getAngleTwo(){
        return this.imu.getAngularOrientation().secondAngle;
    }
    public float getAngleThree(){
        return this.imu.getAngularOrientation().thirdAngle;
    }
}