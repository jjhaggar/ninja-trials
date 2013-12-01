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
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;

import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;

/**
 * This is the Second Intro Sequence class (choosing Sho and Easy).
 * @author Madgear Games
 *
 */
@SuppressWarnings("static-access")
public class Intro2SequenceShoEasy extends Entity implements Sequence{

    private final float SCREEN_WIDTH = ResourceManager.getInstance().cameraWidth;
    private final float SCREEN_HEIGHT = ResourceManager.getInstance().cameraHeight;

    private float timeStart = 0; // This will be useful only when there is a loading time
    private float timeDurationSubSeq1 = 10.0f;
    private float timeDurationSubSeq2 = 10.0f;
    private float timeSubSeq1Start = timeStart;
    private float timeSubSeq2Start = timeStart + timeDurationSubSeq1;
    private float timeSeqFinish = timeStart + timeDurationSubSeq1 + timeDurationSubSeq2;
    private int actualSequence = 0;
    private int lastSequence = 2;

    private TimerHandler timerSubSequence1;
    private TimerHandler timerSubSequence2;
    private TimerHandler timerSubSequencesFinish;

    private Sprite sprBG1;
    private Sprite sprBG2;
    private AnimatedSprite sprMaster;
    private AnimatedSprite sprShoBow;
    private Sprite sprShoStanding;
    private Sprite sprBalloonSpeak;
    private Sprite sprBalloonSpeakLoud;
    private Sprite sprBalloonThink;

    // Constructors
    public Intro2SequenceShoEasy() {
        this(0f);  // loading screen disabled.
    }

    public Intro2SequenceShoEasy(float min) {
        // Initial register of sub-sequence timers, and also show first sub-sequence.
        goToNextSubSequence();
    }

