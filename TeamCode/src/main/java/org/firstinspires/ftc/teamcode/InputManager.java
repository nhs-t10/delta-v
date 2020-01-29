package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;

/**
 * Handle input (button combos, keybinds, etc.) for gamepads.
 */
public class InputManager extends FeatureManager {
    public InputMode currentMode;
    public Gamepad gamepad;

    public static String lastKey;

    public float currentSpeed = 0.6f;

    public boolean dpad_leftPress = false;
    public boolean dpad_rightPress = false;

    public InputManager(Gamepad _gamepad) {
        this.gamepad = _gamepad;

        this.currentMode = InputMode.DRIVING;
    }

    public Gamepad getGamepad() {
        return this.gamepad;
    }

    public InputManager() {
    }

    /**
     * @return Array of control powers; vertical, horizontal, rotational.
     * @see MovementManager
     */
    public MovementOrder getMovementControls() {
        MovementOrder res;

        if (currentMode == InputMode.DRIVING) {
            res = MovementOrder.HVR(
                    gamepad.left_stick_x,
                    gamepad.left_stick_y,
                    gamepad.right_stick_x
            );
        } else if (currentMode == InputMode.DRIVE_FINETUNE) {
            res = MovementOrder.HVR(
                    gamepad.left_stick_x / INPUT_FINETUNE_SCALE,
                    gamepad.left_stick_y / INPUT_FINETUNE_SCALE,
                    gamepad.right_stick_x / INPUT_FINETUNE_SCALE
            );
        } else if (currentMode == InputMode.DRIVE_SIDEWAYS) {
            res = MovementOrder.VHR(
                    gamepad.left_stick_x,
                    gamepad.left_stick_y,
                    gamepad.right_stick_x
            );
        } else {
            res = MovementOrder.NOTHING;
        }

        return res;
    }

    public float[] getLiftControls() {
        float[] powers = new float[2];

        float motorSpeed = 0f;
        if (gamepad.a) motorSpeed = -1f;
        else if (gamepad.b) motorSpeed = 1f;
        motorSpeed *= FeatureManager.LIFT_RAISE_LOWER_SPEED;

        powers[0] = motorSpeed;

        float servoPos = 0;
                //= FeatureManager.LIFT_CLAMP_CLOSE_POS;
        if (gamepad.right_trigger > 0.8f) servoPos = FeatureManager.LIFT_CLAMP_OPEN_POS;
        if (gamepad.left_trigger > 0.8f) servoPos = FeatureManager.LIFT_CLAMP_CLOSE_POS;

        powers[1] = servoPos;

        return powers;
    }

    public float[] getSideLiftControls() {
        float[] powers = new float[2];
        
        float liftServo = 0f;
        boolean toggleLiftTemp = false;
        if(gamepad.x && toggleLiftTemp == false && getSideLiftPosition() == 0) liftServo = 
