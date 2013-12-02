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
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;

/**
 * This is the Ending Sequence class (choosing Ryoko and Easy) - Actually it's just a placeholder -
 * @author Madgear Games
 *
 */
@SuppressWarnings("static-access")
public class EndingSequenceRyokoEasy extends Entity implements Sequence{

    private final float SCREEN_WIDTH = ResourceManager.getInstance().cameraWidth;
    private final float SCREEN_HEIGHT = ResourceManager.getInstance().cameraHeight;

    private float timeStart = 0; // This will be useful only when there is a loading time
    private float timeDurationSubSeq1 = 21.0f; // Ending music lasts 21 seconds
    private float timeSubSeq1Start = timeStart;
    private float timeSeqFinish = timeStart + timeDurationSubSeq1;
    private int actualSequence = 0;
    private int lastSequence = 1;

    private TimerHandler timerSubSequence1;
    private TimerHandler timerSubSequencesFinish;

    private Sprite sprBG;
    private Sprite sprRyoko;

    // Constructors
    public EndingSequenceRyokoEasy() {
        this(0f);  // loading screen disabled.
    }

    public EndingSequenceRyokoEasy(float min) {
        // Initial register of sub-sequence timers, and also show first sub-sequence.
        goToNextSubSequence();
    }

    // SubSequence 1: Placeholder Ending
    private void subSequence1() {

        // Update actual sequence
        actualSequence = 1;

        // Calculate & Set animation times

        // Add Sprites and text

        sprBG = new Sprite(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().endingRyokoEasyBg.getWidth(),
                ResourceManager.getInstance().endingRyokoEasyBg.getHeight(),
                ResourceManager.getInstance().endingRyokoEasyBg,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprBG);

        sprRyoko = new Sprite(SCREEN_WIDTH * 0.75f, SCREEN_HEIGHT * 0.45f,
                ResourceManager.getInstance().endingRyokoEasy.getWidth(),
                ResourceManager.getInstance().endingRyokoEasy.getHeight(),
                ResourceManager.getInstance().endingRyokoEasy,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprRyoko);

        final Text txtEnding = new Text(
                SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.15f,
                ResourceManager.getInstance().fontSmall,
                ResourceManager.getInstance().loadAndroidRes()
                .getString(R.string.ending_ryoko_placeholder),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(txtEnding);

        // Create & add Modifiers to Sprites and Text

        // Play & Manage music
        SFXManager.playMusic(ResourceManager.getInstance().ending);
    }

    public void stopMusicAndSFX(){
        SFXManager.stopMusic(ResourceManager.getInstance().ending);
    }

    @Override
    public void finishSequence() {
    	
        // Update actual sequence
        actualSequence = -1;

        // Stop music and sounds
        stopMusicAndSFX();

        //Call skip() on father entity
        IEntity parentEntity = this.getParent();
        if (parentEntity instanceof EndingScene){
            ((EndingScene) parentEntity).skip(GameManager.SEQUENCE_SKIP_DIRECTLY);
        }
    }

    @Override
    public void goToNextSubSequence() {
        // Update times and unregister timers
        switch (actualSequence) {
        case 1:
            this.unregisterUpdateHandler(timerSubSequencesFinish);
            timeSeqFinish = 0;
            break;
        default:
            break;
        }

        // Register timers and/or show actual sub-sequence
        if (actualSequence == 0){
            if (timeSubSeq1Start > 0f){ // Sub-sequence 1: Master speaking
                timerSubSequence1 = new TimerHandler(timeSubSeq1Start, false, new ITimerCallback(){
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        subSequence1();
                    }
                });
                this.registerUpdateHandler(timerSubSequence1);
            }
            else {
                subSequence1();
            }
        }
        if (timeSeqFinish > 0f){ // End of the Sequence 
            timerSubSequencesFinish = new TimerHandler(timeSeqFinish, false, new ITimerCallback(){
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    if (actualSequence != -1)
                        finishSequence();
                }
            });
            this.registerUpdateHandler(timerSubSequencesFinish);
        }
        else {
            if (actualSequence != -1)
                finishSequence();
        }
    }

    @Override
    public boolean isInLastSubSequence() {
        if (actualSequence == lastSequence || actualSequence == -1)
            return true;
        else
            return false;
    }
}