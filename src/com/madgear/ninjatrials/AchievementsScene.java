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
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import com.madgear.ninjatrials.achievements.Achievement;
import com.madgear.ninjatrials.achievements.AchievementSetNinjaTrial;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

public class AchievementsScene extends GameScene {

    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private AchievementGrid achievementGrid;
    
    public AchievementsScene() {
        super(0);
    }
    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadMenuAchievementsResources();
    }

    @Override
    public void onShowScene() {
        // Bg:
        // TODO: background
        getBackground().setColor(0.5f, 0.3f, 0.8f);
        
        // Main achievements grid:
        achievementGrid = new AchievementGrid(662, HEIGHT/2, GameManager.player1achiev);
        attachChild(achievementGrid);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadMenuAchievementsResources();
    }

    // INTERFACE -------------------------------------------
    /**
     * Returns to the main options screen when menu button is pressed.
     */
    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null)
            SceneManager.getInstance().showScene(new TestingScene());
    }
    
    
    // AUX CLASS ---------------------------------------------
    
    class AchievementGrid extends Entity {
        private static final int COLS = 7;
        private static final int ROWS = 5;
        private static final float BORDER_ICON_GAP = 20;
        private float iconSpaceX, iconSpaceY;
        private Sprite containerIconsSprite;
        float itemX, itemY;
        AchievementItem items[][];
        
        public AchievementGrid(float x, float y, AchievementSetNinjaTrial set) {
            containerIconsSprite = new Sprite(x, y, 
                    ResourceManager.getInstance().menuAchievementsContainerIcons,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(containerIconsSprite);
            
            iconSpaceX = (containerIconsSprite.getWidth() - BORDER_ICON_GAP * 2) / COLS;
            iconSpaceY = (containerIconsSprite.getHeight() - BORDER_ICON_GAP * 2) / ROWS;
            
            items = new AchievementItem[COLS][ROWS];
            
            for(int i = 0; i < COLS; i++)
                for (int j = 0; j < ROWS; j++) {
                    itemX = containerIconsSprite.getX() - containerIconsSprite.getWidth() /2 
                            + BORDER_ICON_GAP + iconSpaceX * (0.5f + i);
                    itemY = containerIconsSprite.getY() + containerIconsSprite.getHeight() /2
                            - (BORDER_ICON_GAP + iconSpaceY * (0.5f + j));
                    
                    items[i][j] = new AchievementItem(itemX, itemY, i, j, 
                            set.achievements[j * COLS + i]);
                    attachChild(items[i][j]);
            }
        }
    }
    
    
    class AchievementItem extends Entity {
        private final int ICON_SIZE = ResourceManager.getInstance().MENU_ACHIEV_ICON_SIZE;
        private TiledSprite achievItemSprite;
        
        AchievementItem(float x, float y, int col, int row, Achievement achiev) {
            
            achievItemSprite = new TiledSprite(x, y,
                    ResourceManager.getInstance().menuAchievementsIconsArray[col][row], 
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            if(achiev.completed)
                achievItemSprite.setCurrentTileIndex(1);
            else 
                achievItemSprite.setCurrentTileIndex(0);    
            attachChild(achievItemSprite);
        }
    }
    
    
    
    class AchievementDetail extends Entity {
        private Sprite containerDescriptionSprite;
        private Sprite iconsSprite;

        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
