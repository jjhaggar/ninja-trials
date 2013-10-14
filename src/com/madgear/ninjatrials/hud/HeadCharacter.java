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

import android.util.Log;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;


public class HeadCharacter extends Entity {
    public static final int HEAD_INDEX_SHO = 0;
    public static final int HEAD_INDEX_RYOKO = 1;
    private static final int TILE_NUM = 3;
    private int charIndex = HEAD_INDEX_SHO;
    private int tileIndex = 0;
    private int character;

    private TiledSprite head;

    public HeadCharacter(float posX, float posY, ITiledTextureRegion tiledTexture, int pChar) {
        head = new TiledSprite(posX, posY, tiledTexture,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        if (pChar == GameManager.CHAR_SHO)
            charIndex = HEAD_INDEX_SHO;
        else if (pChar == GameManager.CHAR_RYOKO)
            charIndex = HEAD_INDEX_RYOKO;
        attachChild(head);
        character = pChar;
    }    
    
    /**
     * 
     * @return The current index of the head tile. (0 to 2)
     */
    public int getIndex() {
        return tileIndex;
    }
    
    
    /**
     * @return The character head offset.
     */
    public int getCharIndex() {
        return charIndex;
    }
    
    
    /**
     * Sets the tile index of the head.
     * @param i The index of the tile. (0 to 2)
     */
    public void setIndex(int i) {
        if(i >= 0 && i < 3) {
            tileIndex = i;
            head.setCurrentTileIndex(charIndex * TILE_NUM + tileIndex);
            // no funciona  setCurrentTileIndex(x,y)!!!    :(
        }
        else
            Log.v("updateState", "IndexError " + i);
    }
}

