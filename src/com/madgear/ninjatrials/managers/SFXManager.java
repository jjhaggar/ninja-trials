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
    private static float soundVolume = 0.7f;
    private static float musicVolume = 0.7f;
    
    
    // Sound control ------------------------------
    
    public static void playSound(Sound s) {
        if(s != null && s.isLoaded()) {
            s.setVolume(getSoundVolume());
            s.play();
        }
    }
    
    public static void playSoundLoop(Sound s) {
        if(s != null && s.isLoaded()) {
            s.setVolume(getSoundVolume());
            s.setLooping(true);
            s.play();
        }
    }
    
    public static void stopSound(Sound s) {
        if(s != null && s.isLoaded()) {
            s.stop();
        }
    }
    
    public static float getSoundVolume() {
        return soundVolume;
    }

    public static void setSoundVolume(float v) {
        soundVolume = v;
    }
    
    
    public static void addSoundVolume(float v) {
        soundVolume += v;
        if(soundVolume > 1) soundVolume = 1;
        if(soundVolume < 0) soundVolume = 0;
    }

    
    // Music Control --------------------------------
    
    /**
     * Begins playing music.
     * @param m
     */
    public static void playMusic(Music m) {
        if(m != null && !m.isPlaying()) {
            m.setVolume(getMusicVolume());
            m.play();
        }
    }
    
    /**
     * Play music in looping.
     * @param m
     */
    public static void playMusicLoop(Music m) {
        if(m != null && !m.isPlaying()) {
            m.setVolume(getMusicVolume());
            m.setLooping(true);
            m.play();
        }
    }
    
    public static boolean isPlaying(Music m) {
        return m.isPlaying();
    }
    
    /**
     * Pause music. May be resumed.
     * @param m
     */
    public static void pauseMusic(Music m) {
        if(m != null && m.isPlaying())
            m.pause();
    }
    
    /**
     * Stops music and sets the tune to the begining.
     * @param m
     */
    public static void stopMusic(Music m) {
        if(m != null && m.isPlaying()) {
            m.pause();
            m.seekTo(0);
        }
    }
    
    /**
     * Resume Music (works with pauseMusic).
     * @param m
     */
    public static void resumeMusic(Music m) {
        m.setVolume(getMusicVolume());
        m.resume();
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(float v) {
        musicVolume = v;
    }
    
    public static void addMusicVolume(float v) {
        musicVolume += v;
        if(musicVolume > 1) musicVolume = 1;
        if(musicVolume < 0) musicVolume = 0;
    }
}
