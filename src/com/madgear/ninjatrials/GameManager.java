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


public class GameManager {

    private static GameManager INSTANCE;

    // GENERAL:
    private static final int INITIAL_SCORE = 0;
    public static final int CHAR_RYOKO = 0;
    public static final int CHAR_SHO = 1;
    public static final int DIFF_EASY = 0;
    public static final int DIFF_MEDIUM = 1;
    public static final int DIFF_HARD = 2;

    // VALORES INICIALES:
    private int currentScore = INITIAL_SCORE;
    private int selectedCharacter = CHAR_SHO;    // Personaje seleccionado en el juego.
    private int selectedDiff = DIFF_MEDIUM;        // Dificultad por defecto.
    private String gameLanguage = "en";

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

    public void resetGame(){
        currentScore = INITIAL_SCORE;
        selectedCharacter = CHAR_SHO;
        selectedDiff = DIFF_MEDIUM;
    }
}
