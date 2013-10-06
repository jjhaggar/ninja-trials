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

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.test.TestingScene;

import android.view.KeyEvent;


public class NinjaTrials extends BaseGameActivity {

    // Resolución de la cámara:
    private static final int WIDTH = 1920; // Ouya res.
    private static final int HEIGHT = 1080; // Ouya res.
    private static final float RATIO = 16 / 9f; // Ouya res.

    // La cámara:
    private Camera mCamera;

    /*
     * Opciones para el motor: - Política de ratio 16/9. - Landscape fixed -
     * Sonido: sí - Música: sí
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        // Camara:
        mCamera = new Camera(0, 0, WIDTH, HEIGHT);

        // Opciones de engine:
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(RATIO), mCamera);

        // Pantalla encendida siempre:
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        // Música:
        engineOptions.getAudioOptions().setNeedsMusic(true);

        // Sonido:
        engineOptions.getAudioOptions().setNeedsSound(true);

        // Return the engineOptions object, passing it to the engine
        return engineOptions;
    }

    // Engine de Máximo FPS = 60;
    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        return new LimitedFPSEngine(pEngineOptions, 60);
    }

    /*
     * Creates Initial Resources
     */
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
        // Setup de ResourceManager.
        ResourceManager.setup(this, this.getEngine(),
                this.getApplicationContext(), WIDTH, HEIGHT);

        // Iniciamos la puntuación, fase, etc
        GameManager.resetGame();

        // Se crea el fichero de datos del usuario si no existe:
        UserData.init(ResourceManager.getInstance().context);

        // Cargamos las fuentes:
        ResourceManager.getInstance().loadFonts(
                ResourceManager.getInstance().engine);

        // Recursos del HUD:
        ResourceManager.getInstance().loadHUDResources();
        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        // No necesitamos ninguna escena
        pOnCreateSceneCallback.onCreateSceneFinished(null);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
        // Iniciamos a la primera escena:
        SceneManager.getInstance().showScene(new TestingScene());
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    // CONTINUAR juego:
    @Override
    public synchronized void onResumeGame() {
        if (ResourceManager.getInstance().music != null
                && !ResourceManager.getInstance().music.isPlaying()) {
            ResourceManager.getInstance().music.play();
        }
        super.onResumeGame();
    }

    // Juego en PAUSA:
    @Override
    public synchronized void onPauseGame() {
        if (ResourceManager.getInstance().music != null
                && ResourceManager.getInstance().music.isPlaying()) {
            ResourceManager.getInstance().music.pause();
        }
        super.onPauseGame();
    }

    /*
     * // Liberamos la memoria: public void onUnloadResources () {
     *
     * ResourceManager.getInstance().unloadFonts();
     *
     *
     * }
     */

    // Esto finaliza el juego:
    /*
     * // Some devices do not exit the game when the activity is destroyed. //
     * This ensures that the game is closed.
     *
     * @Override protected void onDestroy() { super.onDestroy(); System.exit(0);
     * }
     */
/*    @Override
    public void onBackPressed() {
        // If the resource manager has been setup...
        if (ResourceManager.getInstance().engine != null) {

            SceneManager.getInstance().mCurrentScene.onHideManagedScene();
            SceneManager.getInstance().mCurrentScene.onUnloadManagedScene();

            ResourceManager.getInstance().unloadHUDResources();
            ResourceManager.getInstance().unloadFonts();

            System.exit(0);
        }
    }*/
    
    /**
     * Detects if any key is pressed, and send the keyevent information to the scene.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(SceneManager.getInstance().mCurrentScene != null)
            return SceneManager.getInstance().mCurrentScene.onKeyDown(keyCode, event);
        return false;
    }
    
    // TODO controlar la salida del programa (dentro de onkeyDown)
}
