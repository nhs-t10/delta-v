package org.firstinspires.ftc.teamcode;

public class PaulMath {

    /**
     * Get the distance between two points
     * @param origin Point to calculate from
     * @param destination Point to calculate to
     * @return Distance between origin and destination
     */
    public static float distance(Point3d origin, Point3d destination) {
        return (float)Math.sqrt(((origin.x*origin.x)+(origin.y*origin.y))+(destination.z*destination.z));
    }


}
