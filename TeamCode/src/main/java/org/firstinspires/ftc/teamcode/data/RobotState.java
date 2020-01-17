    package org.firstinspires.ftc.teamcode.data;

    import android.os.Build;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.qualcomm.robotcore.util.ElapsedTime;

    import org.firstinspires.ftc.teamcode.MovementManager;

    import java.util.ArrayList;
    import java.util.Arrays;

    public class RobotState {

        public final static float version = 2f;
        public static RobotState current;

        public float speed;
        public float time;
        public ElapsedTime timer;

        public MovementOrder movement;

        public float liftMotorPower;

        public float liftServoPos;

        public double blDrivePower;
        public double frDrivePower;
        public double flDrivePower;
        public double brDrivePower;

        public RobotState (ElapsedTime tmr, float _liftMotorPower, float _liftServoPos, MovementOrder _movement, float _speed) {
            this.liftServoPos = _liftServoPos;
            this.liftMotorPower = _liftMotorPower;
            this.timer = tmr;
            this.speed = _speed;

            this.time = (float)timer.milliseconds();

            this.movement = _movement;
        }

        public float getTime() {return this.time; }

        public float get(String key) {
            this.time = (float)timer.milliseconds();
            if(key == "TIME") return this.time;
            return 0f;
        }

        public void setRawMotors(MovementManager driver) {
            this.blDrivePower = driver.backLeft.getPower();
            this.brDrivePower = driver.backRight.getPower();
            this.flDrivePower = driver.frontLeft.getPower();
            this.frDrivePower = driver.frontRight.getPower();
        }

        public static float getVersion() {
            return version;
        }

        public static RobotState getCurrent() {
            return current;
        }

        public static void setCurrent(RobotState current) {
            RobotState.current = current;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public ElapsedTime getTimer() {
            return timer;
        }

        public void setTimer(ElapsedTime timer) {
            this.timer = timer;
        }

        public MovementOrder getMovement() {
            return movement;
        }

        public void setMovement(MovementOrder movement) {
            this.movement = movement;
        }

        public float getLiftMotorPower() {
            return liftMotorPower;
        }

        public void setLiftMotorPower(float liftMotorPower) {
            this.liftMotorPower = liftMotorPower;
        }

        public float getLiftServoPos() {
            return liftServoPos;
        }

        public void setLiftServoPos(float liftServoPos) {
            this.liftServoPos = liftServoPos;
        }

        public String toString() {
            return RobotState.version + "," + movement.toString() + "," + liftMotorPower + "," + liftServoPos + ",";
        }

        public static RobotState fromString(String str) {
            String[] strSplit = str.split(",");

            //validate version-- if the version is old, return an erorr
            if(Float.parseFloat(strSplit[0]) != RobotState.version) throw new IllegalArgumentException("Wrong version");

            //the first non-version three numbers are the movementorder power
            String[] strMove = Arrays.copyOfRange(strSplit, 1, 4);
            MovementOrder order = MovementOrder.fromString(strMove[0] + "," + strMove[1] + "," + strMove[2]);

            float liftMotorPower = Float.parseFloat(strSplit[4]);

            float liftServoPos = Float.parseFloat(strSplit[5]);

            float speed = Float.parseFloat(strSplit[6]);

            return new RobotState(new ElapsedTime(), liftMotorPower, liftServoPos, order, speed);
        }
    }
