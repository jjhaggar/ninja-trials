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

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.hud.SelectionStripe;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

public class PlayerSelectionScene extends GameScene {

    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    
    private final static float CHAR_SHO_X = 1190;
    private final static float CHAR_SHO_Y = HEIGHT - 508;
    private final static float CHAR_RYOKO_X = 734;
    private final static float CHAR_RYOKO_Y = CHAR_SHO_Y -50;
    private static final float CHAR_SELEC_ALPHA = 0.5f;

    private static final float PANEL_ALPHA = 0.6f;
    private static final float PANEL_X = WIDTH/2;
    private static final float PANEL_Y = 450;
    private static final float SELECT_DIFF_FLASH_TIME = 4;
    
    private Text selectPlayerText;
    private SpriteBackground bg;
    private Sprite moon;
    private Sprite clouds1;
    private Sprite clouds2;
    private Sprite roof;
    private Sprite charSho;
    private Sprite charShoOutline;
    private Sprite charShoSelected;
    private Sprite charRyoko;
    private Sprite charRyokoOutline;
    private Sprite charRyokoSelected;
    private static int charSelected = GameManager.CHAR_RYOKO;
    private static int diffIndex = 1;
    private int selectionStep = 0;

    private Text selectDiffText; 
    private Sprite diffPanel;
    private SelectionStripe selectionStripe;
    private String[] menuOptions = {ResourceManager.getInstance().loadAndroidRes().getString(R.string.select_menu_level_easy),
    		ResourceManager.getInstance().loadAndroidRes().getString(R.string.select_menu_level_normal),
    		ResourceManager.getInstance().loadAndroidRes().getString(R.string.select_menu_level_hard)};
    private TimerHandler timerHandler;
    
    
    
    public PlayerSelectionScene() {
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
        ResourceManager.getInstance().loadMenuSelectedResources();
    }

