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
     * Prueba de documentación.
     * @author MadGear Games
     */
    public GameScene(){
        super(1f);
        setOnSceneTouchListener(this);
    }

    /**
     * Check if the screen is touched.
     */
    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
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
