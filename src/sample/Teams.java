package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by George Stratulat on 17/04/2017.
 */
public class Teams {
    private final IntegerProperty team_id;
    private StringProperty player1_name;
    private StringProperty player2_name;
    private final StringProperty team_name;
    private final IntegerProperty tournament_id;
    private int team_winnings;
    private int teamGames;
    private final IntegerProperty team_goals;

    public Teams(int team_id, int tournament_id, String team_name,  int team_goals, int team_winnings, int teamGames) {

        this.team_id = new SimpleIntegerProperty(team_id);
        this.team_name = new SimpleStringProperty(team_name);
        this.tournament_id = new SimpleIntegerProperty(tournament_id);
        this.team_goals = new SimpleIntegerProperty(team_goals);
        this.team_winnings = team_winnings;
        this.teamGames = teamGames;

    }

    public int getTeamGames() {
        return teamGames;
    }

    public void setTeamGames(int c){
        this.teamGames = c;
    }

    public void addFirstPlayer(String playerName){
        this.player1_name = new SimpleStringProperty(playerName);
    }
    public void addSecondPlayer(String playerName){
        this.player2_name = new SimpleStringProperty(playerName);
    }

    public int getTournament_id() {
        return tournament_id.get();
    }

    public IntegerProperty tournament_idProperty() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id.set(tournament_id);
    }

    public String getPlayer1_name() {
        return player1_name.get();
    }

    public StringProperty player1_nameProperty() {
        return player1_name;
    }

    public void setPlayer1_name(String player1_name) {
        this.player1_name.set(player1_name);
    }

    public String getPlayer2_name() {
        return player2_name.get();
    }

    public StringProperty player2_nameProperty() {
        return player2_name;
    }

    public void setPlayer2_name(String player2_name) {
        this.player2_name.set(player2_name);
    }


    public String getTeam_name() {
        return team_name.get();
    }

    public StringProperty team_nameProperty() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name.set(team_name);
    }

    public void setTeam_id(int value){

        team_id.set(value);
    }

    public int getIdOfTeam() {
        return team_id.get();
    }

    public IntegerProperty team_idProperty() {
        return team_id;
    }

    public int getTeam_winnings(){

        return team_winnings;
    }

    public void setTeam_winnings(int value){

        this.team_winnings = value;
    }

    public int winnerProperty(){

        return team_winnings;
    }

    public int getTeam_goals() {
        return team_goals.get();
    }

    public IntegerProperty team_goalsProperty() {
        return team_goals;
    }

    public void setTeam_goals(int team_goals) {
        this.team_goals.set(team_goals);
    }
}

