package com.madgear.ninjatrials;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import com.madgear.ninjatrials.hud.SelectionStripe;

/**
 * Main Menu Scene. Displays a pattern background, a logo, and a selection stripe.
 * @author Madgear Games
 */
public class MainMenuScene extends GameScene {
    private final static float WIDTH = ResourceManager.getInstance().cameraWidth;
    private final static float HEIGHT = ResourceManager.getInstance().cameraHeight;
    private RepeatingSpriteBackground patternBg;
    private Sprite tittle;
    private SelectionStripe selectionStripe;
    private final String[] menuOptions = {"OPTIONS","PLAY","ACHIEVEMENTS"};

    
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

    @Override
    public void onLoadScene() {
        ResourceManager.getInstance().loadMainMenuResources();        
    }

    @SuppressWarnings("static-access")
    @Override
    public void onShowScene() {
        // Background:
        ResourceManager.getInstance().mainTitlePattern1TR.setTextureSize(WIDTH, HEIGHT);
        patternBg = new RepeatingSpriteBackground(WIDTH, HEIGHT,
                ResourceManager.getInstance().mainTitlePattern1TR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        // TODO shading background.
        // TODO bug, el fondo no cambia de color.
        getBackground().setColor(1f, 1f, 0f);
        setBackground(patternBg);
        
        // NinjaTrials Logo:
        tittle = new Sprite(WIDTH / 2, HEIGHT / 2 + 200,
                ResourceManager.getInstance().mainTitleTR,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        attachChild(tittle);
        
        // Selection Stripe:
        selectionStripe = new SelectionStripe(WIDTH / 2, HEIGHT / 2 - 300, menuOptions, 1, 500);
        attachChild(selectionStripe);
    }

    @Override
    public void onHideScene() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUnloadScene() {
        ResourceManager.getInstance().unloadMainMenuResources();        
    }

    @Override
    public void onPressDpadLeft() {
        selectionStripe.moveLeft();
    }
    
    @Override
    public void onPressDpadRight() {
        selectionStripe.moveRight();
    }
    
    @Override
    public void onPressButtonO() {
        int optionIndex = selectionStripe.getSelectedIndex();
        switch(optionIndex) {
        case 0:
            SceneManager.getInstance().showScene(new DummyMenu());
            break;
        case 1:
            SceneManager.getInstance().showScene(new TrialSceneCut());
            break;
        case 2:
            SceneManager.getInstance().showScene(new DummyMenu());
            break;
        }
    }
    
    @Override
    public void onPressButtonMenu() {
        if (ResourceManager.getInstance().engine != null) {
            SceneManager.getInstance().mCurrentScene.onHideManagedScene();
            SceneManager.getInstance().mCurrentScene.onUnloadManagedScene();
            ResourceManager.getInstance().unloadHUDResources();
            ResourceManager.getInstance().unloadFonts();
            System.exit(0);
        }
    }

}