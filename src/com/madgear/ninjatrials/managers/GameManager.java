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

import com.madgear.ninjatrials.ResultTrial;
import com.madgear.ninjatrials.achievements.AchievementSetNinjaTrial;


public class GameManager {

    //private static GameManager INSTANCE;

    // GENERAL:
    // Use DEBUG_MODE = true for show the testing scene.
    public static final boolean DEBUG_MODE = true;

    private static final int SCORE_INIT = 0;
    public static final int LIVES_INIT = 5;

    public static final int CHAR_RYOKO = 0;
    public static final int CHAR_SHO = 1;
    
    public static final int PLAYER_LEFT = 0;
    public static final int PLAYER_RIGHT = 1;
    
    public static final int DIFF_EASY = 0;
    public static final int DIFF_MEDIUM = 1;
    public static final int DIFF_HARD = 2;
    
    public static final int TRIAL_RUN = 1;
    public static final int TRIAL_CUT = 2;
    public static final int TRIAL_JUMP = 3;
    public static final int TRIAL_SHURIKEN = 4;
    public static final int TRIAL_FINAL = TRIAL_SHURIKEN;
    public static final int TRIAL_START = TRIAL_RUN;
    


    public static ResultTrial player1result;
    public static ResultTrial player2result;

    public static AchievementSetNinjaTrial player1achiev;
    public static AchievementSetNinjaTrial player2achiev;

    
    private static int score;
    private static int currentTrial;
    private static int lives;
    private static int selectedCharacter;
    private static int selectedDiff;
    private static String gameLanguage;




/*    // Contructor:
    GameManager(){
    }

    public static GameManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }*/

    
    // Métodos:

    public static void resetGame(){
        score = SCORE_INIT;
        lives = LIVES_INIT;
        currentTrial = TRIAL_START;
        selectedCharacter = CHAR_SHO;
        selectedDiff = DIFF_MEDIUM;
        gameLanguage = "en";
        player1result = new ResultTrial();
        player2result = new ResultTrial();
        player1achiev = new AchievementSetNinjaTrial();
        player2achiev = new AchievementSetNinjaTrial();
    }

    public static void setSelectedCharacter(int c) {
        selectedCharacter = c;
    }
    
    public static int getSelectedCharacter() {
        return selectedCharacter;
    }

    public static void setSelectedDiff(int d) {
        selectedDiff = d;
    }
    
    public static int getSelectedDiff() {
        return selectedDiff;
    }

    public static int getCurrentScore(){
        return score;
    }

    public static String getGameLanguage() {
        return gameLanguage;
    }

    public static void incrementScore(int pIncrementBy){
        score += pIncrementBy;
    }

    public static int getCurrentTrial() {
        return currentTrial;
    }
    
    public static void setCurrentTrial(int t) {
        currentTrial = t;
    }
    
    public static int nextTrial(int currentTrial) {
        int nextTrial = TRIAL_RUN;
        switch(currentTrial) {
        case TRIAL_RUN: nextTrial = TRIAL_CUT; break;
        case TRIAL_CUT: nextTrial = TRIAL_JUMP; break;
        case TRIAL_JUMP: nextTrial = TRIAL_SHURIKEN; break;
        case TRIAL_SHURIKEN: nextTrial = TRIAL_RUN; break;
        }
        return nextTrial;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int t) {
        lives = t;
    }
}
