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
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.hud.SelectionStripe;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.trials.*;

public class ResultLoseScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private SelectionStripe selectionStripe;
    private Text continueText;
    private Text countdownText;
    private Text youLostText;
    private Text gameOver;
    private TimerHandler timerHandler;
    private final static int COUNT_INIT = 10;
    private int count = COUNT_INIT;
    
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
        // Continue?
        continueText = new Text(WIDTH - 400, HEIGHT - 200,
                ResourceManager.getInstance().fontXBig, "Continue?",
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
        youLostText = new Text(300, 300,
                ResourceManager.getInstance().fontXBig, "You Lost!!",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        youLostText.setColor(android.graphics.Color.YELLOW);
        attachChild(youLostText);

        // SelectionStripe:
        selectionStripe = new SelectionStripe(continueText.getX(), continueText.getY() - 300, 
                SelectionStripe.DISP_HORIZONTAL, 50f,
                new String[] {"yes", "no"}, SelectionStripe.TEXT_ALIGN_CENTER, 0);
        attachChild(selectionStripe);
        
        // Timer:
        timerHandler= new TimerHandler(1, new ITimerCallback()
        {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                count--;
                if(count < 0) {
                    ResultLoseScene.this.unregisterUpdateHandler(timerHandler);
                    // TODO Go to GameOver Scene!
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
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        case 0:
            // Yes
            GameManager.getInstance().setLives(GameManager.getInstance().getLives() - 1);
            nextTrial(GameManager.getInstance().getCurrentTrial());
            break;
        case 4:
            // No
            gameOver();
            break;
        }
    }

    private void nextTrial(int currentTrial) {
        switch(currentTrial) {
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
    }

    protected void gameOver() {
        Rectangle rec = new Rectangle(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rec.setAlpha(0f);
        rec.setColor(0f, 0f, 0f);
        attachChild(rec);

        gameOver = new Text(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().fontXBig, "Game Over",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(gameOver);
        
        rec.registerEntityModifier(new FadeInModifier(5f));
    }
}
