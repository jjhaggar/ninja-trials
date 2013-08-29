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

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.hud.SelectionStripe;

/**
 * Implements the main options scene (sound).
 * @author Madgear Games
 */
public class MainOptionsScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private SelectionStripe selectionStripe;
    private final String[] menuOptions = {"CONFIGURE CONTROLS","MUSIC VOLUME","SOUNDS VOLUME",
            "MUSIC TEST", "SOUND TEST"};

    /**
     * MainOptionsScene constructor.
     * Disabled loading screen.
     */
    public MainOptionsScene() {
        super(0f);
    }
    
    /**
     * This class is not used (loading scene is disabled).
     */
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    /**
     * This class is not used (loading scene is disabled).
     */
    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadOptionResources();
    }

    @Override
    public void onShowScene() {
        // Background:
        // Crate the background Pattern Sprite:
        ResourceManager.getInstance().mainOptionsPatternTR.setTextureSize(WIDTH, HEIGHT);
        Sprite patternSprite = new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().mainOptionsPatternTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // Add pattern sprite to a new entity:
        Entity backgroundEntity = new Entity();
        backgroundEntity.attachChild(patternSprite);
        // Create a new background from the entity ():
        EntityBackground background = new EntityBackground(0.1f, 0.2f, 0.2f, backgroundEntity);
        setBackground(background);
        
        // Options tittle:
        final Text tittle = new Text(WIDTH/2, HEIGHT - 150,
                ResourceManager.getInstance().fontXBig, "OPTIONS",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(tittle);
        
        // SelectionStripe:
        selectionStripe = new SelectionStripe(250, HEIGHT / 2 - 150, 
                SelectionStripe.DISP_VERTICAL, 110f,
                menuOptions, SelectionStripe.TEXT_ALIGN_LEFT, 0);
        attachChild(selectionStripe);
    }

    @Override
    public void onHideScene() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadOptionResources();        
    }

    @Override
    public void onPressDpadUp() {
        selectionStripe.movePrevious();
    }

    @Override
    public void onPressDpadDown() {
        selectionStripe.moveNext();
    }
    
    @Override
    public void onPressDpadLeft() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        // MUSIC VOLUME-
        case 1:
            break;
        // SOUND VOLUME-
        case 2:
            break;
        }
    }

    @Override
    public void onPressDpadRight() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        // MUSIC VOLUME+
        case 1:
            break;
        // SOUND VOLUME+
        case 2:
            break;
        }
    }

    @Override
    public void onPressButtonO() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        // CONFIGURE CONTROLS
        case 0:
            //SceneManager.getInstance().showScene(new ConfigureControlsScene());
            break;
        // MUSIC TEST
        case 3:
            break;
        // SOUND TEST
        case 4:
            break;
        }
    }

    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null)
            SceneManager.getInstance().showScene(new MainMenuScene());
    }
}
