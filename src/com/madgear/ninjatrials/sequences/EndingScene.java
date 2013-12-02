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


package com.madgear.ninjatrials.sequences;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * This is the Ending class.
 * @author Madgear Games
 *
 */
public class EndingScene extends GameScene {
    private static final float PUSH_DELAY_TIME = 2f;
    private boolean pressButtonEnabled = false;
    private TimerHandler timerHandler;
    private Sequence sequenceEntity;
    
    public EndingScene() {
        this(0f);  // loading screen disabled.
    }
    
    public EndingScene(float min) {
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
        Scene loadingScene = new Scene(); // Provisional, it will be an external class
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        // Some text added:
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
    public void onLoadScene() {
        ResourceManager.getInstance().loadEndingResources();
    }

    @Override
    public void onShowScene() {
        // Choose the right sequence to show. Only 2 now, but there'll be 9 different ones later
        switch (GameManager.getSelectedCharacter()) {
        case GameManager.CHAR_SHO:
            sequenceEntity = new EndingSequenceShoEasy();
            break;
        case GameManager.CHAR_RYOKO:
            sequenceEntity = new EndingSequenceRyokoEasy();
            break;
        default:
            break;
        }
        this.attachChild((Entity)sequenceEntity);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadEndingResources();
    }

    protected void skip() {
        skip(GameManager.sequenceBehaviourAtPress);
    }

    protected void skip(int sequenceBehaviour) {
        if(pressButtonEnabled){
            switch (sequenceBehaviour) {
            case GameManager.SEQUENCE_CANNOT_SKIP:
                // Don't do anything. The sequence cannot be skipped.
                break;
            case GameManager.SEQUENCE_ASK_BEFORE_SKIP:
                // TODO Ask before skip. Show confirmation dialog.
                break;
            case GameManager.SEQUENCE_SKIP_DIRECTLY:
                // Skip the sequence without asking.
                if (sequenceEntity.isInLastSubSequence()) {
                    sequenceEntity.stopMusicAndSFX();
                    if(GameManager.DEBUG_MODE)
                        SceneManager.getInstance().showScene(new TestingScene());
                    else
                        SceneManager.getInstance().showScene(new CreditsScene());
                }
                else{
                    sequenceEntity.goToNextSubSequence();
                }
                break;
            default:
                break;
            }
        }
    }

    @Override
    public void onPressButtonMenu() {
        skip();
    }
    @Override
    public void onPressButtonO() {
        skip();
    }
    @Override
    public void onPressButtonU() {
        skip();
    }
    @Override
    public void onPressButtonY() {
        skip();
    }
    @Override
    public void onPressButtonA() {
        skip();
    }
}