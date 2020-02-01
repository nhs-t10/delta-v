package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import java.util.ArrayList;
import java.util.HashMap;

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
    public boolean dpad_leftBumper = false;

    public HashMap<String,Float> lastPresses = new HashMap<>();
    public HashMap<String,Boolean> togglePresses = new HashMap<>();

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
                    resolveControl(ControlMap.MOVE_HORIZONTAL),
                    resolveControl(ControlMap.MOVE_VERTICAL),
                    resolveControl(ControlMap.MOVE_ROTATIONAL)
            );
        } else if (currentMode == InputMode.DRIVE_FINETUNE) {
            res = MovementOrder.HVR(
                    resolveControl(ControlMap.MOVE_HORIZONTAL) / FeatureManager.INPUT_FINETUNE_SCALE,
                    resolveControl(ControlMap.MOVE_VERTICAL) / FeatureManager.INPUT_FINETUNE_SCALE,
                    resolveControl(ControlMap.MOVE_ROTATIONAL) / FeatureManager.INPUT_FINETUNE_SCALE
            );
        } else if (currentMode == InputMode.DRIVE_SIDEWAYS) {
            res = MovementOrder.VHR(
                    resolveControl(ControlMap.MOVE_HORIZONTAL),
                    resolveControl(ControlMap.MOVE_VERTICAL),
                    resolveControl(ControlMap.MOVE_ROTATIONAL)
            );
        } else {
            res = MovementOrder.NOTHING;
        }

        return res;
    }

    public float[] getLiftControls() {
        float[] powers = new float[2];

        powers[0] = resolveControl(ControlMap.LIFT_MAIN) * FeatureManager.LIFT_RAISE_LOWER_SPEED;

        powers[1] = resolveControl(ControlMap.CLAMP_MAIN);

        return powers;
    }
    public float[] getSideLiftControls() {
        float[] powers = new float[2];

        powers[0] = resolveControl(ControlMap.LIFT_SIDE);
        powers[1] = resolveControl(ControlMap.CLAMP_SIDE);

        return powers;
    }

    public float getFoundationMoverPos() {

        float pos = 0f;
        if (resolveControl(ControlMap.FOUNDATION_MOVER) != 0) pos = 1f;

        return pos;
    }

    public RobotState getState() {
        MovementOrder move = this.getMovementControls();
        float[] liftPowers = this.getLiftControls();
        float[] sideLiftPowers = this.getSideLiftControls();
        float foundationMoverPos = this.getFoundationMoverPos();

        return new RobotState(new ElapsedTime(), liftPowers[0], liftPowers[1], move, currentSpeed, new PointNd(0,0,0), new PointNd(0,0,0), sideLiftPowers[0], sideLiftPowers[1], foundationMoverPos);
    }

    public float getCurrentSpeed() {
        return resolveControl(ControlMap.SPEED_TOGGLE);
    }

    public int getLogTabSwitchDelta() {
        return (int)Math.round(resolveControl(ControlMap.TELE_TAB));
    }

    public double getDropperPosition() {
        return (double)resolveControl(ControlMap.DROPPER);
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

    public boolean press(String button) {
        boolean result = false;
        if((lastPresses.containsKey(button) ? lastPresses.get(button) : 0f) > 0 && resolveControl(button) == 0) result = true;

        lastPresses.put(button, resolveControl(button));

        return result;
    }

    public float toggleButton(String button, float res1, float res2) {
        if(!togglePresses.containsKey(button)) togglePresses.put(button, false);

        if(press(button)) {
            boolean lastVal = togglePresses.get(button);

            togglePresses.put(button, !lastVal);
        }

        lastPresses.put(button, resolveControl(button));

        return togglePresses.get(button)?res1:res2;
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

    public float resolveControl(String control) {
        if(control.toLowerCase().equals(control)) return getButtonState(control);
        else return combo(control)?1f:0f;
    }

    public float resolveControl(Control control) {
        //if it's a scalar
        if (control.isScalar) {
            return resolveControl(control.on);
        }
        //if is 1-button toggle
        else if (control.off.isEmpty()) {
            return resolveControl(control.on) > 0.1?control.value1:control.value2;
        }
        //if it's a scalar

        //if it's a 2-button toggle
        else {
            if(resolveControl(control.on) > 0f) return control.value1;
            else if(resolveControl(control.off) > 0f) return control.value2;
            else return control.value3;
        }
    }

    /**
     * Get the current state of a button on the gamepad
     * @param button The name of the button to get the state of
     * @return The current state of the button
     */
    public float getButtonState(String button) {
        switch(button) {
            case "left_stick_x":
                return gamepad.left_stick_x;
            case "left_stick_y":
                return gamepad.left_stick_y;

            case "right_stick_x":
                return gamepad.right_stick_x;
            case "right_stick_y":
                return gamepad.right_stick_y;

            case "left_trigger":
                return gamepad.left_trigger;
            case "right_trigger":
                return gamepad.right_trigger;

            case "left_bumper":
                return gamepad.left_bumper?1f:0f;
            case "right_bumper":
                return gamepad.right_bumper?1f:0f;

            case "a":
                return gamepad.a?1f:0f;
            case "b":
                return gamepad.b?1f:0f;
            case "x":
                return gamepad.x?1f:0f;
            case "y":
                return gamepad.y?1f:0f;

            case "dpad_left":
                return gamepad.dpad_left?1f:0f;
            case "dpad_right":
                return gamepad.dpad_right?1f:0f;
            case "dpad_up":
                return gamepad.dpad_up?1f:0f;
            case "dpad_down":
                return gamepad.dpad_down?1f:0f;

            default:
                return 0f;
        }
    }
}

enum InputMode {
    DRIVING, DRIVE_FINETUNE, DRIVE_SIDEWAYS
}

class GamepadCb implements Gamepad.GamepadCallback {
    public void gamepadChanged(Gamepad gamepad) {
        InputManager.lastKey = gamepad.left_stick_y + "";
    }
}