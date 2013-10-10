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
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;


public class HeadCharacter extends Entity {
    public int framePlus = 0;
    private int frameIndex = 0;

    private AnimatedSprite head;
	private IAnimationListener ani;

    public HeadCharacter(float posX, float posY, ITiledTextureRegion tiledTexture, int character) {
        head = new AnimatedSprite(posX, posY, tiledTexture,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // framePlus = 3 * GameManager.getInstance().getSelectedPlayer();
        if (character == GameManager.CHAR_SHO) {
            framePlus = 0;
        }
        else if (character == GameManager.CHAR_RYOKO) {
            framePlus = 3;
        }
        attachChild(head);
    }

    // TODO: revisar esta clase:
    // ani -> no hace nada y se puede quitar
    // con setCurrentTileIndex se selecciona el frame deseado (x, y). -> no hace falta mantener
    // una variable framePlus, sino una CHAR_INDEX
    public void getFrame(int frame) {
		ani = new IAnimationListener() {
            @Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
			}
			@Override
            public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int
                    pRemainingLoopCount, int pInitialLoopCount) {
			}
			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex,
                    int pNewFrameIndex) {
			}
			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			}
        };
        if (0 <= frame + framePlus && frame + framePlus < 3) {
            head.animate(new long[]{100}, new int[]{frame + framePlus}, false, ani);
        }
        else {
            Log.v("updateState", "IndexError" + frame);
        }
    }
    
    public int getFrameIndex() {
        return frameIndex;
    }
    
    public void setFrame(int i) {
        if(i >= 0 && i < 3) {
            frameIndex = i;
            head.setCurrentTileIndex(i + framePlus);
        }
        else
            Log.v("updateState", "IndexError " + i);
    }
}

