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


package com.madgear.ninjatrials.trials;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.hud.Chronometer;
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.PrecisionAngleBar;
import com.madgear.ninjatrials.hud.PrecisionBar;

/**
 * Cut trial scene.
 *
 * @author Madgear Games
 *
 */
public class TrialSceneJump extends GameScene {
    private static final int SCORE_POOR = 20;
    private static final int SCORE_GREAT = 90;
    private float timeRound;  // tiempo para ciclo de powerbar
    private float timeMax = 10; // Tiempo máximo para corte:
    private float timeCounter = timeMax; // Tiempo total que queda para el corte
    private int frameNum = 0; // Contador para la animación
    private float timerStartedIn = 0; // control de tiempo
    private float origX, origY = 0.0f;
    
    private float[] destiny = {0, 0};
    

    private float WIDTH = ResourceManager.getInstance().cameraWidth;
    private float HEIGHT = ResourceManager.getInstance().cameraHeight;

    private float[] origin = {WIDTH / 2 - 120, HEIGHT / 2};
    private SpriteBackground bg;
    private Statue mStatue;
    private GameHUD gameHUD;
    private PrecisionAngleBar angleBar;
    private Chronometer chrono;
    private Character mCharacter;
    private boolean cutEnabled = false;
    private TimerHandler trialTimerHandler;
    private IUpdateHandler trialUpdateHandler;
    private final float readyTime = 4f;
    private final float endingTime = 6f;
    private int score = 0;
    private float[] scoreJump;

    /**
     * Calls the super class constructor.
     */
    public TrialSceneJump() {
        super();
    }

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene(); // Provisional, sera una clase externa
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        // Añadimos algo de texto:
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.3f,
                ResourceManager.getInstance().fontBig, "Loading...",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        loadingScene.attachChild(loadingText);
        return loadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    /**
     * Loads all the Scene resources and create the main objects.
     */
    @Override
    public void onLoadScene() {
       ResourceManager.getInstance().loadJumpSceneResources();
        setTrialDiff(GameManager.getSelectedDiff());
  //      bg = new SpriteBackground(new Sprite(width * 0.5f, height * 0.5f,
  //              ResourceManager.getInstance().cutBackground,
  //              ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
  //      setBackground(bg);
       mStatue = new Statue();
       mCharacter = new Character(WIDTH / 2 - 120, HEIGHT / 2);
        gameHUD = new GameHUD();
        angleBar = new PrecisionAngleBar(200f, 200f, timeRound);
        chrono = new Chronometer(WIDTH - 200, HEIGHT - 200, 10, 0);
        

    }

    /**
     * Put all the objects in the scene.
     */
    @Override
    public void onShowScene() {
        setBackgroundEnabled(true);

        //attachChild(mStatue);
        attachChild(mCharacter);
        ResourceManager.getInstance().engine.getCamera().setHUD(gameHUD);
        gameHUD.attachChild(angleBar);
        gameHUD.attachChild(chrono);
        

        readySequence();
    }

    @Override
    public void onHideScene() {}

    /**
     * Unloads all the scene resources.
     */
    @Override
    public void onUnloadScene() {
    //    ResourceManager.getInstance().unloadCutSceneResources();
        ResourceManager.getInstance().unloadJumpSceneResources();
    }

