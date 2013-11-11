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

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * This is a testing class.
 * @author Madgear Games
 *
 */
public class CreditsScene extends GameScene {
    private static final float PUSH_DELAY_TIME = 2f;
    private boolean pressButtonEnabled = false;
    private TimerHandler timerHandler;
    
    public CreditsScene() {
        this(0f);  // loading screen disabled.
    }
    
    public CreditsScene(float min) {
        super(min);  // loading screen enabled.
        timerHandler = new TimerHandler(PUSH_DELAY_TIME, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                pressButtonEnabled = true;
            } 
        });
        registerUpdateHandler(timerHandler);
    }
    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene(); // Provisional, sera una clase externa
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        // Añadimos algo de texto:
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.3f,
                ResourceManager.getInstance().fontBig, "Loading...",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        loadingScene.attachChild(loadingText);
        return loadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {}

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0.5f, 0.3f, 0.2f);
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.5f,
                ResourceManager.getInstance().fontMedium,
                "CreditsScene" +
                "Press O for action\n" +
                "You must wait for " + PUSH_DELAY_TIME + " seconds.\n",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(loadingText);

    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {}
    
    /**
     * If we press the O button go to the next scene or testing scene.
     */
    @Override
    public void onPressButtonO() {
        if(pressButtonEnabled)
            if(GameManager.DEBUG_MODE)
                SceneManager.getInstance().showScene(new TestingScene());
            else
                SceneManager.getInstance().showScene(new SplashIntroScene());
    }
}