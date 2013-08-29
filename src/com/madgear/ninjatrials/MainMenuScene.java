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
 * Main Menu Scene. Displays a pattern background, a logo, and a selection stripe.
 * @author Madgear Games
 */
public class MainMenuScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private Sprite tittle;
    private SelectionStripe selectionStripe;
    private final String[] menuOptions = {"OPTIONS","PLAY","ACHIEVEMENTS"};

    
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
    public void onLoadScene() {
        ResourceManager.getInstance().loadMainMenuResources();        
    }

    @SuppressWarnings("static-access")
    @Override
    public void onShowScene() {
        // TODO shading background (from blue to white).
        // Background:
        // Crate the background Pattern Sprite:
        ResourceManager.getInstance().mainTitlePattern1TR.setTextureSize(WIDTH, HEIGHT);
        Sprite patternSprite = new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().mainTitlePattern1TR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // Add pattern sprite to a new entity:
        Entity backgroundEntity = new Entity();
        backgroundEntity.attachChild(patternSprite);
        // Create a new background from the entity (gray-blue):
        EntityBackground background = new EntityBackground(0.42f, 0.57f, 0.67f, backgroundEntity);
        setBackground(background);

        // NinjaTrials Logo:
        tittle = new Sprite(WIDTH / 2, HEIGHT / 2 + 100,
                ResourceManager.getInstance().mainTitleTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(tittle);
        
        // Selection Stripe:
        selectionStripe = new SelectionStripe(WIDTH / 2, HEIGHT / 2 - 300, 
                SelectionStripe.DISP_HORIZONTAL, 500f,
                menuOptions, SelectionStripe.TEXT_ALIGN_CENTER, 1);
        attachChild(selectionStripe);
    }

    @Override
    public void onHideScene() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadMainMenuResources();        
    }

    @Override
    public void onPressDpadLeft() {
        selectionStripe.movePrevious();
    }
    
    @Override
    public void onPressDpadRight() {
        selectionStripe.moveNext();
    }
    
    @Override
    public void onPressButtonO() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        case 0:
            SceneManager.getInstance().showScene(new DummyMenu());
            break;
        case 1:
            SceneManager.getInstance().showScene(new TrialSceneCut());
            break;
        case 2:
            SceneManager.getInstance().showScene(new DummyMenu());
            break;
        }
    }
    
    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null) {
            SceneManager.getInstance().mCurrentScene.onHideManagedScene();
            SceneManager.getInstance().mCurrentScene.onUnloadManagedScene();
            ResourceManager.getInstance().unloadHUDResources();
            ResourceManager.getInstance().unloadFonts();
            System.exit(0);
        }
    }

}