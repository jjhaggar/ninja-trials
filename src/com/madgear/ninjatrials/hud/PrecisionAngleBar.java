/*
 * Ninja Trials is an old school style Android Game developed for OUYA & using
 * AndEngine. It features several minigames with simple gameplay.
 * Copyright 2013 Mad Gear Games <madgeargames@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.madgear.ninjatrials.hud;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import com.madgear.ninjatrials.managers.ResourceManager;




/**
 * Energy bar with a cursor that moves from left to right, and in reverse direction when
 * the cursor reach the right margin.

 * Cursor moves from a minimum value to the maximum value (from 0 to 200), taking all the range
 * of values. The cursor makes a whole cycle in a time "timeRound".
 *
 * The cursor speed is calculated based on timeRound.
 *
 * @author Madgear Games
 */
@SuppressWarnings({ "static-access" })
public class PrecisionAngleBar extends Entity {
    private final float cursorMin = 0f;
    private final float cursorMax = 200f;
    private float cursorValue = 0f;
    private float speed;
    private int direction = 1;
    private float curXInit;
    private float curYInit;
    private int semicycle = 0;
    private Sprite angleBar, cursor;
	private Rectangle rectangleCursor;
	private boolean tooHigh = false;
	private float jump = 1; 

    /**
     * Contruct a PowerBarCursor object.
     *
     * @param posX Position axis X.
     * @param posY Position axis Y.
     * @param timeRound Time in seconds the cursor takes in complete a whole cycle. It's used to
     * calculate the cursor speed.
     */
    public PrecisionAngleBar(float posX, float posY, float timeRound) {
        curXInit = posX + 300;
        curYInit = posY - 100;
        semicycle = 0;
        angleBar = new Sprite(posX, posY,
                ResourceManager.getInstance().hudAngleBarCursor,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        cursor = new Sprite(curXInit, curYInit,
                ResourceManager.getInstance().hudCursor,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(angleBar);
        attachChild(cursor);
        speed = 2.5f * (cursorMax - cursorMin) / timeRound;
        setIgnoreUpdate(true);
    }

    /**
     * Sets the cursor value.
     * @param value The new cursor value.
     */
    public void setCursorValue(float value) {
        if (value >= cursorMin && value <= cursorMax)
            cursorValue = value;
    }
    
    public void setCursorValueToBeginning(){
    	cursorValue =  0;
    	direction = 1;
    	jump = 1;
    }
    
    public void ActivateTooHigh(){
    	tooHigh = true;
    }

    /**
     * Continue moving the cursor.
     */
    public void start() {
        setIgnoreUpdate(false);
    }

    /**
     * Stops moving the cursor.
     */
    public void stop() {
        setIgnoreUpdate(true);
    }

    /**
     * Gets the power value.
     * @return An integer value from -100 (left) to 100 (right). 0 is the center value.
     */
    public float[] getPowerValue() {
    	if (cursorValue <= 0 )
    		cursorValue = cursorMax - 5f;
    	if (cursorValue >= cursorMax - 5f )
    		cursorValue = cursorMax - 5f;
    	
    	float[] result = new float[] {cursorValue, (float) Math.sqrt(Math.pow(cursorMax, 2) - Math.pow(cursorValue, 2)), jump};
    	if (Double.isNaN(result[0]) || Double.isNaN(result[1]))
    		result = new float[] { cursorMax - 5f, (float) Math.sqrt(Math.pow(cursorMax, 2) - Math.pow(cursorMax - 5f, 2)), jump};
    	
    	return result;
        
    }
    
    /**
     * Gets the number of semi-cycles of the bar. A semi-cycle begins each time the cursor reach
     * the bar edge and changes his direction. The first semi-cycle is 0;
     * @return
     */
    public int getSemicycle() {
        return semicycle;
    }
    
    public float getJumpValue() {
    	return jump;
    }
    
    public void setJumpValue(float jumpInput) {
    	jump = jumpInput;
    }

    /**
     * Updates the value of the cursor position and controls when the cursor reach the left or
     * right margin, changing the cursor direction.
     * Cursor doesn't move if the time passed is higher than 0.2 s from the last update.
     */
    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
    	//finish the rectangle movement.
    	//rectangleCursor = new Rectangle(0, 0, 70, 30,  
    	//		ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    	//rectangleCursor.setRotationCenter(0.0f, 0.0f);
    	//rectangleCursor.setRotation(10);
    	
        // controlamos que no se vaya el cursor por el retraso:
        if (pSecondsElapsed < 0.2)
            cursorValue += pSecondsElapsed * speed * direction;
        
        if (cursorValue > cursorMax)
        	cursorValue = cursorMax;
        
        
        if (cursorValue >= cursorMax) {
            direction = -1;
            jump = -1;
            semicycle++;
            cursorValue = 0;
            if (tooHigh)
            	stop();
        }
        if (cursorValue <= (cursorMax / 2.0f)) {
            direction = 1;
            semicycle++;
        }
        
      //the position of Y it's done relative to X to form a semicircle
        float posX = curXInit - cursorValue;
        float posY = curYInit + 200 - ((float) cursorMax - cursorValue);
        cursor.setX(posX);
        cursor.setY(posY);
        
        super.onManagedUpdate(pSecondsElapsed);
    }
}
