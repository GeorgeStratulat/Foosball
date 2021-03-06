package sample;

import javafx.beans.property.*;

/**
 * Created by George Stratulat on 30/03/2017.
 */
public class Players {
    private final IntegerProperty goals;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty team;
    private final IntegerProperty id;
    private final StringProperty birthday;
    private final IntegerProperty teamID;

    public Players(int id, String name, String team, String birthday, String email, int goals, int teamID){
        this.teamID = new SimpleIntegerProperty(teamID);
        this.id = new SimpleIntegerProperty(id);
        this.goals = new SimpleIntegerProperty(goals) ;
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.team = new SimpleStringProperty(team);
        this.birthday = new SimpleStringProperty(birthday);
    }

    public IntegerProperty goalsProperty() {
        return goals;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public int getTeamID() {
        return teamID.get();
    }

    public IntegerProperty teamIDProperty() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID.set(teamID);
    }

    public int getGoals(){return goals.get();}

    public String getName(){
        return name.get();
    }

    public String getEmail()
    {
        return email.get();
    }

    public String getTeam(){return team.get();
    }
    public int getId(){return id.get();}

    public String getBirthday(){return birthday.get();}

    public void setGoals(int value){goals.set(value);}

    public void setName(String value){
        name.set(value);
    }

    public void setEmail(String value){
        email.set(value);
    }

    public void setTeam(String value){
        team.set(value);
    }

    public StringProperty nameProperty(){return name;}
    public StringProperty emailProperty(){return email;}
    public StringProperty teamProperty(){return team;}
    public IntegerProperty goalProperty(){return goals;}
    public IntegerProperty idProperty(){return id;}

}
