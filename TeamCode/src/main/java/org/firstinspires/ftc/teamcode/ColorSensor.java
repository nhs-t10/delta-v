/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSensor {

  /** The colorSensor field will contain a reference to our color sensor hardware object */
  NormalizedColorSensor colorSensor;
  HardwareMap hardwareMap;
  boolean weShouldRead, weveInitiated;
  int colorReturned;

  public ColorSensor(HardwareMap _hardwareMap) {
      this.hardwareMap = _hardwareMap; //since we don't get the hardwaremap by default-- this isn't an OpMode-- we have to set it manually

      this.weveInitiated = true; //We have initiated the code

      this.colorReturned = 0;

      this.weShouldRead = false;

      this.colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color"); // set the colorSensor to the actual hardware color sensor

     // this.runSample(); // actually execute the sampling code; start up the loop
  }

  //Switch the sample loop on/off
  public void switchSampling(boolean start_or_dont) {
          //save a boolean for the loop's state before changes
          boolean weShouldRead_old = this.weShouldRead;
          this.weShouldRead = start_or_dont;
          //if it's switching on from being previously off, start the loop again
  }

  //return our color integer
  public int getColorInt () {
      return this.colorReturned;
    }

  public String getHexCode() {

      return Color.red(this.colorReturned) + " | "+ Color.green(this.colorReturned) + " | " + Color.blue(this.colorReturned) + " | " +Color.alpha(this.colorReturned);
  }
  public boolean getReadState() {

      return this.weShouldRead;
  }

//Test if we're seeing gold
public boolean isGold() {
      //if the green value is between 0x53 (hexidecimal 53) and 0x64 (hexidecimal 64), it's gold. Otherwise, it's false.
    if ( Color.red(this.colorReturned) >= 112) {
        return true;
        //Since the condition is commented out, this will always be passed over
    } else {
        return false;
    }
    
    }

  //This won't be ran by any code outside of this class, so we can make it private.

  //Why won't it be ran? Simple! See, by making this method private, we keep the
  //public methods (and the wiki) nice and concise. Also, it doesn't return anything,
  //which isn't the best for an informational class.
  public void runSample() {

    // values is a reference to the hsvValues array.
    float[] hsvValues = new float[3];

    // If possible, turn the light on in the beginning (it might already be on anyway,
    // we just make sure it is if we can).
    if (this.colorSensor instanceof SwitchableLight) {
      ((SwitchableLight)this.colorSensor).enableLight(true); 
    }

    // Loop until we are asked to stop
        // Read the sensor
        NormalizedRGBA colors = this.colorSensor.getNormalizedColors();

        //Convert the color to HSV
        Color.colorToHSV(colors.toColor(), hsvValues);

        //normalize the colors-- make it so brightness won't affect our readout (much)
        float max = Math.max(Math.max(colors.red, colors.green), Math.max(colors.blue, colors.alpha));
        colors.red /= max;
        colors.green /= max;
        colors.blue /= max;


        //set the colorReturned variable so it can be used by the other methods
        this.colorReturned = colors.toColor();
  }
}