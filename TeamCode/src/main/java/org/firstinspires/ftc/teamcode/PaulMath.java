package org.firstinspires.ftc.teamcode;

public class PaulMath {
    float x_initial = 0;
    float y_initial = 0;
    /**
     * Get the distance between two points
     * @param origin Point to calculate from
     * @param destination Point to calculate to
     * @return Distance between origin and destination
     */
    public static float distance(Point3d origin, Point3d destination) {
        return (float)Math.sqrt(((origin.x*origin.x)+(origin.y*origin.y))+(destination.z*destination.z));
    }

    /**
     * Get the current location of robot
     * @param array of all previous velocities in the x direction
     * @param array of all previous velocities in the y direction
     * @return a 2 dimentional point location for the robot
     */
    public Point2d location(float[] velocities_x, float[] velocities_y) {
        float delta_x = 0;
        float delat_y = 0;
        for (float velocity: velocities_x) {
            delta_x = delat_x + velocity;
        }
        for (float velocity: velocities_y) {
            delta_y = delta_y + velocity;
        }
        float x_current = x_initial + delta_x;
        float y_current = y_initial + delta_y;
        Point2d location = new Point2d(x_current, y_current);
        return location;

    }


}
