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


package com.madgear.ninjatrials.trials.run;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.debug.Debug;

import com.madgear.ninjatrials.trials.TrialSceneRun;
import com.madgear.ninjatrials.managers.ResourceManager;


/**
 * Class to load character in screen.
 */
public class RunCharacter extends Entity {

    private AnimatedSprite charSprite;
	private IAnimationListener anilis;
	private IAnimationListener ani;
	private float speed = 1;
    private final int[] ANIM_PREPARATION = new int[]{0, 1, 2};
    private final int[] ANIM_RUN_NORMAL = new int[]{3, 5, 6, 7, 8, 10};
    private final int[] ANIM_RUN_FAST = new int[]{11, 12, 13, 15, 16, 17};
    private final int[] ANIM_LOSE = new int[]{14, 19};
    private final int[] ANIM_WIN = new int[]{4, 9};

    /**
     * Constructor. Create a character and show in screen.
     *
     * @param posX float position X
     * @param posY float position Y
     * @param tiledTexture ITiledTextureRegion with the select player
     *
     * @return RunCharacter objects.
     */
    public RunCharacter(float posX, float posY, ITiledTextureRegion tiledTexture) {
        charSprite = new AnimatedSprite(posX, posY, tiledTexture,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(charSprite);
    }

    /**
     * Update animation with power value.
     *
     * @param power int power value
     */
	public void updateRunAnimation(int power) {
		anilis = new IAnimationListener() {
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
                //updateRunAnimation(TrialSceneRun.power);
			}
		};

		if (power > 0) {
			long sM = (long)(100 * (50 / power));
            if (power <= 50) {
				charSprite.animate(new long[]{sM, sM, sM, sM, sM, sM}, ANIM_RUN_NORMAL, false, anilis);
            }
            else if (power > 50 && power <= 90) {
				charSprite.animate(new long[]{sM, sM, sM, sM, sM, sM}, ANIM_RUN_FAST, false, anilis);
            }
			else if (power > 90){
				charSprite.animate(new long[]{sM, sM, sM, sM, sM, sM}, ANIM_RUN_FAST, false, anilis);
			}
		}
		else {
            charSprite.animate(new long[]{300, 300, 300}, ANIM_PREPARATION, false, anilis);
		}
	}

    public void win(float posX, float posY) {
        charSprite.setPosition(posX, posY);
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
        charSprite.animate(new long[]{300, 300}, ANIM_WIN, false, ani);
    }

    public void lose(float posX, float posY) {
        charSprite.setPosition(posX, posY);
		ani = new IAnimationListener() { @Override
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
        charSprite.animate(new long[]{300, 300}, ANIM_LOSE, false, ani);
    }
}
