package com.dodge.game;

public class ScoreManager {
    private static ScoreManager instance;
    private int highScore = 0;

    private ScoreManager() { }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateHighScore(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
        }
    }
}