package sample;

import javafx.beans.property.*;

/**
 * Created by George Stratulat on 30/03/2017.
 */
public class Tournament {
    private final StringProperty name_Of_Tournament;
    private final StringProperty prize_Of_Tournament;
    private final StringProperty date_To_Start;
    private final StringProperty date_To_Finish;

    public Tournament(String name_Of_Tournament, String prize_Of_Tournament, String date_To_Start, String date_To_Finish){

        this.name_Of_Tournament = new SimpleStringProperty(name_Of_Tournament);
        this.prize_Of_Tournament = new SimpleStringProperty(prize_Of_Tournament);
        this.date_To_Start = new SimpleStringProperty(date_To_Start);
        this.date_To_Finish = new SimpleStringProperty(date_To_Finish);
    }


    public String getNameOfTournament(){

        return name_Of_Tournament.get();
    }

    public void setNameOfTournament(String value){

        name_Of_Tournament.set(value);
    }

    public StringProperty nameProperty(){

        return name_Of_Tournament;
    }

}
