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

import org.andengine.engine.camera.hud.HUD;


public abstract class ManagedLayer extends HUD {
    // Is set TRUE if the layer is loaded.
    public boolean hasLoaded = false;
    // Set by the constructor, if true, the layer will be unloaded after being hidden.
    public boolean unloadOnHidden;

    // Convenience constructor. Creates a layer that does not unload when hidden.
    public ManagedLayer() {
        this(false);
    }

    // Constructor. Sets whether the layer will unload when hidden and ensures that there is no
    // background on the layer.
    public ManagedLayer(boolean pUnloadOnHidden) {
        unloadOnHidden = pUnloadOnHidden;
        this.setBackgroundEnabled(false);
    }

    // If the layer is not loaded, load it. Ensure that the layer is not paused.
    public void onShowManagedLayer() {
        if(!hasLoaded) {
            hasLoaded = true;
            onLoadLayer();
        }
        this.setIgnoreUpdate(false);
        onShowLayer();
    }

    // Pause the layer, hide it, and unload it if it needs to be unloaded.
    public void onHideManagedLayer() {
        this.setIgnoreUpdate(true);
        onHideLayer();
        if(unloadOnHidden) {
            onUnloadLayer();
        }
    }

    // Methods to override in subclasses.
    public abstract void onLoadLayer();
    public abstract void onShowLayer();
    public abstract void onHideLayer();
    public abstract void onUnloadLayer();
}
