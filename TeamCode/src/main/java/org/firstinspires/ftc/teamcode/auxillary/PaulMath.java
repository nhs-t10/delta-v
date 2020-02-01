package org.firstinspires.ftc.teamcode.auxillary;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.data.*;

public class PaulMath {

    public static final float PID_P_COEF = 0.03f;
    final double ENCODER_CPR = 1120;
    final double GEAR_RATIO = 1;
    final double WHEEL_DIAMETER = 4;
    final double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    /**
     * A PID controller
     * @param current the current value of the sensor
     * @param target the value that you want it to be
     * @return the new value of the sensor
     */
    public static float pid(float current, float target) {
        return  current + ((target - current)*PaulMath.PID_P_COEF);
    }

    /**
     * Get the distance between two points
     * @param origin Point to calculate from
     * @param destination Point to calculate to
     * @return Distance between origin and destination
     */
    public static float distance(PointNd origin, PointNd destination) {
        return (float)Math.sqrt(((origin.getX()*origin.getX())+(origin.getY()*origin.getY()))+(destination.getZ()*destination.getZ()));
    }

    /**
     * Get the current location of robot
     * @param velocities_x array of all previous velocities in the x direction
     * @param velocities_y array of all previous velocities in the y direction
     * @return a 2 dimentional point location for the robot
     */
    public static PointNd location(float[] velocities_x, float[] velocities_y) {
        float x_initial = 0;
        float y_initial = 0;

        float delta_x = 0;
        float delta_y = 0;
        for (float velocity: velocities_x) {
            delta_x = delta_x + velocity;
        }
        for (float velocity: velocities_y) {
            delta_y = delta_y + velocity;
        }
        float x_current = x_initial + delta_x;
        float y_current = y_initial + delta_y;
        PointNd location = new PointNd(x_current, y_current);
        return location;

    }

    /**
     * Joins a string array with a delim
     * @param delim the deliminator.
     * @param arr the array to join. Objects will be converted to string
     * @return the joined string
    */
    public static String join(String delim, Object[] arr) {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < arr.length; i++) {
            res.append(arr[i].toString());
            if(i + 1 <= arr.length) res.append(delim);
        }

        return res.toString();
    }
    /**
     * Round a given number to a decimal place.
     * @param input Number to round.
     * @param place Place to round to-- e.g. 10 will round to 10ths
     * @return Rounded number
     */ 
    public static float roundToPoint(float input, float place) {
        return Math.round(input / place) * place;
    }

    public static float highestValue(float[] array) {
        int arrayLength = array.length;
        float highest = -1000000;
        for (int i = 0; i < arrayLength; i++) {
            if(array[i] > highest) {
                highest = array[i];
            }
        }
        return highest;
    }

    public static float[] normalizeArray(float[] array) {
        int arrayLength = array.length;
        float highest = highestValue(array);
        for (int i = 0; i < arrayLength; i++) {
            array[i] = array[i] / highest;
        }
        return array;
    }

    public int encoderDistance(double distance) {
        double ROTATIONS = distance / CIRCUMFERENCE;
        int counts =  (int)(ENCODER_CPR * ROTATIONS * GEAR_RATIO);
        return counts;
    }

    /**
     * Drives using vert, hor, rot inputs.
     * @param horizontalPower Horizontal input
     * @param verticalPower Verticl input
     * @param rotationalPower Rotational input
     */
    //confusing names we are trubleshooting
    public static float[] omniCalc(float verticalPower, float horizontalPower, float rotationalPower) {
        float lx = Range.clip(horizontalPower, -1, 1);
        float lY = Range.clip(verticalPower, -1, 1);
        float rx = Range.clip(rotationalPower, -1, 1);

        // Motor powers of fl, fr, br, bl
        // Motor powers used to be 0.4f for all motors other than fl
        float[] vertical = {-lY, lY, -lY, lY};
        float[] horizontal = {lx, 0.7f*lx, -0.7f*lx, -0.7f*lx};
        float[] rotational = {rx, rx, rx, rx};

        float[] sum = new float[4];
        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }
        //This makes sure that no value is greater than 1 by dividing all of them by the maximum
        float highest = highestValue(sum);
        if(highest > 1) {
            normalizeArray(sum);
        }
        return sum;
    }

}
