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

import tv.ouya.console.api.OuyaController;

import com.madgear.ninjatrials.managers.GameManager;

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
     * @param pLoadingScreenMinimumSecondsShown The minimum time the loading screen is shown.
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
        else if (pSceneTouchEvent.isActionDown())
            onPressButtonO();
        else if (pSceneTouchEvent.isActionUp())
            onReleaseButtonO();
        return true;
    }

    /**
     * Check the pressed key and calls the appropriate method.
     * @param keyCode
     * @param event
     * @return true
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // OUYA Controller
        if (NinjaTrials.OUYA_CONTROL){
            boolean handled = OuyaController.onKeyDown(keyCode, event);
            // findOrCreatePlayer(event.getDeviceId());
            // System.out.println("Mando usado = " + event.getDeviceId() );
            switch (keyCode) {
            case OuyaController.BUTTON_O:
                onPressButtonO();
                break;
            case OuyaController.BUTTON_U:
                onPressButtonU();
                break;
            case OuyaController.BUTTON_Y:
                onPressButtonY();
                break;
            case OuyaController.BUTTON_A:
                onPressButtonA();
                break;
            case OuyaController.BUTTON_L1:
                
                break;
            case OuyaController.BUTTON_L3:
                
                break;
            case OuyaController.BUTTON_R1:
                
                break;
            case OuyaController.BUTTON_R3:
                
                break;
            case OuyaController.BUTTON_DPAD_UP:
                onPressDpadUp();
                break;
            case OuyaController.BUTTON_DPAD_DOWN:
                onPressDpadDown();
                break;
            case OuyaController.BUTTON_DPAD_LEFT:
                onPressDpadLeft();
                break;
            case OuyaController.BUTTON_DPAD_RIGHT:
                onPressDpadRight();
                break;
            case OuyaController.BUTTON_MENU:
                onPressButtonMenu();
                break;
            default:
                break;
            }
            return handled || onKeyDown(keyCode, event);
        }
        else{
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
            /*
             * <KEYBOARD SUPPORT>
             * Default keys: W A S D, directional pads and delete for menu.
             * Custom keys option might be included in the future.
             */
            if ((keyCode == KeyEvent.KEYCODE_DEL)){
                onPressButtonMenu();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_S)){
                onPressButtonO();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_A)){
                onPressButtonU();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_W)){
                onPressButtonY();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_D)){
                onPressButtonA();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_UP)){
                onPressDpadUp();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_DOWN)){
                onPressDpadDown();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)){
                onPressDpadRight();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_LEFT)){
                onPressDpadLeft();
                return true;
            }
            /*
             * </KEYBOARD SUPPORT>
             */
        }
        return false;
    }

    /**
     * Check the released key and calls the appropriate method.
     * @param keyCode
     * @param event
     * @return true
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (NinjaTrials.OUYA_CONTROL){
            boolean handled = OuyaController.onKeyUp(keyCode, event);
            switch (keyCode) {
            case OuyaController.BUTTON_O:
                onReleaseButtonO();
                break;
            case OuyaController.BUTTON_U:
                onReleaseButtonU();
                break;
            case OuyaController.BUTTON_Y:
                onReleaseButtonY();
                break;
            case OuyaController.BUTTON_A:
                onReleaseButtonA();
                break;
            case OuyaController.BUTTON_L1:
                
                break;
            case OuyaController.BUTTON_L3:
                
                break;
            case OuyaController.BUTTON_R1:
                
                break;
            case OuyaController.BUTTON_R3:
                
                break;
            case OuyaController.BUTTON_DPAD_UP:
                onReleaseDpadUp();
                break;
            case OuyaController.BUTTON_DPAD_DOWN:
                onReleaseDpadDown();
                break;
            case OuyaController.BUTTON_DPAD_LEFT:
                onReleaseDpadLeft();
                break;
            case OuyaController.BUTTON_DPAD_RIGHT:
                onReleaseDpadRight();
                break;
            case OuyaController.BUTTON_MENU:
                onReleaseButtonMenu();
                break;
            default:
                break;
            }
            return handled || onKeyDown(keyCode, event);
        }
        else{
            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
                onReleaseDpadRight();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
                onReleaseDpadLeft();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_BACK)){
                onReleaseButtonMenu();
                return true;
            }
            /*
             * <KEYBOARD SUPPORT>
             * Default keys: W A S D, directional pads and delete for menu.
             * Custom keys option might be included in the future.
             */
            if ((keyCode == KeyEvent.KEYCODE_DEL)){
                onReleaseButtonMenu();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_S)){
                onReleaseButtonO();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_A)){
                onReleaseButtonU();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_W)){
                onReleaseButtonY();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_D)){
                onReleaseButtonA();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_UP)){
                onReleaseDpadUp();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_DOWN)){
                onReleaseDpadDown();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)){
                onReleaseDpadRight();
                return true;
            }
            if ((keyCode == KeyEvent.KEYCODE_DPAD_LEFT)){
                onReleaseDpadLeft();
                return true;
            }
            /*
             * </KEYBOARD SUPPORT>
             */
        }
        return false;
    }

    // Methods that Subclasses should overwrite:
    public void onPressButtonO() {}
    public void onReleaseButtonO() {}
    public void onPressButtonU() {}
    public void onReleaseButtonU() {}
    public void onPressButtonY() {}
    public void onReleaseButtonY() {}
    public void onPressButtonA() {}
    public void onReleaseButtonA() {}
    public void onPressButtonMenu() {}
    public void onReleaseButtonMenu() {}
    public void onPressDpadUp() {}
    public void onReleaseDpadUp() {}
    public void onPressDpadDown() {}
    public void onReleaseDpadDown() {}
    public void onPressDpadLeft() {}
    public void onReleaseDpadLeft() {}
    public void onPressDpadRight() {}
    public void onReleaseDpadRight() {}
}
