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
