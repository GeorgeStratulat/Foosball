package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static sample.DBConnection.getConnection;

public class Controller {

    @FXML
    private Button create_New_Tournament;
    @FXML
    private TextField name_Tournament;
    @FXML
    private TextField prize_Tournament;
    @FXML
    private TextField date_To_Start;
    @FXML
    private TextField date_To_Finish;
    @FXML
    private AnchorPane rootPane1;
    @FXML
    private Button generate_New_Tournament;
    @FXML
    private Button save_New_Team;
    @FXML
    private TextField new_Team_Name;
    @FXML
    private TextField new_Player1_Name;
    @FXML
    private TextField new_Player1_Date;
    @FXML
    private TextField new_Player1_Email;
    @FXML
    private TextField new_Player2_Name;
    @FXML
    private TextField new_Player2_Date;
    @FXML
    private TextField new_Player2_Email;

    String Name_Tournament;

    @FXML
    private void createNewTournament(ActionEvent event) throws Exception {

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Create Tournament.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void generateNewTournament(ActionEvent event) throws Exception {

        try {
            Connection con = getConnection();
            String name = name_Tournament.getText();
            String databaseName = name+"players";
            String prize = prize_Tournament.getText();
            String dateStart = date_To_Start.getText();
            String dateFinish = date_To_Finish.getText();
            String sql = "INSERT INTO tournaments VALUES (NULL, '"+name+"', '"+prize+"', '"+dateStart+"', '"+dateFinish+"')";
            con.createStatement().executeUpdate(sql);
            String another_sql = "CREATE TABLE "+databaseName+" (ID int NOT NULL AUTO_INCREMENT, team_name VARCHAR(255), player1_name VARCHAR(255), player1_date VARCHAR(255), player1_email VARCHAR(255), player2_name VARCHAR(255), player2_date VARCHAR(255), player2_email VARCHAR(255), PRIMARY KEY (ID))";
            con.createStatement().executeUpdate(another_sql);
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Create Teams.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void saveNewTeam(ActionEvent event) throws Exception{

        try {
            Connection con = getConnection();
            String databasename = Name_Tournament+"players";
            String name_Team = new_Team_Name.getText();
            String name_Player1 = new_Player1_Name.getText();
            String date_Player1 = new_Player1_Date.getText();
            String email_Player1 = new_Player1_Email.getText();
            String name_Player2 = new_Player2_Name.getText();
            String date_Player2 = new_Player2_Date.getText();
            String email_Player2 = new_Player2_Email.getText();

            String sql = "INSERT INTO "+databasename+" VALUES (NULL, '"+name_Team+"', '"+name_Player1+"', '"+date_Player1+"', '"+email_Player1+"', '"+name_Player2+"', '"+date_Player2+"', '"+email_Player2+"')";
            con.createStatement().executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

    }

    @FXML
    private void generateMatches(ActionEvent event) throws  Exception{}
}
