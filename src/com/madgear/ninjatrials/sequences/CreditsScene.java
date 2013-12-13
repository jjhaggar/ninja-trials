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
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.RecordsScene;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * This is the Credits Scene class.
 * @author Madgear Games
 *
 */
@SuppressWarnings("static-access")
public class CreditsScene extends GameScene {
    private final float SCREEN_WIDTH = ResourceManager.getInstance().cameraWidth;
    private final float SCREEN_HEIGHT = ResourceManager.getInstance().cameraHeight;
    private int CATEGORY_PROGRAMMING = 0;
    private int CATEGORY_READING = 1;
    private int CATEGORY_SINGING = 2;
    private int CATEGORY_MUSIC = 3;
    private int CATEGORY_RUNNING = 4;
    private int CATEGORY_OCTOPUS = 5;
    private int CATEGORY_GRAPHICS1 = 6;
    private int CATEGORY_PIXELART = 7;
    private int CATEGORY_GRAPHICS2 = 8;
    private VertexBufferObjectManager vertBuffObjMan =
            ResourceManager.getInstance().engine.getVertexBufferObjectManager();

    private float timeStart = 0;
    private float timeDurationProgramming = 10f;
    private float timeDurationMusic = 10f;
    private float timeDurationGraphics = 10f;
    private float timeDurationThanks01 = 10f;
    private float timeDurationThanks02 = 10f;
    private float tDT = 0.5f; // Time: Duration of the Transitions
    private float timeProgrammingStart = timeStart;
    private float timeMusicStart = timeProgrammingStart + timeDurationProgramming;
    private float timeGraphicsStart = timeMusicStart + timeDurationMusic;
    private float timeThanks01Start = timeGraphicsStart + timeDurationGraphics;
    private float timeThanks02Start = timeThanks01Start + timeDurationThanks01;
    private float timeFinish = timeThanks02Start + timeDurationThanks02;

    private TimerHandler timerProgramming;
    private TimerHandler timerMusic;
    private TimerHandler timerGraphics;
    private TimerHandler timerThanks01;
    private TimerHandler timerThanks02;
    private TimerHandler timerFinish;

    private Sprite sprBG;
    private TiledSprite sprCategories;
    private Sprite sprLogoEverGreen;
    private Sprite sprLogoAndEngine;

    private String maxStringWidthFontXBig = "12345678901234567890";
    private String maxStringWidthFontBig = "123456789012345678901234567890";
    private String maxStringWidthFontMedium = "1234567890123456789012345678901234567890";
    private String maxStringWidthFontSmall =
            "123456789012345678901234567890123456789012345678901234567890";

    private Text txtFontXBig;
    private Text txtFontBig01;
    private Text txtFontBig02;
    private Text txtFontBig03;
    private Text txtFontMedium01;
    private Text txtFontMedium02;
    private Text txtFontMedium03;
    private Text txtFontMedium04;
    private Text txtFontMedium05;
    private Text txtFontMedium06;
    private Text txtFontSmall01;
    private Text txtFontSmall02;
    private Text txtFontSmall03;
    private Text txtFontSmall04;
    private Text txtFontSmall05;
    private Text txtFontSmall06;

    public CreditsScene() {
        this(0f);  // loading screen disabled.
    }
    
    public CreditsScene(float min) {
        super(min);  // loading screen enabled.
        
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene(); // Provisional, it will be an external class
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        // Add some text:
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.3f,
                ResourceManager.getInstance().fontBig, "Loading...",
                new TextOptions(HorizontalAlign.CENTER),
                vertBuffObjMan);
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
        // Load and attach all sprites and text (with alpha = 0)
        addSpritesAndText();

