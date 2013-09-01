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

import org.andengine.entity.scene.Scene;

import android.view.KeyEvent;


public abstract class ManagedScene extends Scene {
    // Tells the Scene Manager that the managed scene either has or doesn't have a loading screen.
    public final boolean hasLoadingScreen;
    // The minimum length of time (in seconds) that the loading screen should be displayed.
    public final float minLoadingScreenTime;
    // Keeps track of how long the loading screen has been visible. Set by the SceneManager.
    public float elapsedLoadingScreenTime = 0f;
    // Is set TRUE if the scene is loaded.
    public boolean isLoaded = false;

    // Convenience constructor that disables the loading screen.
    public ManagedScene() {
        this(0f);
    }

    // Constructor that sets the minimum length of the loading screen and sets hasLoadingScreen
    // accordingly.
    public ManagedScene(final float pLoadingScreenMinimumSecondsShown) {
        minLoadingScreenTime = pLoadingScreenMinimumSecondsShown;
        hasLoadingScreen = (minLoadingScreenTime > 0f);
    }

    // Called by the Scene Manager. It calls onLoadScene if loading is needed, sets the isLoaded
    // status, and pauses the scene while it's not shown.
    public void onLoadManagedScene() {
        if(!isLoaded) {
            onLoadScene();
            isLoaded = true;
            this.setIgnoreUpdate(true);
        }
    }

    // Called by the Scene Manager. It calls onUnloadScene if the scene has been previously loaded
    // and sets the isLoaded status.
    public void onUnloadManagedScene() {
        if(isLoaded) {
            isLoaded = false;
            onUnloadScene();
        }
    }

    // Called by the Scene Manager. It unpauses the scene before showing it.
    public void onShowManagedScene() {
        this.setIgnoreUpdate(false);
        onShowScene();
    }

    // Called by the Scene Manager. It pauses the scene before hiding it.
    public void onHideManagedScene() {
        this.setIgnoreUpdate(true);
        onHideScene();
    }

    // Methods to Override in the subclasses.
    public abstract Scene onLoadingScreenLoadAndShown();
    public abstract void onLoadingScreenUnloadAndHidden();
    public abstract void onLoadScene();
    public abstract void onShowScene();
    public abstract void onHideScene();
    public abstract void onUnloadScene();
    
    // Added to control volume keys:
    public abstract boolean onKeyDown(int keyCode, KeyEvent event);
}
