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

/**
 * 
 */
package com.madgear.ninjatrials.layers;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.ManagedLayer;
import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.ResultLoseScene;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.sequences.SplashIntroScene;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * @author Madgear Games
 *
 */
public class GameOverLayer extends ManagedLayer {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private final static int GAME_OVER_TIME = 8;
    private Rectangle rec;
    private Text gameOver;
    private TimerHandler timerHandler;

    @Override
    public void onLoadLayer() {
        ResourceManager.getInstance().loadGameOverResources();
    }

    @Override
    public void onShowLayer() {
        rec = new Rectangle(WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        rec.setAlpha(0f);
        rec.setColor(0f, 0f, 0f);
        attachChild(rec);

        gameOver = new Text(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().fontXBig,  
                ResourceManager.getInstance().loadAndroidRes().getString(R.string.result_lose_game_over),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(gameOver);

        rec.registerEntityModifier(new FadeInModifier(5f));

        // Timer:
        timerHandler= new TimerHandler(GAME_OVER_TIME, new ITimerCallback()
        {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                ResourceManager.getInstance().engine.unregisterUpdateHandler(timerHandler);
                SceneManager.getInstance().hideLayer();
                if(GameManager.DEBUG_MODE)
                    SceneManager.getInstance().showScene(new TestingScene());
                else
                    SceneManager.getInstance().showScene(new MainMenuScene());
            }
        });
        ResourceManager.getInstance().engine.registerUpdateHandler(timerHandler);

        // Sound & Music;
        // TODO: game over sound not ready (andengine bug) 
        SFXManager.playSound(ResourceManager.getInstance().gameOver);
        SFXManager.playMusic(ResourceManager.getInstance().gameOverMusic);
    }

    @Override
    public void onHideLayer() {}

    @Override
    public void onUnloadLayer() {
        ResourceManager.getInstance().unloadGameOverResources();
    }

}
