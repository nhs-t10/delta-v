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
        } else {
            res = MovementOrder.NOTHING;
        }

        return res;
    }

    public float[] getLiftControls() {
        float[] powers = new float[2];

        float motorSpeed = 0f;
        if (gamepad.dpad_up) motorSpeed = -1f;
        else if (gamepad.dpad_down) motorSpeed = 1f;
        motorSpeed *= FeatureManager.LIFT_RAISE_LOWER_SPEED;

        powers[0] = motorSpeed;

        float servoPos = FeatureManager.LIFT_CLAMP_CLOSE_POS;
        if (gamepad.left_trigger > 0.8f) servoPos = FeatureManager.LIFT_CLAMP_OPEN_POS;

        powers[1] = servoPos;

        return powers;
    }

    public RobotState getState() {
        MovementOrder move = this.getMovementControls();
        float[] powers = this.getLiftControls();

        return new RobotState(new ElapsedTime(), powers[0], powers[1], move);

    }

    /**
     * Change the current driving mode.
     *
     * @param newMode Mode to switch to.
     */
    public void setCurrentMode(InputMode newMode) {
        currentMode = newMode;
    }

    /**
     * Switch from fine-tuning to non-fine-tuning
     */
    public void toggleFinetune() {
        if (currentMode == InputMode.DRIVE_FINETUNE) currentMode = InputMode.DRIVING;
        else if (currentMode == InputMode.DRIVING) currentMode = InputMode.DRIVE_FINETUNE;
    }

    /**
     * Test for a given button combo (A/B/X/Y)
     *
     * @param buttons Button letters -- e.g. "YA" for [Y] + [A]
     * @return Whether the combo is currently active or not.
     */
    public boolean combo(String buttons) {
        char[] buttonLetters = buttons.toCharArray();
        boolean result = true;
        for (int i = 0; i < buttonLetters.length; i++) {
            char letter = buttonLetters[i];
            if (letter == 'A' && !gamepad.a) result = false;
            if (letter == 'B' && !gamepad.b) result = false;
            if (letter == 'X' && !gamepad.x) result = false;
            if (letter == 'Y' && !gamepad.y) result = false;
        }
        if (gamepad.timestamp > INPUT_DOUBLECLICK_TIME) result = false;

        return result;
    }

}

enum InputMode {
    DRIVING, DRIVE_FINETUNE
}

class GamepadCb implements Gamepad.GamepadCallback {
    public void gamepadChanged(Gamepad gamepad) {
        InputManager.lastKey = gamepad.left_stick_y + "";
    }
}