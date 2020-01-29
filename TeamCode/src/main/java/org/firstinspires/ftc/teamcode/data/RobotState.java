    package org.firstinspires.ftc.teamcode.data;

    import android.graphics.Point;
    import android.os.Build;
    import android.text.Layout;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.qualcomm.robotcore.util.ElapsedTime;

    import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
    import org.firstinspires.ftc.robotcore.external.navigation.Position;
    import org.firstinspires.ftc.teamcode.MovementManager;
    import org.firstinspires.ftc.teamcode.auxillary.PaulMath;

    import java.util.ArrayList;
    import java.util.Arrays;

    public class RobotState {

        public final static float version = 3f;
        public static RobotState current;

        public float speed;
        public float time;
        public ElapsedTime timer;

        public MovementOrder movement;

        public float liftMotorPower;
        public float liftServoPos;

        public float sideLiftPos;
        public float sideGrabPos;
        
        public float foundationMoverPos;

        public double blDrivePower;
        public double frDrivePower;
        public double flDrivePower;
        public double brDrivePower;

        public PointNd orientation;
        public PointNd position;

        public RobotState (ElapsedTime tmr, float _liftMotorPower, float _liftServoPos, MovementOrder _movement, float _speed, PointNd _ori, PointNd _pos, float _sideLiftPos, float _sideGrabPos, float _foundationMoverPos) {
            this.liftServoPos = _liftServoPos;
            this.liftMotorPower = _liftMotorPower;
            this.timer = tmr;
            this.speed = _speed;

            this.time = (float)timer.milliseconds();

            this.movement = _movement;

            this.orientation = _ori;
            this.position = _pos;

            this.sideLiftPos = _sideLiftPos;
            this.sideGrabPos = _sideGrabPos;

            this.foundationMoverPos = _foundationMoverPos;
        }

        public RobotState (ElapsedTime tmr, float _liftMotorPower, float _liftServoPos, MovementOrder _movement, float _speed, PointNd _ori, PointNd _pos) {
            this.liftServoPos = _liftServoPos;
            this.liftMotorPower = _liftMotorPower;
            this.timer = tmr;
            this.speed = _speed;

            this.time = (float)timer.milliseconds();

            this.movement = _movement;

            this.orientation = _ori;
            this.position = _pos;
        }
        public RobotState(int msTime, float _liftMotorPower, float _liftServoPos, MovementOrder _movement, float _speed, PointNd _ori, PointNd _pos) {
            this.liftServoPos = _liftServoPos;
            this.liftMotorPower = _liftMotorPower;

            this.time = (float)msTime;

            this.movement = _movement;

            this.orientation = _ori;
            this.position = _pos;
        }

        public RobotState(int msTime, float _liftMotorPower, float _liftServoPos) {
            this.liftServoPos = _liftServoPos;
            this.liftMotorPower = _liftMotorPower;

            this.time = (float)msTime;   
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

        public void setRawMotors(DcMotor frontRight, DcMotor frontLeft, DcMotor backLeft, DcMotor backRight) {
            this.blDrivePower = backLeft.getPower();
            this.brDrivePower = backRight.getPower();
            this.flDrivePower = frontLeft.getPower();
            this.frDrivePower = frontRight.getPower();
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

        public PointNd getOrientation() {
            return orientation;
        }

        public void setOrientation(PointNd orientation) {
            this.orientation = orientation;
        }

        public PointNd getPosition() {
            return position;
        }

        public void setPosition(PointNd position) {
            this.position = position;
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
            return RobotState.version + "," + movement.toString() + "," + liftMotorPower + "," + liftServoPos + "," + speed + "," + orientation.toString() + "," + position.toString();
        }

        public static RobotState fromString(String str) {
            String[] strSplit = str.split(",");

            //validate version-- if the version is old, return an error
            if(Float.parseFloat(strSplit[0]) != RobotState.version) throw new IllegalArgumentException("Wrong version");

            //the first non-version three numbers are the MovementOrder power
            String[] strMove = Arrays.copyOfRange(strSplit, 1, 4);
            MovementOrder order = MovementOrder.fromString(strMove[0] + "," + strMove[1] + "," + strMove[2]);

            float liftMotorPower = Float.parseFloat(strSplit[4]);

            float liftServoPos = Float.parseFloat(strSplit[5]);

            float speed = Float.parseFloat(strSplit[6]);

            PointNd ori = PointNd.fromString(PaulMath.join(",", Arrays.copyOfRange(strSplit, 7, 10)));
            PointNd pos = PointNd.fromString(PaulMath.join(",", Arrays.copyOfRange(strSplit, 11, 14)));

            return new RobotState(new ElapsedTime(), liftMotorPower, liftServoPos, order, speed, ori, pos);
        }
    }
