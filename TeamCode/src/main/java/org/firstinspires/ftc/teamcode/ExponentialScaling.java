package org.firstinspires.ftc.teamcode;

public class ExponentialScaling extends MovementManager {
    public float scalingFactor(float verticalPower, float horizontalPower, float rotationalPower) {
       float[] sum = omniCalc(verticalPower, horizontalPower, rotationalPower);
       float factorSquared = 0;
       for(int i = 0; i<4; i++) {
           factorSquared += Math.abs(sum[i]);
       }
       float factor = (float)((1-Math.exp(-(double)factorSquared))/(1+Math.exp((double)factorSquared)));
       return factor;
    }

    public void driveOmniScaled(float verticalPower, float horizontalPower, float rotationalPower) {
        float[] sum = omniCalc(verticalPower, horizontalPower, rotationalPower);
        float factor = scalingFactor(verticalPower, horizontalPower, rotationalPower);
        driveRaw(factor*sum[0], factor*sum[1], factor*sum[2], factor*sum[3]);
    }
}
