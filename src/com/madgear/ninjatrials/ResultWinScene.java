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

import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;

/**
 * This class shows the trial results, and adds the trial score to the total score.
 * @author Madgear Games
 *
 */
public class ResultWinScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private String trialName;
    private Text trialNameText;
    private SpriteBackground bg;
    private Sprite characterSprite;
    private Sprite scroll;
    private TiledSprite drawings;
    private TiledSprite stamp;
    private TimerHandler timerHandler;
    private boolean pressEnabled = true;
    private int drawingIndex;


    public ResultWinScene() {
        super(0f);
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @SuppressWarnings("static-access")
    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadResultWinResources();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onShowScene() {

        testing();
        setup();

        // Background:
        bg = new SpriteBackground(new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().winBg,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
        setBackground(bg);
        
        // Scroll:
        scroll = new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().winScroll,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(scroll);

        // Drawings:
        drawings = new TiledSprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().winDrawings,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        drawings.setCurrentTileIndex(drawingIndex);
        attachChild(drawings);
        
        // Stamp:
        stamp = new TiledSprite(750, HEIGHT - 850,
                ResourceManager.getInstance().winStampRanking,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        stamp.setVisible(true);
        attachChild(stamp);
        
        // Trial Name:
        trialNameText = new Text(WIDTH/2, HEIGHT - 200,
                ResourceManager.getInstance().fontBig, trialName,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(trialNameText);
        
        // Character:
        if(GameManager.getSelectedCharacter() ==
                GameManager.CHAR_SHO) {
            characterSprite = new Sprite(300, HEIGHT - 690,
                    ResourceManager.getInstance().winCharSho,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        }
        else {
            characterSprite = new Sprite(300, HEIGHT - 690,
                    ResourceManager.getInstance().winCharRyoko,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        }
        attachChild(characterSprite);
    }

    @Override
    public void onHideScene() {}

    @SuppressWarnings("static-access")
    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadResultWinResources();
    }

    /*
     * Intialices some scene values.
     */
    private void setup() {
        switch(GameManager.getCurrentTrial()) {
        case GameManager.TRIAL_RUN:
            drawingIndex = 2;
            trialName = "Run Results";
            break;
        case GameManager.TRIAL_CUT:
            drawingIndex = 3;
            trialName = "Cut Results";
            break;
        case GameManager.TRIAL_JUMP:
            drawingIndex = 0;
            trialName = "Jump Results";
            break;
        case GameManager.TRIAL_SHURIKEN:
            drawingIndex = 1;
            trialName = "Shuriken Results";
            break;
        }
    }
    
    private int getTrialScore() {
        int score = 0;
        return score;
    }
    
    private void testing() {
        if (GameManager.DEBUG_MODE == true) {
            GameManager.setCurrentTrial(GameManager.TRIAL_CUT);
            GameManager.player1result.cutConcentration = 70;
            GameManager.player1result.cutTime = 1.444f;
        }
    }
    
    // INTERFACE --------------------------------------------------------

    @Override
    public void onPressButtonO() {
        if(pressEnabled) {
            // TODO: Go to the map screen or ending screen.
            SceneManager.getInstance().showScene(new DummyMenu());
        }
    }
}
