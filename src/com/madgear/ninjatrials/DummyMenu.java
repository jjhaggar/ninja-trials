package com.madgear.ninjatrials;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * This is a testing class for loading the Trial Scene.
 * @author Madgear Games
 *
 */
public class DummyMenu extends GameScene {
    public DummyMenu() {
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

    @Override
    public void onLoadScene() {}

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0.5f, 0.3f, 0.2f);
        final Text loadingText = new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.5f,
                ResourceManager.getInstance().fontMedium, "Press O for loading TrialSceneCut",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        this.attachChild(loadingText);
    }

    @Override
    public void onHideScene() {}

    @Override
    public void onUnloadScene() {}
    
    /**
     * If we press the O button go to the Cut Trial Scene.
     */
    @Override
    public void onPressButtonO() {
        SceneManager.getInstance().showScene(new MainMenuScene());
    }
}