    @Override
    public void onShowScene() {
        // Sky Background:
        bg = new SpriteBackground(new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().menuSelectSky,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
        setBackground(bg);
  
        // Moon:
        moon = new Sprite(WIDTH/2, HEIGHT-200,
                ResourceManager.getInstance().menuSelectMoon,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(moon);
        
        // Clouds:
        clouds1 = new Sprite(WIDTH/4, HEIGHT/2 + 200,
                ResourceManager.getInstance().menuSelectClouds1,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(clouds1);
        
        clouds2 = new Sprite(WIDTH*3/4+200, HEIGHT/2-150,
                ResourceManager.getInstance().menuSelectClouds2,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(clouds2);
        
        // Roof:
        roof = new Sprite(WIDTH/2, 192,
                ResourceManager.getInstance().menuSelectRoof,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(roof);
        
        // Select Player Text:
        selectPlayerText = new Text(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.select_menu_select_player),
                new TextOptions(HorizontalAlign.LEFT),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        selectPlayerText.setX(selectPlayerText.getWidth()/2 + 100);
        selectPlayerText.setY(HEIGHT - selectPlayerText.getHeight()/2 - 100);
        attachChild(selectPlayerText);
        
        // Sho Outline
        charShoOutline = new Sprite(CHAR_SHO_X, CHAR_SHO_Y,
                ResourceManager.getInstance().menuSelectChShoOutline,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        charShoOutline.setVisible(false);
        attachChild(charShoOutline);
        
        // Sho:
        charSho = new Sprite(CHAR_SHO_X, CHAR_SHO_Y,
                ResourceManager.getInstance().menuSelectChSho,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(charSho);
        
        // Sho selected:
        charShoSelected = new Sprite(CHAR_SHO_X, CHAR_SHO_Y,
                ResourceManager.getInstance().menuSelectChShoOutline,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        charShoSelected.setVisible(false);
        charShoSelected.setAlpha(CHAR_SELEC_ALPHA);
        attachChild(charShoSelected);
        
        // Ryoko Outline:
        charRyokoOutline = new Sprite(CHAR_RYOKO_X, CHAR_RYOKO_Y,
                ResourceManager.getInstance().menuSelectChRyokoOutline,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(charRyokoOutline);
          
        // Ryoko:
        charRyoko = new Sprite(CHAR_RYOKO_X, CHAR_RYOKO_Y,
                ResourceManager.getInstance().menuSelectChRyoko,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(charRyoko);
        
        // Ryoko Selected:
        charRyokoSelected = new Sprite(CHAR_RYOKO_X, CHAR_RYOKO_Y,
                ResourceManager.getInstance().menuSelectChRyokoOutline,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        charRyokoSelected.setVisible(false);
        charRyokoSelected.setAlpha(CHAR_SELEC_ALPHA);
        attachChild(charRyokoSelected);
        
        // LAYER:
        
        // Diff panel:
        diffPanel = new Sprite(PANEL_X, PANEL_Y,
                ResourceManager.getInstance().menuSelectDifficulty,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        diffPanel.setAlpha(PANEL_ALPHA);
        diffPanel.setVisible(false);
        attachChild(diffPanel);
        
        // Diff tittle:
        selectDiffText = new Text(diffPanel.getX() - diffPanel.getWidth()/4, diffPanel.getY(),
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.select_menu_select_difficulty),
                new TextOptions(HorizontalAlign.RIGHT),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        selectDiffText.setVisible(false);
        attachChild(selectDiffText);
        
        // Selection Stripe:
        selectionStripe = new SelectionStripe(
                diffPanel.getX() + diffPanel.getWidth()/4,
                diffPanel.getY(), 
                SelectionStripe.DISP_VERTICAL, 110f,
                menuOptions , SelectionStripe.TEXT_ALIGN_CENTER, 1);
        selectionStripe.setVisible(false);
        attachChild(selectionStripe);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadMenuSelectedResources();
    }
    
    
    
    // INTERFACE --------------------------------------------------------

    /**
     * Select Player or Diff.
     */
    @Override
    public void onPressButtonO() {
        if(selectionStep == 0) {
            // Select Player
            selectionStep = 1;
            if(charSelected == GameManager.CHAR_RYOKO)
                charRyokoSelected.setVisible(true);
            else
                charShoSelected.setVisible(true);

            GameManager.setSelectedCharacter(charSelected);
            selectPlayerText.setVisible(false);
            
            diffPanel.setVisible(true);
            selectDiffText.setVisible(true);
            selectionStripe.setVisible(true);

            SFXManager.playSound(ResourceManager.getInstance().menuActivate);
            //SceneManager.getInstance().showLayer(new DiffSelectLayer(), false, false, false);
        }
        else if(selectionStep == 1) {
            // Select Diff:
            selectionStep = 2;
            GameManager.setSelectedDiff(selectionStripe.getSelectedIndex());
            selectionStripe.textFlash();
            timerHandler = new TimerHandler(SELECT_DIFF_FLASH_TIME, true, new ITimerCallback() {
                @Override
                public void onTimePassed(final TimerHandler pTimerHandler) {
                    PlayerSelectionScene.this.unregisterUpdateHandler(timerHandler);
                    SceneManager.getInstance().showScene(new Intro2Scene());
                } 
            });
            registerUpdateHandler(timerHandler);
            SFXManager.playSound(ResourceManager.getInstance().menuActivate);
        }
    }
    
    /**
     * Cancel Select Player or Diff.
     */
    @Override
    public void onPressButtonA() {
        if(selectionStep == 0) {
            // Cancel Select Player:
            if(GameManager.DEBUG_MODE)
                SceneManager.getInstance().showScene(new TestingScene());
            else
                SceneManager.getInstance().showScene(new MainMenuScene());
        }
        else if (selectionStep == 1) {
            // Cancel Select Diff:
            diffPanel.setVisible(false);
            selectDiffText.setVisible(false);
            selectionStripe.setVisible(false);
            selectionStep = 0;
            charRyokoSelected.setVisible(false);
            charShoSelected.setVisible(false);
        }
    }
    
    @Override
    public void onPressButtonMenu() {
            onPressButtonA();
    }
    
    /**
     * Selected player = Ryoko.
     */
    @Override
    public void onPressDpadLeft() {
        if(selectionStep == 0) {
            charRyokoOutline.setVisible(true);
            charShoOutline.setVisible(false);
            charSelected = GameManager.CHAR_RYOKO;
            SFXManager.playSound(ResourceManager.getInstance().menuFocus);
        }
    }

    /**
     * Selected player = Sho.
     */
    @Override
    public void onPressDpadRight() {
        if(selectionStep == 0) {
            charRyokoOutline.setVisible(false);
            charShoOutline.setVisible(true);
            charSelected = GameManager.CHAR_SHO;   
            SFXManager.playSound(ResourceManager.getInstance().menuFocus);
        }
    }
    
    /**
     * Diff = -1
     */
    @Override
    public void onPressDpadUp() {
        if(selectionStep == 1) {
            selectionStripe.movePrevious();
        }
    } 

    /**
     * Diff = +1
     */
    @Override
    public void onPressDpadDown() {
        if(selectionStep == 1) {
            selectionStripe.moveNext();
        }
    } 
}
