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
    private final StringProperty name_Of_Team;
    private final IntegerProperty player1_id;
    private final IntegerProperty player2_id;
    private final IntegerProperty tournament_id;
    private final IntegerProperty winner;
    private final StringProperty player1_name;
    private final StringProperty player2_name;
    private final IntegerProperty team_goals;

    public Teams(int team_id, String name_Of_Team, int player1_id, int player2_id, String player1_name, String player2_name, int tournament_id, int team_goals, int winner) {

        this.team_id = new SimpleIntegerProperty(team_id);
        this.name_Of_Team = new SimpleStringProperty(name_Of_Team);
        this.player1_id= new SimpleIntegerProperty(player1_id);
        this.player2_id = new SimpleIntegerProperty(player2_id);
        this.player1_name = new SimpleStringProperty(player1_name);
        this.player2_name = new SimpleStringProperty(player2_name);
        this.tournament_id = new SimpleIntegerProperty(tournament_id);
        this.team_goals = new SimpleIntegerProperty(team_goals);
        this.winner = new SimpleIntegerProperty(winner);
    }

    public int getIdOfTeam(){

        return team_id.get();
    }

    public String getNameOfTeam() {

        return name_Of_Team.get();
    }

    public void setTeam_id(int value){

        team_id.set(value);
    }

    public void setName_Of_Team(String value) {

        name_Of_Team.set(value);
    }

    public StringProperty nameProperty() {

        return name_Of_Team;
    }

    public IntegerProperty idProperty(){

        return team_id;
    }

    public int getWinner(){

        return winner.get();
    }

    public void setWinner(int value){

        winner.set(value);
    }

    public IntegerProperty winnerProperty(){

        return winner;
    }

    public int getGoalsOfTeam(){

        return team_goals.get();
    }

    public IntegerProperty goalsTeamProperty(){

        return team_goals;
    }
}

