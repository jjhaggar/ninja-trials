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

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.ResourceManager;


/**
 * This class controls the display of the game HUD of the game.
 * Can write only a text message at once. 
 * @author Madgear Games
 *
 */
public class GameHUD extends HUD {
    private Text textMessage = null;
    public final static float DEF_FADE_IN_TIME = 0.1f;
    public final static float DEF_FADE_OUT_TIME = 0.1f;
    public final static float DEF_MESS_STAND_TIME = 2.0f;
    public final static float DEF_IN_DELAY_TIME = 0f;
    private final static float SCALE_INIT = 0.9f;
    private final static float SCALE_FINAL = 1.2f;
    private final static int AUTOWRAP_SIZE = 10;
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private final static Font DEF_FONT = ResourceManager.getInstance().fontBig;

    /**
     * GameHUD constructor
     */
    public GameHUD() {}
    
    /**
     * Writes a message in the screen (centered).
     * @param message The text we want to display.
     */
    public void showMessage(String message) {
        showMessage(message, DEF_IN_DELAY_TIME, DEF_FADE_IN_TIME, DEF_MESS_STAND_TIME,
                DEF_FADE_OUT_TIME);
    }

    /**
     * Writes a message in the screen (centered).
     * @param message The text we want to display.
     * @param msgInDelayTime The time the message waits to be displayed.
     * @param msgDisplayTime Time for the text to stand in the screen (including fade in and
     * fade out time).
     */
    public void showMessage(String message, float msgInDelayTime, float msgDisplayTime) {
        showMessage(message, msgInDelayTime, DEF_FADE_IN_TIME, msgDisplayTime,
                DEF_FADE_OUT_TIME);
    }
    
    /**
     * Display a message in the screen (centered).
     * @param message The text we want to display.
     * @param msgInDelayTime The time the message waits to be displayed.
     * @param msgEnterTime Time for the fade in time.
     * @param msgDisplayTime Time for the text to stand in the screen (including fade in and
     * fade out time).
     * @param msgExitTime Time for the fade out time.
     */
    public void showMessage(String message, float msgInDelayTime ,float msgEnterTime,
            float msgDisplayTime, float msgExitTime) {
        
        showMessage(message, msgInDelayTime, msgEnterTime, msgDisplayTime, msgExitTime,
                WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Display a message in the screen.
     * @param message The text we want to display.
     * @param msgInDelayTime The time the message waits to be displayed.
     * @param msgEnterTime Time for the fade in text.
     * @param msgDisplayTime Time for the text to stand in the screen (including fade in and
     * fade out time).
     * @param msgExitTime Time for the fade out text.
     * @param xPos The X position.
     * @param yPos The Y position.
     */
    public void showMessage(String message, float msgInDelayTime, float msgEnterTime,
            float msgDisplayTime, float msgExitTime, float xPos, float yPos) {
        
        // if exists a previus text, destroy it.
        if(textMessage != null) textMessage.detachSelf();
        textMessage = new Text(xPos, yPos,
                DEF_FONT, message,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        
        if(msgInDelayTime <= 0) msgInDelayTime = DEF_IN_DELAY_TIME;
        if(msgEnterTime <= 0) msgEnterTime = DEF_FADE_IN_TIME;
        if(msgDisplayTime <= 0) msgDisplayTime = DEF_MESS_STAND_TIME;
        if(msgExitTime <= 0) msgExitTime = DEF_FADE_OUT_TIME;
        
        attachChild(textMessage);
        textMessage.setAlpha(0);
        textMessage.setScale(SCALE_INIT);        
        textMessage.registerEntityModifier(new SequenceEntityModifier(
                new DelayModifier(msgInDelayTime),
                new ParallelEntityModifier(
                        new ScaleModifier(msgEnterTime, SCALE_INIT, 1),
                        new FadeInModifier(msgEnterTime)),
                new DelayModifier(msgDisplayTime - msgEnterTime - msgExitTime),
                new ParallelEntityModifier(
                        new ScaleModifier(msgExitTime, 1f, SCALE_FINAL),
                        new FadeOutModifier(msgExitTime))
                ));
    }

    /**
     * Display a message permanently in the screen center.
     * @param message The message to display.
     */
    public void showComboMessage(String message) {
        showComboMessage(message, WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Display a message in the screen permanently.
     * @param message The message to display.
     * @param xPos Position X.
     * @param yPos Position Y.
     */
    public void showComboMessage(String message, float xPos, float yPos) {
        // if exists a previus text, destroy it.
        if(textMessage != null) textMessage.detachSelf();
        textMessage = new Text(xPos, yPos,
                DEF_FONT, message,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(textMessage);
    }

    /**
     * Hides the message of the combo message.
     */
    public void hideComboMessage() {
        textMessage.setAlpha(0);
    }
}
