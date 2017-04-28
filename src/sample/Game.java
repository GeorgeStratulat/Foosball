package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.*;
import javafx.beans.property.StringProperty;

/**
 * Created by Constantine on 4/27/2017.
 */
public class Game {
    private final IntegerProperty gameId;
    private final IntegerProperty firstTeam;
    private final IntegerProperty secondTeam;
    private final IntegerProperty firstTeamScore;
    private final IntegerProperty secondTeamScore;
    private StringProperty Score;
    private StringProperty firstTeamName;
    private StringProperty secondTeamName;

    public Game(int gameId, int firstTeam, int secondTeam, int firstTeamScore, int secondTeamScore, String firstTeamName, String secondTeamName) {
        this.gameId = new SimpleIntegerProperty(gameId);
        this.firstTeam = new SimpleIntegerProperty(firstTeam);
        this.secondTeam = new SimpleIntegerProperty(secondTeam);
        this.firstTeamScore = new SimpleIntegerProperty(firstTeamScore);
        this.secondTeamScore = new SimpleIntegerProperty(secondTeamScore);
        this.Score = new SimpleStringProperty("0-0");
        this.firstTeamName = new SimpleStringProperty(firstTeamName);
        this.secondTeamName = new SimpleStringProperty(secondTeamName);
    }

    public String getFirstTeamName() {
        return firstTeamName.get();
    }

    public StringProperty firstTeamNameProperty() {
        return firstTeamName;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName.set(firstTeamName);
    }

    public String getSecondTeamName() {
        return secondTeamName.get();
    }

    public StringProperty secondTeamNameProperty() {
        return secondTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName.set(secondTeamName);
    }

    public String getScore() {
        return getFirstTeamScore() + "-" + getSecondTeamScore();
    }

    public StringProperty scoreProperty() {
        return Score;
    }

    public void setScore(String score) {
        this.Score.set(score);
    }

    public int getGameId() {
        return gameId.get();
    }

    public IntegerProperty gameIdProperty() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId.set(gameId);
    }

    public int getFirstTeam() {
        return firstTeam.get();
    }

    public IntegerProperty firstTeamProperty() {
        return firstTeam;
    }

    public void setFirstTeam(int firstTeam) {
        this.firstTeam.set(firstTeam);
    }

    public int getSecondTeam() {
        return secondTeam.get();
    }

    public IntegerProperty secondTeamProperty() {
        return secondTeam;
    }

    public void setSecondTeam(int secondTeam) {
        this.secondTeam.set(secondTeam);
    }

    public int getFirstTeamScore() {
        return firstTeamScore.get();
    }

    public IntegerProperty firstTeamScoreProperty() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(int firstTeamScore) {
        this.firstTeamScore.set(firstTeamScore);
    }

    public int getSecondTeamScore() {
        return secondTeamScore.get();
    }

    public IntegerProperty secondTeamScoreProperty() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(int secondTeamScore) {
        this.secondTeamScore.set(secondTeamScore);
    }
}
