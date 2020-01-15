package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.auxillary.*;
import java.util.HashMap;

/**
 * Handle all movement of the chassis.
 */
public class TelemetryManager extends FeatureManager {

    public Telemetry telemetry;
    /**
     * Create a TelemetryManager with four motors.
     * @param fl Front Left motor
     * @param fr Front Right motor
     * @param br Back Right motor
     * @param bl Back Left motor
     */

    private static float speed = 1.0f;

    public TelemetryManager(Telemetry _telemetry) {
        
        this.telemetry = _telemetry;
    }
    public TelemetryManager(){ }

    /**
     * 
     */
    public void driveRaw(float fl, float fr, float br, float bl) {
    }

}
