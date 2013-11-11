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
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.hud.SelectionStripe;
import com.madgear.ninjatrials.layers.GameOverLayer;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;
import com.madgear.ninjatrials.trials.TrialSceneCut;
import com.madgear.ninjatrials.trials.TrialSceneJump;
import com.madgear.ninjatrials.trials.TrialSceneRun;
import com.madgear.ninjatrials.trials.TrialSceneShuriken;


public class ResultLoseScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private SelectionStripe selectionStripe;
    private Text continueText;
    private Text countdownText;
    private Text youLostText;
    private SpriteBackground bg;
    private Sprite characterSprite;
    private TimerHandler timerHandler;
    private int count = 10;
    private boolean pressEnabled = true;
    
    /**
     * Contructor (no loading screen).
     */
    public ResultLoseScene() {
        super(0f);
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadResultLoseSceneResources();
    }

    @Override
    public void onShowScene() {
        // Background:
        bg = new SpriteBackground(new Sprite(WIDTH * 0.5f, HEIGHT * 0.5f,
                ResourceManager.getInstance().loseBg,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
        setBackground(bg);

        // Continue?
        continueText = new Text(WIDTH - 400, HEIGHT - 200,
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.result_lose_continue),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(continueText);

        // Countdown
        countdownText = new Text(continueText.getX(), continueText.getY() - 400,
                ResourceManager.getInstance().fontXBig, "10",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(countdownText);

        // You Lost
        youLostText = new Text(400, 300,
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.result_lose_you_lost),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        youLostText.setColor(android.graphics.Color.YELLOW);
        attachChild(youLostText);

        // SelectionStripe:
        selectionStripe = new SelectionStripe(continueText.getX(), continueText.getY() - 200, 
                SelectionStripe.DISP_HORIZONTAL, 200f,
                new String[] {ResourceManager.getInstance().loadAndroidRes().getString(R.string.result_lose_yes),
	        		ResourceManager.getInstance().loadAndroidRes().getString(R.string.result_lose_no)}, 
	        	SelectionStripe.TEXT_ALIGN_CENTER, 0);
        attachChild(selectionStripe);

        // Character:
        if(GameManager.getSelectedCharacter() ==
                GameManager.CHAR_SHO) {
            characterSprite = new Sprite(youLostText.getX(), youLostText.getY() + 300,
                    ResourceManager.getInstance().loseCharSho,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        }
        else {
            characterSprite = new Sprite(youLostText.getX(), youLostText.getY() + 300,
                    ResourceManager.getInstance().loseCharRyoko,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        }
        attachChild(characterSprite);

        // Music:
        SFXManager.playMusic(ResourceManager.getInstance().loseMusic);
        SFXManager.playSound(ResourceManager.getInstance().loseYouLose);

        countdown();
    }

    void countdown() {
        timerHandler= new TimerHandler(1, new ITimerCallback()
        {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                count--;
                if(count < 0) {
                    gameOver();
                }
                else {
                    countdownText.setText(Integer.toString(count));
                    timerHandler.reset();
                }
            }
        });
        registerUpdateHandler(timerHandler);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadResultLoseSceneResources();
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
        if(pressEnabled) {
            int optionIndex = selectionStripe.getSelectedIndex();
            switch(optionIndex) {
            case 0:
                // Yes
                GameManager.setLives(GameManager.getLives() - 1);
                if(GameManager.DEBUG_MODE)
                    SceneManager.getInstance().showScene(new TestingScene());
                else
                    // Go to the current trial again:
                    switch(GameManager.getCurrentTrial()) {
                    case GameManager.TRIAL_RUN:
                        SceneManager.getInstance().showScene(new TrialSceneRun());
                        break;
                    case GameManager.TRIAL_CUT:
                        SceneManager.getInstance().showScene(new TrialSceneCut());
                        break;
                    case GameManager.TRIAL_JUMP:
                        SceneManager.getInstance().showScene(new TrialSceneJump());
                        break;
                    case GameManager.TRIAL_SHURIKEN:
                        SceneManager.getInstance().showScene(new TrialSceneShuriken());
                        break;
                    }
                break;
            case 1:
                // No
                gameOver();
                break;
            }
        }
    }

    private void gameOver() {
        clearUpdateHandlers();
        SFXManager.pauseMusic(ResourceManager.getInstance().loseMusic);
        //pressEnabled = false;
        SceneManager.getInstance().showLayer(new GameOverLayer(), false, false, true);
    }
}