    /**
     * Shows a Ready Message, then calls actionSecuence().
     * "Ready" is displayed 1 sec after the scene is shown and ends 1 secs before the 
     * action secuence begins.
     */
    private void readySequence() {
        gameHUD.showMessage("Ready", 1, readyTime - 1);
        mCharacter.start(); // <-
        timerStartedIn = ResourceManager.getInstance().engine.getSecondsElapsedTotal(); 
        trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                timerStartedIn + readyTime) {
                    TrialSceneJump.this.unregisterUpdateHandler(trialUpdateHandler);
                    actionSequence();
                  }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
    }

    /**
     * Main trial secuence. Shows a "Cut!" message, starts the Chronometer and enables the cut.
     */
    protected void actionSequence() {
        trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
               // if(chrono.isTimeOut()) {
                 //   TrialSceneJump.this.unregisterUpdateHandler(trialUpdateHandler);
                  //  timeOut();
               // }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
        gameHUD.showMessage("Jump!", 0, 1);
        chrono.start();
      //  precisionBar.start();
        angleBar.start();
        cutEnabled = true;
    }

  
    public void jumpSequence() {
        cutEnabled = false;
        chrono.stop();
    //    precisionBar.stop();
        angleBar.stop();
        scoreJump = getScoreJump();
        frameNum = 0;
        origin = mCharacter.jump(origin, scoreJump); // <-
        trialTimerHandler = new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                pTimerHandler.reset();  // new frame each 0.1 second !
                if (frameNum == 10) 
                
                frameNum++;
            }
        });
        actionSequence();
        registerUpdateHandler(trialTimerHandler);
        cutEnabled = true;
    }

    /**
     * Shows the score and the final animation. Clean the HUD and calls to the next scene.
     */
    private void endingSequence() {
        String message;
        GameManager.incrementScore(score);
        if(score <= SCORE_POOR) {
            message = "POOR " + score;
        }
        else if(score >= SCORE_GREAT) {
            message = "GREAT! " + score;
        }
        else {
            message = "MEDIUM " + score;
        }
        gameHUD.showComboMessage("Your score is...\n" + message);
        trialTimerHandler= new TimerHandler(endingTime, new ITimerCallback()
        {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                TrialSceneJump.this.unregisterUpdateHandler(trialTimerHandler);
                gameHUD.detachChildren();
                SceneManager.getInstance().showScene(new MainMenuScene());
            }
        });
        registerUpdateHandler(trialTimerHandler);
    }

    /**
     * When time is out the cut is not enabled. Calls ending secuence.
     */
    private void timeOut() {
        cutEnabled = false;
      //  precisionBar.stop();
        angleBar.stop();
        score = 0;
        endingSequence();
    }

    /**
     * When the action button is pressed launch the cut if enabled.
     */
    @Override
    public void onPressButtonO() {
        if (cutEnabled) {
      //      cutSequence();
        	jumpSequence();
        }
    }

    /**
     * Adjust the trial parameters using the game difficulty as base.
     * @param diff The game difficulty.
     */
    private void setTrialDiff(int diff) {
        if(diff == GameManager.DIFF_EASY)
            timeRound = 4;
        else if(diff == GameManager.DIFF_MEDIUM)
            timeRound = 2;
        else if(diff == GameManager.DIFF_HARD)
            timeRound = 1;
    }

    
    /**
     * Calculates the trial score.
     * Score = 100 - abs(precision bar power value) - precision bar semicycle number * 3
     * @return The Trial Score (int from 0 to 100).
     */
    public static int getScore() {
        float[] trialScore = new float[1];
        //trialScore = 100 - Math.abs(precisionBar.getPowerValue()) - precisionBar.getSemicycle() * 3;
       // trialScore = angleBar.getPowerValue();
        return (int)trialScore[0];
    }
    
    /**
     * Calculates the trial score.
     * Score = 100 - abs(precision bar power value) - precision bar semicycle number * 3
     * @return The Trial Score (int from 0 to 100).
     */
    public float[] getScoreJump() {
        float[] trialScore;
        //trialScore = 100 - Math.abs(precisionBar.getPowerValue()) - precisionBar.getSemicycle() * 3;
        trialScore = angleBar.getPowerValue();
        return trialScore;
    }

    
// Auxiliary Classes

    /**
     * Controls the character object in the scene
     * @author Madgear Games
     */
    private class Character extends Entity {
        private AnimatedSprite charSprite;

        public Character(float posX, float posY) {
			charSprite = new AnimatedSprite(posX, posY,
                    ResourceManager.getInstance().jumpChSho,
                    ResourceManager.getInstance().engine.getVertexBufferObjectManager());
            attachChild(charSprite);
        }
        
        public void start() {
        	charSprite.animate(new long[] { 100, 100 }, 1, 2, true);
        //	Path path = new Path(2).to(0f, 0f).to(0f,0f);
        	
        //	charSprite.registerEntityModifier(new PathModifier(.0f, path));
        }
        
        public float[] jump(float[] origin, float[] score) {
        	float angle = (float) Math.atan(score[0]/score[1]);
        	float[] destiny = {0, 0};
        	float xDistance = WIDTH - 50;
        	// x will be 0 or 100 always
        	if (origin[0] == 50f){
        		destiny[0] = xDistance;
        		charSprite.setFlippedHorizontal(false);
        	}
        	else{
        		destiny[0] = 50f;
        		charSprite.setFlippedHorizontal(true);
        	}
        	charSprite.animate(new long[] { 100, 100, 100, 100, 100
        			, 100, 100, 100}, new int[] {8, 9, 10, 11, 12, 13, 14, 15},
        			false);
        	
        	destiny[1] = ((float) (Math.tan(angle) * xDistance)) * 0.1f + origin[1]; // its correct (float) (Math.tan(angle) * xDistance) + origin[1];
        	
        	//erase later
        	if (destiny[1] > 1800f)
        		destiny[1] = 0f;
        	
        	Path path = new Path(2).to(origin[0], origin[1])
        			.to(destiny[0],destiny[1]);
        	
        	charSprite.registerEntityModifier(new PathModifier(.4f, path));
        	if (Double.isNaN(destiny[0]) || Double.isNaN(destiny[1]))
        		destiny = origin;
        	return destiny;
        }

    }
    
    private class Statue extends Entity {
    	private Sprite statueSprite;
    	
    	public Statue() {
    		statueSprite = new Sprite(500, 500, 
    				ResourceManager.getInstance().jumpBg1StoneStatues,
    				ResourceManager.getInstance().engine.getVertexBufferObjectManager());
    		attachChild(statueSprite);
    	}
    }


    public static int getStamp(int score2) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static int getTimeScore() {
        // TODO Auto-generated method stub
        return 0;
    }

    public static int getPerfectJumpScore() {
        // TODO Auto-generated method stub
        return 0;
    }

    public static int getMaxPerfectJumpScore() {
        // TODO Auto-generated method stub
        return 0;
    }
}
