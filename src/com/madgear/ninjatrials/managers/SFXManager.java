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

package com.madgear.ninjatrials.managers;

import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;

public class SFXManager {
    
    public static void playSound(Sound s) {
        s.setVolume(GameManager.getSoundVolume());
        s.play();
    }
    
    public static void playMusic(Music m) {
        if(m != null && !m.isPlaying()) {
            m.setVolume(GameManager.getMusicVolume());
            m.play();
        }
    }
    
    public static void pauseMusic(Music m) {
        if(m != null && m.isPlaying())
            m.pause();
    }
    
    public static void resumeMusic (Music m) {
        m.setVolume(GameManager.getMusicVolume());
        m.resume();
    }
}