    // SubSequence 1: Master speaking to his disciple Sho
    private void subSequence1() {

        // Update actual sequence
        actualSequence = 1;

        // Calculate & Set animation times
        float aTShoSpeaking = timeDurationSubSeq1 * 0.75f;

        // Add Sprites and text

        sprBG1 = new Sprite(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().intro2CommonBg.getWidth(),
                ResourceManager.getInstance().intro2CommonBg.getHeight(),
                ResourceManager.getInstance().intro2CommonBg,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprBG1);

        sprMaster = new AnimatedSprite(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().intro2CommonMaster.getWidth(),
                ResourceManager.getInstance().intro2CommonMaster.getHeight(),
                ResourceManager.getInstance().intro2CommonMaster,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        sprMaster.animate(500, true);
        attachChild(sprMaster);

        sprBalloonSpeak = new Sprite(SCREEN_WIDTH * 0.20f, SCREEN_HEIGHT * 0.7f,
                ResourceManager.getInstance().intro2CommonMasterTextBalloon.getWidth(),
                ResourceManager.getInstance().intro2CommonMasterTextBalloon.getHeight(),
                ResourceManager.getInstance().intro2CommonMasterTextBalloon,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprBalloonSpeak);

        final Text txtMaster = new Text(
                SCREEN_WIDTH * 0.20f, SCREEN_HEIGHT * 0.7f,
                ResourceManager.getInstance().fontSmall,
                ResourceManager.getInstance().loadAndroidRes()
                .getString(R.string.intro2_1p_sho_03_master), // TODO Complete the dialogs
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(txtMaster);

        sprShoBow = new AnimatedSprite(SCREEN_WIDTH * 0.7f, SCREEN_HEIGHT * 0.25f,
                ResourceManager.getInstance().intro2CommonSho.getWidth(),
                ResourceManager.getInstance().intro2CommonSho.getHeight(),
                ResourceManager.getInstance().intro2CommonSho,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        sprShoBow.animate( (long)(aTShoSpeaking * 1000), false);
        sprShoBow.setFlippedHorizontal(true);
        attachChild(sprShoBow);

        sprBalloonSpeakLoud = new Sprite(SCREEN_WIDTH * 0.8f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().intro2CommonShoTextBalloon.getWidth(),
                ResourceManager.getInstance().intro2CommonShoTextBalloon.getHeight(),
                ResourceManager.getInstance().intro2CommonShoTextBalloon,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        sprBalloonSpeakLoud.setAlpha(0);
        attachChild(sprBalloonSpeakLoud);

        final Text txtSho = new Text(
                SCREEN_WIDTH * 0.8f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().fontMedium,
                ResourceManager.getInstance().loadAndroidRes()
                    .getString(R.string.intro2_1p_sho_04_sho),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        txtSho.setAlpha(0);
        this.attachChild(txtSho);

        // Create & add Modifiers to Sprites and Text
        DelayModifier modDelBalloon = new DelayModifier(aTShoSpeaking,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier,
                            IEntity pItem) {
                        // Do nothing
                    }
                    @Override
                    public void onModifierFinished(
                            IModifier<IEntity> pModifier, IEntity pItem) {
                        sprBalloonSpeakLoud.setAlpha(1f);
                        txtSho.setAlpha(1f);
                        sprMaster.stopAnimation(0);
                        sprBalloonSpeak.setAlpha(0f);
                        txtMaster.setAlpha(0f);
                    }
        });
        sprBalloonSpeakLoud.registerEntityModifier(modDelBalloon);
        
        // Play & Manage music
        SFXManager.playMusic(ResourceManager.getInstance().intro2);
    }

    // SubSequence 2: Sho is thinking
    private void subSequence2() {

        // Update actual sequence
        actualSequence = 2; 

        // Add Sprites and text

        sprBG2 = new Sprite(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().intro2ShoBg.getWidth(),
                ResourceManager.getInstance().intro2ShoBg.getHeight(),
                ResourceManager.getInstance().intro2ShoBg,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprBG2);

        sprShoStanding = new Sprite(SCREEN_WIDTH * 0.25f, SCREEN_HEIGHT * 0.45f,
                ResourceManager.getInstance().intro2Sho.getWidth(),
                ResourceManager.getInstance().intro2Sho.getHeight(),
                ResourceManager.getInstance().intro2Sho,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(sprShoStanding);

        sprBalloonThink= new Sprite(SCREEN_WIDTH * 0.70f, SCREEN_HEIGHT * 0.75f,
                ResourceManager.getInstance().intro2ShoBalloonText.getWidth(),
                ResourceManager.getInstance().intro2ShoBalloonText.getHeight(),
                ResourceManager.getInstance().intro2ShoBalloonText,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        sprBalloonThink.setFlippedHorizontal(true);
        attachChild(sprBalloonThink);

        final Text txtSho = new Text(
                SCREEN_WIDTH * 0.75f, SCREEN_HEIGHT * 0.75f,
                ResourceManager.getInstance().fontBig,
                ResourceManager.getInstance().loadAndroidRes()
                .getString(R.string.intro2_1p_sho_05_sho),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(txtSho);

        // Create & add Modifiers to Sprites and text

        // Play & Manage music

    }


    public void stopMusicAndSFX(){
        SFXManager.stopMusic(ResourceManager.getInstance().intro2);
    }

    @Override
    public void finishSequence() {

        // Update actual sequence
        actualSequence = -1;

        // Stop music and sounds
        stopMusicAndSFX();

        //Call skip() on father entity
        IEntity parentEntity = this.getParent();
        if (parentEntity instanceof Intro2Scene){
            ((Intro2Scene) parentEntity).skip(GameManager.SEQUENCE_SKIP_DIRECTLY);
        }
    }

    @Override
    public void goToNextSubSequence() {
        // Update times and unregister timers
        switch (actualSequence) {
        case 1:
            this.unregisterUpdateHandler(timerSubSequence2);
            this.unregisterUpdateHandler(timerSubSequencesFinish);
            timeSubSeq2Start = 0;
            timeSeqFinish = timeDurationSubSeq2;
            break;
        case 2:
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
        if (timeSubSeq2Start > 0f){ // Sub-sequence 2: Sho thinking
            timerSubSequence2 = new TimerHandler(timeSubSeq2Start, false, new ITimerCallback(){
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    subSequence2();
                }
            });
            this.registerUpdateHandler(timerSubSequence2);
        }
        else {
            subSequence2();
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