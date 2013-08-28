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

/**
 * Implements the main options scene (sound).
 * @author Madgear Games
 */
public class MainOptionsScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

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
        // Create a new background from the entity (gray-blue):
        EntityBackground background = new EntityBackground(0.42f, 0.57f, 0.67f, backgroundEntity);
        setBackground(background);
        
        // Selection group:
    }

    @Override
    public void onHideScene() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUnloadScene() {
        // TODO Auto-generated method stub
        
    }
}
