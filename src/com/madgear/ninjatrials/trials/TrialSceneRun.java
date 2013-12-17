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

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.ResultLoseScene;
import com.madgear.ninjatrials.ResultWinScene;
import com.madgear.ninjatrials.hud.Chronometer;
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.HeadCharacter;
import com.madgear.ninjatrials.hud.PowerBar;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.trials.run.RunBg;
import com.madgear.ninjatrials.trials.run.RunCharacter;


/**
 * Run trial scene.
 */
public class TrialSceneRun extends GameScene {

    private final float width = ResourceManager.getInstance().cameraWidth;
    private final float height = ResourceManager.getInstance().cameraHeight;

    public static int power = 0;
    public static final int SCORE_THUG = 5000;
    public static final int SCORE_NINJA = 7000;
    public static final int SCORE_NINJA_MASTER = 9000;
    public static final int SCORE_GRAND_MASTER = 9500;
    private boolean canGainPower = false;
    private final int minPower = 0;
    private final int maxPower = 110;
    private final int powerHight = 100;
    private final int powerDecrement = 2;
    private final int powerIncrement = this.powerDecrement * 4;
    private float comboActual = 0;
    private static float comboTotal = 0;
    private static float maxCombo = 0;
    private static int maxSpeed = 0;

    private static int distanceReached = 0;
    private static final int DISTANCE_TOTAL = 100;
    private boolean distanceCompleted = false;
    private static int score = 0;
    private static float timeTotal = 0;

    private final float timeLoopLogic = 0.1f;
    private final int timeTrial = 10;
    private final float endingTime = 6;
    public int seconds = 10; // TODO fix loading screen first

    private Chronometer chrono;
    private GameHUD gameHUD;
    private HeadCharacter head;
    private PowerBar powerBar;
    private RunBg parallaxBackground;
    private RunCharacter character;
    private TimerHandler trialTimerHandler;


    /**
     * Calls the super class constructor.
     * Loading scene is enabled by default.
     */
    public TrialSceneRun() {
        super(1f);
    }

