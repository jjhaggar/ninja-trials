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
