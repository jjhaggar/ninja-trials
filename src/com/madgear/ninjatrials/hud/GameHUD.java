package com.madgear.ninjatrials.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.ResourceManager;


/**
 * This class controls the display of the game HUD of the game.
 *
 * Can write text, includes a chronometer and various types of power bars.
 * @author Madgear Games
 *
 */
public class GameHUD extends HUD {
    private float width = ResourceManager.getInstance().cameraWidth;
    private float height = ResourceManager.getInstance().cameraHeight;
    private Text mTextComboMessage = null;
    public final static float DEF_FADE_IN_TIME = 0.25f;
    public final static float DEF_FADE_OUT_TIME = 0.25f;
    public final static float DEF_MESS_STAND_TIME = 2.0f;
    public final static float DEF_IN_DELAY_TIME = 0.01f;

    /**
     * GameHUD constructor
     */
    public GameHUD() {
        mTextComboMessage = new Text(width/2, height/2,
                ResourceManager.getInstance().fontMedium, "",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        mTextComboMessage.setAlpha(0);
        attachChild(mTextComboMessage);
    }
    
    /**
     * Writes a message in the screen. By default the text stands for 1 second, and fade in and
     * fade out in 0.25 seconds, and is in the screen center.
     * The text grows from 90% to 100% size.
     * @param message The text we want to display.
     */
    public void showMessage(String message) {
        showMessage(message, DEF_FADE_IN_TIME, DEF_MESS_STAND_TIME, DEF_FADE_OUT_TIME,
                DEF_IN_DELAY_TIME);
    }

    /**
     * Display a message in the screen. By default the text is in the screen center.
     * The text grows from 90% to 100% size.
     * @param message The text we want to display.
     * @param msgEnterTime Time for the fade in text.
     * @param msgDisplayTime Time for the text to stand in the screen.
     * @param msgExitTime Time for the fade out text.
     * @param msgInDelayTime The time the message waits to be displayed.
     */
    public void showMessage(String message, float msgEnterTime, float msgDisplayTime,
            float msgExitTime, float msgInDelayTime) {
        showMessage(message, msgEnterTime, msgDisplayTime, msgExitTime, width / 2, height / 2,
                msgInDelayTime);
    }

    /**
     * Display a message in the screen. The text grows from 90% to 100% size.
     * @param message The text we want to display.
     * @param msgEnterTime Time for the fade in text.
     * @param msgDisplayTime Time for the text to stand in the screen.
     * @param msgExitTime Time for the fade out text.
     * @param xPos The X position.
     * @param yPos The Y position.
     * @param msgInDelayTime The time the message waits to be displayed.
     */
    public void showMessage(String message, float msgEnterTime, float msgDisplayTime,
            float msgExitTime, float xPos, float yPos, float msgInDelayTime) {
        final Text mTextMessage = new Text(xPos, yPos,
                ResourceManager.getInstance().fontMedium, message,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        
        if(msgDisplayTime <= 0) msgDisplayTime = 0.1f;
        if(msgDisplayTime <= 0) msgDisplayTime = 0.1f;
        if(msgExitTime <= 0) msgExitTime = 0.1f;
        if(msgInDelayTime <= 0) msgInDelayTime = 0.1f;
        
        attachChild(mTextMessage);
        mTextMessage.setAlpha(0);
        mTextMessage.setScale(0.9f);        
        mTextMessage.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(msgInDelayTime),
                new ParallelEntityModifier(
                        new ScaleModifier(msgEnterTime, 0.9f, 1),
                        new FadeInModifier(msgEnterTime)),
                new DelayModifier(msgDisplayTime),
                new ParallelEntityModifier(
                        new ScaleModifier(msgExitTime, 1f, 1.2f),
                        new FadeOutModifier(msgExitTime))
                ));
    }

    /**
     * Display a message in the screen permanently in the screen center.
     * @param message The message to display.
     */
    public void showComboMessage(String message) {
        showComboMessage(message, width / 2, height / 2);
    }

    /**
     * Display a message in the screen permanently.
     * @param message The message to display.
     * @param xPos Position X.
     * @param yPos Position Y.
     */
    public void showComboMessage(String message, float xPos, float yPos) {
        mTextComboMessage.setPosition(xPos, yPos);
        mTextComboMessage.setText(message);
        mTextComboMessage.setAlpha(1);
    }

    /**
     * Changes the message of the combo message.
     * @param message The new message.
     */
    public void changeComboMessage(String message) {
        mTextComboMessage.setText(message);
    }

    /**
     * Hides the message of the combo message.
     */
    public void hideComboMessage() {
        mTextComboMessage.setAlpha(0);
    }
}