        // "Programming Credits" sub sequence
        if (timeStart > 0f){
            timerProgramming = new TimerHandler(timeStart, false, new ITimerCallback(){
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    subSequenceProgramming();
                }
            });
            this.registerUpdateHandler(timerProgramming);
        }
        else {
            subSequenceProgramming();
        }

        // "Music Credits" sub sequence
        timerMusic = new TimerHandler(timeMusicStart, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                subSequenceMusic();
            }
        });
        this.registerUpdateHandler(timerMusic);

        // "Graphics Credits" sub sequence
        timerGraphics = new TimerHandler(timeGraphicsStart, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                subSequenceGraphics();
            }
        });
        this.registerUpdateHandler(timerGraphics);

        // "Special Thanks Credits 1" sub sequence
        timerThanks01 = new TimerHandler(timeThanks01Start, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                subSequenceThanks01();
            }
        });
        this.registerUpdateHandler(timerThanks01);

        // "Special Thanks Credits 2" sub sequence
        timerThanks02 = new TimerHandler(timeThanks02Start, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                subSequenceThanks02();
            }
        });
        this.registerUpdateHandler(timerThanks02);

        // Finish the sequence
        timerFinish = new TimerHandler(timeFinish, false, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                finishSequence();
            }
        });
        this.registerUpdateHandler(timerFinish);
    }

    private void addSpritesAndText(){
        sprBG = new Sprite (SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().endingCreditsBackground,
                vertBuffObjMan);
        attachChild(sprBG);
        sprLogoAndEngine = new Sprite(SCREEN_WIDTH * 0.2f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().endingCreditsLogoAndengine,
                vertBuffObjMan);
        sprLogoAndEngine.setAlpha(0);
        attachChild(sprLogoAndEngine);
        sprLogoEverGreen = new Sprite(SCREEN_WIDTH * 0.7f, SCREEN_HEIGHT * 0.5f,
                ResourceManager.getInstance().endingCreditsLogoEstudioevergreen,
                vertBuffObjMan);
        sprLogoEverGreen.setAlpha(0);
        attachChild(sprLogoEverGreen);
        sprCategories = new TiledSprite(SCREEN_WIDTH * 0.3f, SCREEN_HEIGHT * 0.7f,
                ResourceManager.getInstance().endingCreditsCategories,
                vertBuffObjMan);
        sprCategories.setCurrentTileIndex(CATEGORY_PROGRAMMING);
        sprCategories.setAlpha(0);
        attachChild(sprCategories);
        txtFontXBig = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.8f,
                ResourceManager.getInstance().fontXBig, maxStringWidthFontXBig,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontXBig.setAlpha(0);
        this.attachChild(txtFontXBig);
        txtFontBig01 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.6f,
                ResourceManager.getInstance().fontBig, maxStringWidthFontBig,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontBig01.setAlpha(0);
        this.attachChild(txtFontBig01);
        txtFontBig02 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.6f,
                ResourceManager.getInstance().fontBig, maxStringWidthFontBig,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontBig02.setAlpha(0);
        this.attachChild(txtFontBig02);
        txtFontBig03 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.6f,
                ResourceManager.getInstance().fontBig, maxStringWidthFontBig,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontBig03.setAlpha(0);
        this.attachChild(txtFontBig03);
        txtFontMedium01 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium01.setAlpha(0);
        this.attachChild(txtFontMedium01);
        txtFontMedium02 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium02.setAlpha(0);
        this.attachChild(txtFontMedium02);
        txtFontMedium03 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium03.setAlpha(0);
        this.attachChild(txtFontMedium03);
        txtFontMedium04 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium04.setAlpha(0);
        this.attachChild(txtFontMedium04);
        txtFontMedium05 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium05.setAlpha(0);
        this.attachChild(txtFontMedium05);
        txtFontMedium06 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.4f,
                ResourceManager.getInstance().fontMedium, maxStringWidthFontMedium,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontMedium06.setAlpha(0);
        this.attachChild(txtFontMedium06);
        txtFontSmall01 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall01.setAlpha(0);
        this.attachChild(txtFontSmall01);
        txtFontSmall02 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall02.setAlpha(0);
        this.attachChild(txtFontSmall02);
        txtFontSmall03 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall03.setAlpha(0);
        this.attachChild(txtFontSmall03);
        txtFontSmall04 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall04.setAlpha(0);
        this.attachChild(txtFontSmall04);
        txtFontSmall05 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall05.setAlpha(0);
        this.attachChild(txtFontSmall05);
        txtFontSmall06 = new Text(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.2f,
                ResourceManager.getInstance().fontSmall, maxStringWidthFontSmall,
                new TextOptions(HorizontalAlign.CENTER), vertBuffObjMan);
        txtFontSmall06.setAlpha(0);
        this.attachChild(txtFontSmall06);
    }

    private void subSequenceProgramming() {
        // Create & add Modifiers to Sprites and text
        DelayModifier modDelProgramation = new DelayModifier(timeDurationProgramming - tDT,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        // Text: Title Programming
                        txtFontXBig.setPosition(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.85f);
                        txtFontXBig.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_title));
                        txtFontXBig.setAlpha(1);
                        // Sprite: Ninja Programmer
                        sprCategories.setPosition(SCREEN_WIDTH * 0.85f, SCREEN_HEIGHT * 0.82f);
                        sprCategories.setCurrentTileIndex(CATEGORY_PROGRAMMING);
                        sprCategories.setAlpha(1);
                        // Text: Wargo
                        txtFontMedium01.setPosition(SCREEN_WIDTH * 0.33f, SCREEN_HEIGHT * 0.65f);
                        txtFontMedium01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_wargo));
                        txtFontMedium01.setAlpha(1);
                        txtFontSmall01.setPosition(SCREEN_WIDTH * 0.33f, SCREEN_HEIGHT * 0.57f);
                        txtFontSmall01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_wargo_name));
                        txtFontSmall01.setAlpha(1);
                        // Text: Bralmu
                        txtFontMedium02.setPosition(SCREEN_WIDTH * 0.25f, SCREEN_HEIGHT * 0.45f);
                        txtFontMedium02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_bralmu));
                        txtFontMedium02.setAlpha(1);
                        txtFontSmall02.setPosition(SCREEN_WIDTH * 0.25f, SCREEN_HEIGHT * 0.37f);
                        txtFontSmall02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_bralmu_name));
                        txtFontSmall02.setAlpha(1);
                        // Text: Danpelgar
                        txtFontMedium03.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.65f);
                        txtFontMedium03.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_danpelgar));
                        txtFontMedium03.setAlpha(1);
                        txtFontSmall03.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.57f);
                        txtFontSmall03.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_danpelgar_name));
                        txtFontSmall03.setAlpha(1);
                        // Text: Virako
                        txtFontMedium04.setPosition(SCREEN_WIDTH * 0.75f, SCREEN_HEIGHT * 0.45f);
                        txtFontMedium04.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_virako));
                        txtFontMedium04.setAlpha(1);
                        txtFontSmall04.setPosition(SCREEN_WIDTH * 0.75f, SCREEN_HEIGHT * 0.37f);
                        txtFontSmall04.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_virako));
                        txtFontSmall04.setAlpha(1);
                        // Text: JJHaggar
                        txtFontMedium05.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.25f);
                        txtFontMedium05.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_jjhaggar));
                        txtFontMedium05.setAlpha(1);
                        txtFontSmall05.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.17f);
                        txtFontSmall05.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_programming_jjhaggar_name));
                        txtFontSmall05.setAlpha(1);
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        txtFontXBig.setAlpha(0);
                        sprCategories.setAlpha(0);
                        txtFontMedium01.setAlpha(0);
                        txtFontSmall01.setAlpha(0);
                        txtFontMedium02.setAlpha(0);
                        txtFontSmall02.setAlpha(0);
                        txtFontMedium03.setAlpha(0);
                        txtFontSmall03.setAlpha(0);
                        txtFontMedium04.setAlpha(0);
                        txtFontSmall04.setAlpha(0);
                        txtFontMedium05.setAlpha(0);
                        txtFontSmall05.setAlpha(0);
                    }
        });
        this.registerEntityModifier(modDelProgramation);

        // Play & Manage music
        SFXManager.playMusic(ResourceManager.getInstance().credits);
    }

    private void subSequenceMusic() {
        // Create & add Modifiers to Sprites and text
        DelayModifier modDelMusic = new DelayModifier(timeDurationMusic - tDT,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        // Text: Title Music
                        txtFontXBig.setPosition(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.85f);
                        txtFontXBig.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_title));
                        txtFontXBig.setAlpha(1);
                        // Sprite: Ninja Musician
                        sprCategories.setPosition(SCREEN_WIDTH * 0.85f, SCREEN_HEIGHT * 0.82f);
                        sprCategories.setCurrentTileIndex(CATEGORY_MUSIC);
                        sprCategories.setAlpha(1);
                        // Text: Studio Evergreen
                        txtFontBig01.setPosition(SCREEN_WIDTH * 0.40f, SCREEN_HEIGHT * 0.68f);
                        txtFontBig01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_evergreen));
                        txtFontBig01.setAlpha(1);
                        // Sprite: Studio Evergreen
                        sprLogoEverGreen.setPosition(SCREEN_WIDTH * 0.30f, SCREEN_HEIGHT * 0.4f);
                        sprLogoEverGreen.setAlpha(1);
                        // Text: Musamic
                        txtFontMedium01.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.55f);
                        txtFontMedium01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_evergreen_musamic));
                        txtFontMedium01.setAlpha(1);
                        txtFontSmall01.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.47f);
                        txtFontSmall01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_evergreen_musamic_name));
                        txtFontSmall01.setAlpha(1);
                        // Text: Danpelgar
                        txtFontMedium02.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.30f);
                        txtFontMedium02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_evergreen_danpelgar));
                        txtFontMedium02.setAlpha(1);
                        txtFontSmall02.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.22f);
                        txtFontSmall02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_music_evergreen_danpelgar_name));
                        txtFontSmall02.setAlpha(1);
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        txtFontXBig.setAlpha(0);
                        sprCategories.setAlpha(0);
                        txtFontBig01.setAlpha(0);
                        sprLogoEverGreen.setAlpha(0);
                        txtFontMedium01.setAlpha(0);
                        txtFontSmall01.setAlpha(0);
                        txtFontMedium02.setAlpha(0);
                        txtFontSmall02.setAlpha(0);
                    }
        });
        this.registerEntityModifier(modDelMusic);
    }

    private void subSequenceGraphics() {
        // Create & add Modifiers to Sprites and text
        DelayModifier modDelGraphics = new DelayModifier(timeDurationGraphics - tDT,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        // Text: Title Graphics
                        txtFontXBig.setPosition(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.85f);
                        txtFontXBig.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_graphics_title));
                        txtFontXBig.setAlpha(1);
                        // Sprite: Ninja Graphics
                        sprCategories.setPosition(SCREEN_WIDTH * 0.85f, SCREEN_HEIGHT * 0.82f);
                        sprCategories.setCurrentTileIndex(CATEGORY_GRAPHICS1);
                        sprCategories.setAlpha(1);
                        // Text: JJHaggar
                        txtFontMedium01.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.55f);
                        txtFontMedium01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_graphics_jjhaggar));
                        txtFontMedium01.setAlpha(1);
                        txtFontSmall01.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.47f);
                        txtFontSmall01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_graphics_jjhaggar_name));
                        txtFontSmall01.setAlpha(1);
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        txtFontXBig.setAlpha(0);
                        sprCategories.setAlpha(0);
                        txtFontMedium01.setAlpha(0);
                        txtFontSmall01.setAlpha(0);
                    }
        });
        this.registerEntityModifier(modDelGraphics);
    }

    private void subSequenceThanks01() {
        // Create & add Modifiers to Sprites and text
        DelayModifier modDelThanks01 = new DelayModifier(timeDurationThanks01 - tDT,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        // Text: Title Thanks
                        txtFontXBig.setPosition(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.85f);
                        txtFontXBig.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_title));
                        txtFontXBig.setAlpha(1);
                        // Sprite: Ninja Thanks 1
                        sprCategories.setPosition(SCREEN_WIDTH * 0.85f, SCREEN_HEIGHT * 0.82f);
                        sprCategories.setCurrentTileIndex(CATEGORY_GRAPHICS2);
                        sprCategories.setAlpha(1);
                        // Text: PigSaint
                        txtFontMedium01.setPosition(SCREEN_WIDTH * 0.33f, SCREEN_HEIGHT * 0.60f);
                        txtFontMedium01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_pigsaint));
                        txtFontMedium01.setAlpha(1);
                        txtFontSmall01.setPosition(SCREEN_WIDTH * 0.33f, SCREEN_HEIGHT * 0.52f);
                        txtFontSmall01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_pigsaint_name));
                        txtFontSmall01.setAlpha(1);
                        // Text: Otaruk
                        txtFontMedium02.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.60f);
                        txtFontMedium02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_otaruk));
                        txtFontMedium02.setAlpha(1);
                        txtFontSmall02.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.52f);
                        txtFontSmall02.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_otaruk_name));
                        txtFontSmall02.setAlpha(1);
                        // Text: Inixtrom
                        txtFontMedium03.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.34f);
                        txtFontMedium03.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_inixtrom));
                        txtFontMedium03.setAlpha(1);
                        txtFontSmall03.setPosition(SCREEN_WIDTH * 0.5f, SCREEN_HEIGHT * 0.26f);
                        txtFontSmall03.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_inixtrom_name));
                        txtFontSmall03.setAlpha(1);
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        sprCategories.setAlpha(0);
                        txtFontMedium01.setAlpha(0);
                        txtFontSmall01.setAlpha(0);
                        txtFontMedium02.setAlpha(0);
                        txtFontSmall02.setAlpha(0);
                        txtFontMedium03.setAlpha(0);
                        txtFontSmall03.setAlpha(0);
                    }
        });
        this.registerEntityModifier(modDelThanks01);
    }

    private void subSequenceThanks02() {
        // Create & add Modifiers to Sprites and text
        DelayModifier modDelThanks02 = new DelayModifier(timeDurationThanks02 - tDT,
                new IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        // Sprite: Ninja Thanks 2
                        sprCategories.setPosition(SCREEN_WIDTH * 0.85f, SCREEN_HEIGHT * 0.82f);
                        sprCategories.setCurrentTileIndex(CATEGORY_READING);
                        sprCategories.setAlpha(1);
                        // Text: AndEngine
                        txtFontBig01.setPosition(SCREEN_WIDTH * 0.4f, SCREEN_HEIGHT * 0.63f);
                        txtFontBig01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_engine_title));
                        txtFontBig01.setAlpha(1);
                        // Sprite: AndEngine Logo
                        sprLogoAndEngine.setPosition(SCREEN_WIDTH * 0.3f, SCREEN_HEIGHT * 0.35f);
                        sprLogoAndEngine.setAlpha(1);
                        // Text: Nicolas Gramlich
                        txtFontMedium01.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.40f);
                        txtFontMedium01.setText(ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_engine_nicolas));
                        txtFontMedium01.setAlpha(1);
                        txtFontSmall01.setPosition(SCREEN_WIDTH * 0.66f, SCREEN_HEIGHT * 0.32f);
                        txtFontSmall01.setText("("+ ResourceManager.getInstance().loadAndroidRes()
                                .getString(R.string.credits_thanks_engine_nicolas_andengine) + ")");
                        txtFontSmall01.setAlpha(1);
                    }
                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        txtFontXBig.setAlpha(0);
                        sprCategories.setAlpha(0);
                        txtFontBig01.setAlpha(0);
                        sprLogoAndEngine.setAlpha(0);
                        txtFontMedium01.setAlpha(0);
                        txtFontSmall01.setAlpha(0);
                    }
        });
        this.registerEntityModifier(modDelThanks02);
    }

    private void finishSequence() {
        SFXManager.playMusic(ResourceManager.getInstance().credits);
        skip();
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadEndingResources();
    }

    /**
     * Skip the Credits Scene.
     */
    private void skip() {
            SFXManager.stopMusic(ResourceManager.getInstance().credits);
            if(GameManager.DEBUG_MODE)
                SceneManager.getInstance().showScene(new TestingScene());
            else
                SceneManager.getInstance().showScene(new RecordsScene());
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