    /**
     * Loading screen. Provisional class
     * @return Scene
     */
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene();
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        final Text loadingText = new Text(this.width * 0.5f, this.height * 0.3f,
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.app_loading),
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
        ResourceManager.getInstance().loadRunSceneResources();
        ResourceManager.getInstance().loadHUDResources();
        this.parallaxBackground = new RunBg(0, -10f, -120f, -5f);
        this.gameHUD = new GameHUD();
        this.chrono = new Chronometer(this.width - 200, this.height - 200, this.timeTrial, 0);
        this.powerBar = new PowerBar(330, 110, this.minPower, this.maxPower);
        this.head = new HeadCharacter(110, 110, ResourceManager.getInstance().runHead,
                GameManager.getSelectedCharacter());
        this.head.setIndex(0);
        if (GameManager.getSelectedCharacter() == GameManager.CHAR_SHO) {
            this.character = new RunCharacter(this.width / 2, this.height / 2,
                    ResourceManager.getInstance().runSho);
        }
        else if (GameManager.getSelectedCharacter() == GameManager.CHAR_RYOKO) {
            this.character = new RunCharacter(this.width / 2, this.height / 2,
                    ResourceManager.getInstance().runRyoko);
        }
        this.parallaxBackground.updateSpeed(power);
    }

    /**
     * Put all the objects in the scene.
     */
    @Override
    public void onShowScene() {
        this.attachChild(this.parallaxBackground);
        this.attachChild(this.character);
        ResourceManager.getInstance().engine.getCamera().setHUD(this.gameHUD);
        this.gameHUD.attachChild(this.chrono);
        this.gameHUD.attachChild(this.powerBar);
        this.gameHUD.attachChild(this.head);
        // TODO loading screen stolen me time by the face
        this.runPreparation();
    }

    /**
     * Control time, handler and objects before of start the trial.
     */
    private void runPreparation() {
        this.registerUpdateHandler(new TimerHandler(1, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                if (TrialSceneRun.this.seconds == 4) {
                    TrialSceneRun.this.gameHUD.showMessage(ResourceManager.getInstance().loadAndroidRes().getString(R.string.trial_run_ready), 0, 1);
                }
                else if (1 <= TrialSceneRun.this.seconds && TrialSceneRun.this.seconds <= 3) {
                    TrialSceneRun.this.gameHUD.showMessage("" + TrialSceneRun.this.seconds);
                    TrialSceneRun.this.canGainPower = true;
                }
                else if (TrialSceneRun.this.seconds == 0) {
                    TrialSceneRun.this.gameHUD.showMessage(ResourceManager.getInstance().loadAndroidRes().getString(R.string.trial_run_go), 0, 1);
                    TrialSceneRun.this.runStart();
                }
                else {
                    TrialSceneRun.this.gameHUD.showMessage("...", 0, 1);
                }
                TrialSceneRun.this.seconds--;
            }
        }));
    }

    /**
     * Control the time, handler and objects in the course trial.
     */
    private void runStart(){
        this.clearUpdateHandlers();
        this.chrono.start();
        this.registerUpdateHandler(new TimerHandler(this.timeLoopLogic, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                TrialSceneRun.this.chrono.setTimeValue(TrialSceneRun.this.chrono.getTimeValue() - TrialSceneRun.this.timeLoopLogic);
                TrialSceneRun.this.updatePower(-TrialSceneRun.this.powerDecrement);
                if (power >= TrialSceneRun.this.powerHight) {
                    TrialSceneRun.this.comboActual += TrialSceneRun.this.timeLoopLogic;
                    comboTotal += TrialSceneRun.this.timeLoopLogic;
                    if (maxCombo < TrialSceneRun.this.comboActual) {
                        maxCombo = TrialSceneRun.this.comboActual;
                    }
                    TrialSceneRun.this.gameHUD.showMessage(ResourceManager.getInstance().loadAndroidRes().getString(R.string.trial_run_combo) + TrialSceneRun.this.comboActual, 0, 1);
                }
                else {
                    TrialSceneRun.this.comboActual = 0;
                }
                TrialSceneRun.this.incrementDistance();
                TrialSceneRun.this.parallaxBackground.updateSpeed(power);
                TrialSceneRun.this.character.updateRunAnimation(power);
                if (TrialSceneRun.this.chrono.isTimeOut() || TrialSceneRun.this.distanceCompleted) {
                    TrialSceneRun.this.runFinish();
                }
            }
        }));
    }

    /**
     * Increment distance
     */
    private void incrementDistance(){
        assert distanceReached < 0;
        distanceReached += power / 100;
        if (distanceReached >= DISTANCE_TOTAL ) {
            this.distanceCompleted = true;
        }
    }

    // If sync. error:
    // http://epere4.blogspot.com.es/2008/04/cmo-funciona-synchronized-en-java.html
    // http://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html
    /**
     * Update power with a relative power (negative or positive).
     */
    private void updatePower(int relPower) {
        if (this.canGainPower) {
        	System.out.println("VALUEEEESSSS");
        	System.out.println(power);
            power += relPower / (power / this.maxPower + 1);
        	System.out.println(power);
            if (power > maxSpeed) {
                maxSpeed = power;
            }
            if (power < 0) {
                power = 0;
            }
            this.powerBar.update(power);
            if (power <= 50) {
                this.head.setIndex(0);
            }
            else if (50 <= power && power < 90) {
                this.head.setIndex(1);
            }
            else if (90 <= power && power < this.maxPower) {
                this.head.setIndex(2);
            }
            else if (this.maxPower < power) {
                // TODO add effect
                this.head.setIndex(2);
            }
        }
    }

    /**
     * Show the result of trial in screen.
     */
    private void runShowResults() { // JJ: Results must be showed by using ResultWinScene and ResultLoseScene
        this.clearUpdateHandlers();
        this.canGainPower = false;
        System.out.println("SCORESSS");
        System.out.println(this.chrono.getTimeValue());
        timeTotal = 10 - this.chrono.getTimeValue();
        System.out.println(maxSpeed);
        System.out.println(comboTotal);
        System.out.println(maxCombo);
        score = (int) (maxSpeed * comboTotal * maxCombo / timeTotal);
        if (score >= 20) {
            this.character.win(200, 200);
        }
        else if (score >= 15) {
            this.character.win(200, 200);
        }
        else if (score >= 10) {
            this.character.win(200, 200);
        }
        else if (score < 10) {
            this.character.lose(200, 200);
        }
    }

    /**
     * Calculate the score when you finish the trials or finish the trial time.
     */
    private void runFinish() {
	    // Achievement 1: Run 200 kms. / test mode = 2 m
	    // distanceReached is in meters.
	    if(!GameManager.player1achiev.achievements[0].isCompleted()) {
	        GameManager.player1achiev.achievements[0].progressIncrement(distanceReached);
	        if(GameManager.player1achiev.achievements[0].isCompleted()) {
	            this.gameHUD.showAchievementCompleted(1);
	            GameManager.player1achiev.unlock(1);
	        }
	        else
	            UserData.saveAchiev(ResourceManager.getInstance().context);
	    }

        this.runShowResults();
        this.trialTimerHandler= new TimerHandler(this.endingTime, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                TrialSceneRun.this.unregisterUpdateHandler(TrialSceneRun.this.trialTimerHandler);
                TrialSceneRun.this.resetScene();
                if (TrialSceneRun.score < 10)
                    SceneManager.getInstance().showScene(new ResultLoseScene());
                else
                    SceneManager.getInstance().showScene(new ResultWinScene());
            }
        });
        this.registerUpdateHandler(this.trialTimerHandler);
    }

    /**
     * Reset Scene
     */
    public void resetScene() {
        this.gameHUD.detachChildren();
        this.gameHUD.detachSelf();
    }

    @Override
    public void onHideScene() {}

    /**
     * Unloads all the scene resources.
     */
    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadRunSceneResources();
    }

    /**
     * Input control (Ouya gamepad and touch event).
     */
    @Override
    public void onPressButtonO() {
        this.updatePower(this.powerIncrement);
    }

    public static int getTimeScore() {
    	return (int) timeTotal;
    }

    public static int getMaxSpeedComboScore() {
        return (int) TrialSceneRun.maxCombo;
    }

    public static int getMaxSpeedComboTotalScore() {
        return (int) TrialSceneRun.comboTotal;
    }

    public static int getMaxSpeedScore() {
    	return maxSpeed;
    }

    public static int getScore() {
        return score;
    }

    public static int getStamp(int score) {
        int stamp = ResultWinScene.STAMP_THUG;
        if(score >= 20)
            stamp = ResultWinScene.STAMP_GRAND_MASTER;
        else if(score >= 15)
            stamp = ResultWinScene.STAMP_NINJA_MASTER;
        else if(score >= 10)
            stamp = ResultWinScene.STAMP_NINJA;

        return stamp;
    }
}
