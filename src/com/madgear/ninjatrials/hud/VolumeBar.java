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

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import com.madgear.ninjatrials.managers.ResourceManager;

/**
 * Draws a "volume" bar. It is composed by two bars, the background bar, and the active bar that
 * is painted on top of the background one. The portion of the active bar that is drawed depends
 * on the value of the bar.
 * 
 * @author Madgear Games
 *
 */
public class VolumeBar extends Entity {
    private float value;
    private float x;
    private float y;
    private final static int VALUE_MIN = 0;
    private final static int VALUE_MAX = 100;
    private static float texture_width;
    private final static int texture_height = 110;
    private ITextureRegion shownBarTR = null;
    private Sprite shownBar;
    private Sprite bgBar;
    
    /**
     * Constructs a VolumeBar object.
     * @param posX The x axis position.
     * @param posY The y axis position.
     * @param musicPercentage The initial value (from VALUE_MIN to VALUE_MAX)
     */
    public VolumeBar(float posX, float posY, float musicPercentage) {
        x = posX;
        y = posY;
        if(musicPercentage >= VALUE_MIN && musicPercentage <= VALUE_MAX) value = musicPercentage;
        else value = 0;
        
        bgBar = new Sprite(x, y,
                ResourceManager.getInstance().mainOptionsSoundBarsInactiveTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(bgBar);
        
        texture_width = ResourceManager.getInstance().mainOptionsSoundBarsInactiveTR.getWidth();

        shownBarTR = null;
        drawActiveBar();
    }
    
    /**
     * Draws the volume bar (only thr active colored part).
     */
    public void drawActiveBar() {
        int xPartShown;
        
        // Clean memory if the texture is loaded:
        if(shownBarTR!=null) {
            if(shownBarTR.getTexture().isLoadedToHardware()) {
                //shownBarTR.getTexture().unload();
                shownBarTR = null;
                shownBar.detachSelf();
            }
        }
        // Calculates the pixels that must be shown:
        xPartShown = Math.round(value * texture_width / VALUE_MAX);
        // Load texture showing only the part on the left of value:
        shownBarTR = TextureRegionFactory.extractFromTexture(
                ResourceManager.getInstance().mainOptionsSoundBarsActiveTR.getTexture(),
                0, 0, xPartShown, texture_height, false);
        shownBar = new Sprite(
                x - texture_width/2 + shownBarTR.getWidth()/2, y,
                shownBarTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(shownBar);
    }
    
    /**
     * Increments the value of the bar.
     * @param v The amount increased.
     */
    public void addValue(int v) {
        value+= v;
        if(value > VALUE_MAX) value = VALUE_MAX;
        if(value < VALUE_MIN) value = VALUE_MIN;
        drawActiveBar();
    }
    
    /**
     * Sets the value of the bar.
     * @param v The new value.
     */
    public void setValue(float v) {
        if(v >= VALUE_MIN && v <= VALUE_MAX) value = v;
        drawActiveBar();
    }
    
    /**
     * Returns the value of the bar.
     * @return The value.
     */
    public float getValue() {
        return value;        
    }
}

