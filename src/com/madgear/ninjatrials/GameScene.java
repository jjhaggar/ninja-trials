package com.madgear.ninjatrials;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;


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

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        onPressButtonO();
        return true;
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
