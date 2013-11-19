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

package com.madgear.ninjatrials.test;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.scene.Scene;

import android.util.Log;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;

public class DataLoadAndSaveTestScene extends GameScene {

    private Music cutMusic;

    
    @Override
    public Scene onLoadingScreenLoadAndShown() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLoadScene() {
        // Sonido:
        SoundFactory.setAssetBasePath("sounds/");
        MusicFactory.setAssetBasePath("music/");
        try {
            cutMusic = MusicFactory.createMusicFromAsset(
                    ResourceManager.getInstance().activity.getMusicManager(),
                    ResourceManager.getInstance().context,
                    "trial_cut_music.ogg");
        } catch (final IOException e) {
            Log.v("Sounds Load","Exception: " + e.getMessage());
            e.printStackTrace();
        }        
    }

    @Override
    public void onShowScene() {
        this.getBackground().setColor(0.5f, 0.3f, 0.9f);
        if(cutMusic != null && !cutMusic.isPlaying())
            cutMusic.play();
    }

    @Override
    public void onHideScene() {        
    }

    @Override
    public void onUnloadScene() {
        if(cutMusic != null)
            if(!cutMusic.isReleased())
                cutMusic.release();
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
