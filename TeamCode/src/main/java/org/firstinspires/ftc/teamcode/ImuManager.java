package org.firstinspires.ftc.teamcode;

import android.view.VelocityTracker;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.NaiveAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.auxillary.PositionSensorIntegrator;
import org.firstinspires.ftc.teamcode.data.PointNd;


public class ImuManager extends FeatureManager {
    private BNO055IMU imu;
    Orientation angle = new Orientation();
    Velocity timeSpeed = new Velocity();
    public static String status;

    public Thread initLoaderThread;
    public boolean initialized;

    private BNO055IMU.Parameters parameters;

    public ImuManager(){
        this.imu = null;
    }
    public ImuManager(BNO055IMU imu_) {
        this.imu = imu_;
    }
    public void calibrate() {
        parameters = new BNO055IMU.Parameters();

        //defining 
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.accelerationIntegrationAlgorithm = new PositionSensorIntegrator();

        initLoaderThread = new Thread(new IMULoader());
        initLoaderThread.start();
        // IDK if we really need to calibrate it or if we have to, some examples have some don't, let's play it safe

        //however, this causes it to be stuck in init, so i'm taking it out for now.
//        while(!this.imu.isGyroCalibrated()){
//            try{
//                Thread.sleep(50);
//            } catch(InterruptedException e){}
//            status = imu.getCalibrationStatus().toString();
//        }
    }
    public PointNd getPosition() {
        return new PointNd((float)this.getVelocityX(), (float)this.getVelocityY(), (float)this.getVelocityZ());
    }

    public PointNd getOrientation() {
        return new PointNd((float)this.getAccelertationX(), (float)this.getAccelertationY(), (float)this.getAccelertationZ());
    }
    public double getVelocityX(){
        if(!initialized) return 0;
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.xVeloc;
    }
    public double getVelocityY(){
        if(!initialized) return 0;
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.yVeloc;
    }
    public double getVelocityZ(){
        if(!initialized) return 0;
        timeSpeed = this.imu.getVelocity();
        return timeSpeed.zVeloc;
    }
    public double  getAccelertationX(){
        if(!initialized) return 0;
        return this.imu.getAcceleration().xAccel;
    }
    public double  getAccelertationY(){
        if(!initialized) return 0;
        return this.imu.getAcceleration().yAccel;
    }
    public double  getAccelertationZ(){
        if(!initialized) return 0;
        return this.imu.getAcceleration().zAccel;
    }

    public float getAngleOne(){
        if(!initialized) return 0;
        return this.imu.getAngularOrientation().firstAngle;
    }
    public float getAngleTwo(){
        if(!initialized) return 0;
        return this.imu.getAngularOrientation().secondAngle;
    }
    public float getAngleThree(){
        if(!initialized) return 0;
        return this.imu.getAngularOrientation().thirdAngle;
    }


    class IMULoader implements Runnable {
        public void run() {
            boolean re = imu.initialize(parameters);
            initialized = imu.initialize(parameters);
        }
    }
}


