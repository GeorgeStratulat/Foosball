package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String another_sql = "CREATE TABLE teams (ID int(55) PRIMARY KEY AUTO_INCREMENT, team_name VARCHAR(255), player1_name VARCHAR(255), player1_date VARCHAR(255), player1_email VARCHAR(255), player2_name VARCHAR(255), player2_date VARCHAR(255), player2_email VARCHAR(255), winner INT(55))";
            con.createStatement().executeUpdate(another_sql);
            String ano_sql = "CREATE TABLE players (ID int(55) PRIMARY KEY AUTO_INCREMENT, player_name VARCHAR(255), player_team VARCHAR(255), player_date VARCHAR(255), player_email VARCHAR(255), player_goals INT(55))";
            con.createStatement().executeUpdate(ano_sql);

            con.close();
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

            String sql = "INSERT INTO teams VALUES (NULL, '"+name_Team+"', '"+name_Player1+"', '"+date_Player1+"', '"+email_Player1+"', '"+name_Player2+"', '"+date_Player2+"', '"+email_Player2+"', 1)";
            con.createStatement().executeUpdate(sql);
            String ano_sql = "INSERT INTO players VALUES(NULL, '"+name_Player1+"', '"+name_Team+"', '"+date_Player1+"', '"+email_Player1+"', 0)";
            con.createStatement().executeUpdate(ano_sql);
            String another_sql = "INSERT INTO players VALUES(NULL, '"+name_Player2+"', '"+name_Team+"', '"+date_Player2+"', '"+email_Player2+"', 0)";
            con.createStatement().executeUpdate(another_sql);

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

    }

    @FXML
    private void generateMatches(ActionEvent event) throws  Exception{

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("General Window.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private Button add_Player;
    @FXML
    private Button load_Player;
    @FXML
    private Button edit_Player;
    @FXML
    private Button save_Player;
    @FXML
    private Button delete_Player;
    @FXML
    private TextField name_Player;
    @FXML
    private TextField date_Player;
    @FXML
    private TextField email_Player;
    @FXML
    private TextField team_Player;
    @FXML
    private TableColumn<Players, String> columnName;
    @FXML
    private TableColumn<Players, String> columnTeam;
    @FXML
    private TableColumn<Players, String> columnEmail;
    @FXML
    private TableColumn<Players, String> columnDate;
    @FXML
    private TableColumn<Players, String> columnGoals;
    @FXML
    private javafx.scene.control.TableView<Players> tableSt;

    private ObservableList<Players> data;

    @FXML
    private void add_Player(ActionEvent event) throws FileNotFoundException {

        String name = name_Player.getText();
        String email = date_Player.getText();
        String date = email_Player.getText();
        String team = team_Player.getText();

        try {
            String sql = "INSERT INTO players VALUES ( NULL, '"+name+"', '"+team+"', '" +date+ "', '" +email+ "', 0);";
            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            String ano_sql = "INSERT INTO players(player_team) SELECT team_name FROM teams";
            stmt.executeUpdate(ano_sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name_Player.setText("");
        date_Player.setText("");
        email_Player.setText("");

    }


    @FXML
    private void loadMySql(javafx.event.ActionEvent event) throws FileNotFoundException {
        try {
            Connection con = getConnection();
            data = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()) {
                data.add(new Players(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("team"));
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Players, String>("email"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Players, String>("goals"));
        tableSt.setItems(null);
        tableSt.setItems(data);

    }

    @FXML
    private void deleteSql(ActionEvent event) throws FileNotFoundException {
        ObservableList<Players> playersSelected, allPlayers;
        allPlayers = tableSt.getItems();
        playersSelected = tableSt.getSelectionModel().getSelectedItems();
        Players players = tableSt.getSelectionModel().getSelectedItem();

        try {
            Connection con = getConnection();

            con.createStatement().executeUpdate("DELETE FROM players WHERE id=" + players.getId());

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        playersSelected.forEach(allPlayers::remove);


    }

    @FXML
    private void editSql(ActionEvent event) throws FileNotFoundException {

        Players player;
        player = tableSt.getSelectionModel().getSelectedItem();
        name_Player.setText(player.getName());
        date_Player.setText(player.getEmail());
        email_Player.setText(player.getEmail());
        team_Player.setText(player.getTeam());

    }

    @FXML
    private Button saveEdit_button;

    @FXML
    private void saveEdit(ActionEvent event) throws FileNotFoundException {

        Players player = tableSt.getSelectionModel().getSelectedItem();
        try {
            Connection con = getConnection();


            String sql = "UPDATE players SET player_name='" + name_Player.getText() + "', player_team='" + team_Player.getText() + "', player_date='" + date_Player.getText() + "',player_email='" +email_Player+"', player_goals=0 WHERE id = " + player.getId();
            con.createStatement().executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }

}
