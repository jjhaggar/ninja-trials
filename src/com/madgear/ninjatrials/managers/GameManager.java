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


public class GameManager {

    private static GameManager INSTANCE;

    // GENERAL:
    private static final int INITIAL_SCORE = 0;
    public static final int CHAR_RYOKO = 0;
    public static final int CHAR_SHO = 1;
    public static final int DIFF_EASY = 0;
    public static final int DIFF_MEDIUM = 1;
    public static final int DIFF_HARD = 2;
    public static final int TRIAL_RUN = 1;
    public static final int TRIAL_CUT = 2;
    public static final int TRIAL_SHURIKEN = 3;
    public static final int TRIAL_JUMP = 4;
    public static final int LIVES_INIT = 5;

    private int currentScore;
    private int currentTrial;
    private int lives;
    private int selectedCharacter;
    private int selectedDiff;
    private String gameLanguage;
    private float soundVolume;
    private float musicVolume;

    // Contructor:
    GameManager(){
    }

    public static GameManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    // Métodos:

    public int getSelectedCharacter() {
        return selectedCharacter;
    }

    public int getSelectedDiff() {
        return selectedDiff;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public String getGameLanguage() {
        return gameLanguage;
    }

    public void incrementScore(int pIncrementBy){
        currentScore += pIncrementBy;
    }
    
    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float v) {
        soundVolume = v;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float v) {
        musicVolume = v;
    }

    public int getCurrentTrial() {
        return currentTrial;
    }
    
    public void setCurrentTrial(int t) {
        currentTrial = t;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int t) {
        lives = t;
    }

    public void resetGame(){
        currentScore = INITIAL_SCORE;
        currentTrial = TRIAL_RUN;
        lives = LIVES_INIT;
        selectedCharacter = CHAR_SHO;
        selectedDiff = DIFF_MEDIUM;
        gameLanguage = "en";
        setSoundVolume(1f);
        setMusicVolume(1f);
    }
}
