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


package com.madgear.ninjatrials;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import android.view.KeyEvent;

/*
 * Clased GameScene.
 */
public abstract class GameScene extends ManagedScene implements IUserInput, IOnSceneTouchListener {

    /**
     * GameScene constructor that disables the loading screen.
     * @author MadGear Games
     */
    public GameScene(){
        this(0f);
    }
    
    /**
     * GameScene constructor that enables the loading screen.
     * @param pLoadingScreenMinimumSecondsShown The minimum time the loading scrren is shown.
     */
    public GameScene(float pLoadingScreenMinimumSecondsShown){
        super(pLoadingScreenMinimumSecondsShown);
        setOnSceneTouchListener(this);
    }

    /**
     * Check if the screen is touched.
     * The left part of the screen controls the Y axis of the game pad.
     */
    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(pSceneTouchEvent.getX() < 600)
            if(pSceneTouchEvent.getY() > 540)
                onPressDpadUp();
            else
                onPressDpadDown();
        else
            onPressButtonO();
        return true;
    }

    /**
     * Check the key pressed and calls the apropiate method.
     * @param keyCode
     * @param event
     * @return true
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            onPressDpadRight();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            onPressDpadLeft();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            onPressButtonMenu();
            return true;
        }     
        return false;
    }   
    
    // Métodos que pueden ser sobreescritos por las subclases:
    public void onPressButtonO() {}
    public void onPressButtonU() {}
    public void onPressButtonY() {}
    public void onPressButtonA() {}
    public void onPressButtonMenu() {}
    public void onPressDpadUp() {}
    public void onPressDpadDown() {}
    public void onPressDpadLeft() {}
    public void onPressDpadRight() {}
}
