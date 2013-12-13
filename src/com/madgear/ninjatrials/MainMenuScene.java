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
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.SelectionStripe;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.test.TestingScene;
import com.madgear.ninjatrials.trials.TrialSceneJump;

/**
 * Main Menu Scene. Displays a pattern background, a logo, and a selection stripe.
 * @author Madgear Games
 */
public class MainMenuScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private Sprite tittle;
    private SelectionStripe selectionStripe;
    private final String[] menuOptions = {ResourceManager.getInstance().loadAndroidRes().getString(R.string.main_menu_options),
    		ResourceManager.getInstance().loadAndroidRes().getString(R.string.main_menu_play),
    		ResourceManager.getInstance().loadAndroidRes().getString(R.string.main_menu_achievements)};
    private TimerHandler timerHandler;
    private GameHUD gameHUD;
    private static final float GO_CHAR_INFO_TIME = 12f;



    /**
     * MainMenuScene constructor.
     * Loading scene is enabled by default.
     */
    public MainMenuScene() {
        super(1f);
    }
    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        Scene loadingScene = new Scene(); // Provisional, sera una clase externa
        loadingScene.getBackground().setColor(0.3f, 0.3f, 0.6f);
        // Añadimos algo de texto:
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.3f,
                ResourceManager.getInstance().fontBig, ResourceManager.getInstance().loadAndroidRes().getString(R.string.app_loading),
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        loadingScene.attachChild(loadingText);
        return loadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {}

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadMainMenuResources();        
    }

    @SuppressWarnings("static-access")
    @Override
    public void onShowScene() {
        // TODO shading background (from blue to white).
        // Background:
        // Crate the background Pattern Sprite:
        ResourceManager.getInstance().mainTitlePattern1.setTextureSize(WIDTH, HEIGHT);
        Sprite patternSprite = new Sprite(WIDTH/2, HEIGHT/2,
                ResourceManager.getInstance().mainTitlePattern1,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // Add pattern sprite to a new entity:
        Entity backgroundEntity = new Entity();
        backgroundEntity.attachChild(patternSprite);
        // Create a new background from the entity (gray-blue):
        EntityBackground background = new EntityBackground(0.42f, 0.57f, 0.67f, backgroundEntity);
        setBackground(background);

        // NinjaTrials Logo:
        tittle = new Sprite(WIDTH / 2, HEIGHT / 2 + 100,
                ResourceManager.getInstance().mainTitle,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(tittle);
        //tittle.registerEntityModifier(new ScaleModifier(6f, 0.95f, 1.1f));
        
        // Selection Stripe:
        selectionStripe = new SelectionStripe(WIDTH / 2, HEIGHT / 2 - 300, 
                SelectionStripe.DISP_HORIZONTAL, 500f,
                menuOptions, SelectionStripe.TEXT_ALIGN_CENTER, 1);
        attachChild(selectionStripe);
        
        // Go to Character info scene when some time passed and no key is pressed.
        timerHandler = new TimerHandler(GO_CHAR_INFO_TIME , true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                SceneManager.getInstance().showScene(new CharacterIntroScene());
            } 
        });
        registerUpdateHandler(timerHandler);
        
        // HUD:
        gameHUD = new GameHUD();
        ResourceManager.getInstance().engine.getCamera().setHUD(gameHUD);
        
        // Check for achievement 5 (100 hours playing!):
        if(!GameManager.player1achiev.achievements[4].isCompleted()) {
            
            GameManager.gamePlayedTime =
                    ResourceManager.getInstance().engine.getSecondsElapsedTotal() - 
                    GameManager.gameMenuInitTime;
            
            GameManager.gameMenuInitTime =
                    ResourceManager.getInstance().engine.getSecondsElapsedTotal();

            GameManager.player1achiev.achievements[4].
                progressIncrement(Math.round(GameManager.gamePlayedTime / 60));
            
            if(GameManager.player1achiev.achievements[4].isCompleted()) {
                gameHUD.showAchievementCompleted(5);
                GameManager.player1achiev.unlock(5);
            }
            else {
                UserData.saveAchiev(ResourceManager.getInstance().context);
            }
        }
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadMainMenuResources();        
    }

    @Override
    public void onPressDpadLeft() {
        selectionStripe.movePrevious();
        timerHandler.reset(); // Key pressed
    }

    @Override
    public void onPressDpadRight() {
        selectionStripe.moveNext();
        timerHandler.reset();  // Key pressed
    }

    @Override
    public void onPressButtonO() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        case 0:
            SceneManager.getInstance().showScene(new MainOptionsScene());
            break;
        case 1:
            GameManager.newGame();
            SceneManager.getInstance().showScene(new PlayerSelectionScene());
            break;
        case 2:
            SceneManager.getInstance().showScene(new AchievementsScene());
            break;
        }
    }
    
    @Override
    public void onPressButtonMenu() {
        if(GameManager.DEBUG_MODE)
            SceneManager.getInstance().showScene(new TestingScene());
        else {
            GameManager.endGame();
        }
    }
}
