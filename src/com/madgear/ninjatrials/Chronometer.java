package com.madgear.ninjatrials;

import java.text.DecimalFormat;

import org.andengine.entity.Entity;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;


/**
 * Choronometer with 4 digits with format 00:00.
 * Each digit has his own position, this way the digits doesn't move if the lenght of the
 * string changes.
 * 
 * @author Madgear Games
 */
public class Chronometer extends Entity {
    private float timeValue;
    private float initialValue;
    private float finalValue;
    private float direction;
    private float posX, posY;
    private boolean timeOut;
    private Text digit1, digit2, digit3, digit4, colon;
    private String timeString;
    private DecimalFormat formatter = new java.text.DecimalFormat("00.00");


    /**
     * Construct a chronometer.
     * @param posX Position axis X.
     * @param posY Position axis Y.
     * @param initialValue Initial time value.
     * @param finalValue Final time value.
     */
    public Chronometer(float posX, float posY, int initialValue, int finalValue) {
        setIgnoreUpdate(true);
        this.initialValue = (float) initialValue;
        this.finalValue = (float) finalValue;
        this.timeValue = this.initialValue;
        this.posX = posX;
        this.posY = posY;
        this.timeOut = false;
        digit1 = new Text(posX - 100, posY,
                ResourceManager.getInstance().fontMedium, "0",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        digit2 = new Text(posX - 50, posY,
                ResourceManager.getInstance().fontMedium, "0",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        digit3 = new Text(posX + 50, posY,
                ResourceManager.getInstance().fontMedium, "0",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        digit4 = new Text(posX + 100, posY,
                ResourceManager.getInstance().fontMedium, "0",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        colon = new Text(posX, posY,
                ResourceManager.getInstance().fontMedium, ":",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(digit1);
        attachChild(digit2);
        attachChild(digit3);
        attachChild(digit4);
        attachChild(colon);
        if (initialValue < finalValue)
            direction = 1;
        else
            direction = -1;
        draw();
    }

    /**
     * @return The current time of the chronometer.
     */
    public float getTimeValue() {
        return timeValue;
    }

    /**
     * Sets the time value of the chronometer to value.
     * @param value The new time value.
     */
    public void setTimeValue(float value) {
        if (value >= initialValue && value <= finalValue)
            timeValue = value;
    }

    /**
     * Continue counting time.
     */
    public void start() {
        setIgnoreUpdate(false);
    }

    /**
     * Stop counting time.
     */
    public void stop() {
        setIgnoreUpdate(true);
    }

    /**
     *
     * @return True if the Chronometer has reached his final value;
     */
    public boolean isTimeOut() {
        return timeOut;
    }

    /**
     * Draw the chronometer in the screen with the current time value.
     */
    private void draw() {
        timeString = formatter.format(timeValue);
        digit1.setText(timeString.substring(0, 1));
        digit2.setText(timeString.substring(1, 2));
        digit3.setText(timeString.substring(3, 4));
        digit4.setText(timeString.substring(4));
    }

    /**
     * Updates the value of current time and draws the chronometer. If the time exceds the final
     * value then stop the chronometer.
     */
    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
        if(direction == 1) {
            timeValue += pSecondsElapsed;
            if(timeValue >= finalValue) {
                timeValue = finalValue;
                stop();
                timeOut = true;
            }
        }
        else {
            timeValue -= pSecondsElapsed;
            if(timeValue <= finalValue) {
                timeValue = finalValue;
                stop();
                timeOut = true;
            }
        }
        draw();
        super.onManagedUpdate(pSecondsElapsed);
    }
}
