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

    private ObservableList<Players> dataPlayers;
    private ObservableList<Teams> dataTeams;
    @FXML
    private Button addTeam_button;
    @FXML
    private Button loadTeam_button;
    @FXML
    private Button deleteTeam_button;

    @FXML
    private void addPlayer(ActionEvent event) throws FileNotFoundException {

        String name = name_Player.getText();
        String email = date_Player.getText();
        String date = email_Player.getText();
        String team = team_Player.getText();
        String databasename = GlobalVariable.nameOfTournamentGlobal+"players";

        try {
            String sql = "INSERT INTO "+databasename+" VALUES ( NULL, '"+name+"', '"+team+"', '" +date+ "', '" +email+ "', 0);";
            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name_Player.setText("");
        date_Player.setText("");
        email_Player.setText("");

    }


    @FXML
    private void loadPlayer(javafx.event.ActionEvent event) throws FileNotFoundException {
        try {
            String databasename = GlobalVariable.nameOfTournamentGlobal+"players";
            Connection con = getConnection();
            dataPlayers = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM '"+databasename+"'");
            while (rs.next()) {
                dataPlayers.add(new Players(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("team"));
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Players, String>("email"));
        columnGoals.setCellValueFactory(new PropertyValueFactory<Players, String>("goals"));
        tableSt.setItems(null);
        tableSt.setItems(dataPlayers);

    }

    @FXML
    private void deletePlayer(ActionEvent event) throws FileNotFoundException {
        String databasename = GlobalVariable.nameOfTournamentGlobal+"players";
        ObservableList<Players> playersSelected, allPlayers;
        allPlayers = tableSt.getItems();
        playersSelected = tableSt.getSelectionModel().getSelectedItems();
        Players players = tableSt.getSelectionModel().getSelectedItem();

        try {
            Connection con = getConnection();

            con.createStatement().executeUpdate("DELETE FROM '"+databasename+"' WHERE id=" + players.getId());

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        playersSelected.forEach(allPlayers::remove);


    }

    @FXML
    private void editPlayer(ActionEvent event) throws FileNotFoundException {

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
    private void saveEdit_Player(ActionEvent event) throws FileNotFoundException {

        Players player = tableSt.getSelectionModel().getSelectedItem();
        try {
            String databasename = GlobalVariable.nameOfTournamentGlobal+"players";
            Connection con = getConnection();


            String sql = "UPDATE '"+databasename+"' SET player_name='" + name_Player.getText() + "', player_team='" + team_Player.getText() + "', player_date='" + date_Player.getText() + "',player_email='" +email_Player+"', player_goals=0 WHERE id = " + player.getId();
            con.createStatement().executeUpdate(sql);

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }

    @FXML
    private TableColumn<Teams, String> columnNameTeam;
    @FXML
    private TableColumn<Teams, String> columnPlayer1Name;
    @FXML
    private TableColumn<Teams, String> columnPlayer2Name;
    @FXML
    private TableColumn<Teams, String> columnTeamGoals;
    @FXML
    private javafx.scene.control.TableView<Teams> tableTeams;

    @FXML
    private void addTeam(ActionEvent event) throws Exception{

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Create Teams.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void saveNewTeam(ActionEvent event) throws Exception{

        try {
            Connection con = getConnection();
            String name_Team = new_Team_Name.getText();
            String name_Player1 = new_Player1_Name.getText();
            String date_Player1 = new_Player1_Date.getText();
            String email_Player1 = new_Player1_Email.getText();
            String name_Player2 = new_Player2_Name.getText();
            String date_Player2 = new_Player2_Date.getText();
            String email_Player2 = new_Player2_Email.getText();
            String databasename1 = GlobalVariable.nameOfTournamentGlobal+"players";
            String databasename2 = GlobalVariable.nameOfTournamentGlobal+"teams";
            int id_Player1 = 0, id_Player2 = 0 , id_Tournament = 0;

            String ano_sql = "INSERT INTO "+databasename1+" VALUES(NULL, '"+name_Player1+"', '"+name_Team+"', '"+date_Player1+"', '"+email_Player1+"', 0)";
            con.createStatement().executeUpdate(ano_sql);
            ResultSet rs1 = con.createStatement().executeQuery("SELECT * FROM "+databasename1+" ORDER BY id DESC LIMIT 1");
            if(rs1.next())
            id_Player1 = rs1.getInt(1);
            String another_sql = "INSERT INTO "+databasename1+" VALUES(NULL, '"+name_Player2+"', '"+name_Team+"', '"+date_Player2+"', '"+email_Player2+"', 0)";
            con.createStatement().executeUpdate(another_sql);
            ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM "+databasename1+" ORDER BY id DESC LIMIT 1");
            if(rs2.next())
            id_Player2 = rs2.getInt(1);
            ResultSet rs3 = con.createStatement().executeQuery("SELECT id FROM tournaments WHERE name='"+GlobalVariable.nameOfTournamentGlobal+"'");
            if(rs3.next())
            id_Tournament = rs3.getInt(1);
            String sql = "INSERT INTO "+databasename2+" VALUES (NULL, '"+name_Team+"', '"+id_Player1+"', '"+id_Player2+"', '"+name_Player1+"', '"+name_Player2+"', '"+id_Tournament+"', 0, 1)";
            con.createStatement().executeUpdate(sql);

            con.close();

            new_Team_Name.setText("");
            new_Player1_Name.setText("");
            new_Player1_Date.setText("");
            new_Player1_Email.setText("");
            new_Player2_Name.setText("");
            new_Player2_Date.setText("");
            new_Player2_Email.setText("");

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

    }



    @FXML
    private void deleteTeam(ActionEvent event) throws FileNotFoundException {

        String databasename = GlobalVariable.nameOfTournamentGlobal+"teams";
        ObservableList<Teams> teamsSelected, allTeams;
        allTeams = tableTeams.getItems();
        teamsSelected = tableTeams.getSelectionModel().getSelectedItems();
        Teams teams = tableTeams.getSelectionModel().getSelectedItem();

        try {
            Connection con = getConnection();

            con.createStatement().executeUpdate("DELETE FROM '"+databasename+"' WHERE id=" + teams.getIdOfTeam());

            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        teamsSelected.forEach(allTeams::remove);
    }

    @FXML
    private void loadTeam(ActionEvent event) throws FileNotFoundException {

        try {
            String databasename = GlobalVariable.nameOfTournamentGlobal+"teams";
            Connection con = getConnection();
            dataTeams = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM '"+databasename+"'");
            while (rs.next()) {
                dataTeams.add(new Teams(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        columnNameTeam.setCellValueFactory(new PropertyValueFactory<>("name_Of_Team"));
        columnPlayer1Name.setCellValueFactory(new PropertyValueFactory<>("player1_name"));
        columnPlayer2Name.setCellValueFactory(new PropertyValueFactory<>("player2_name"));
        columnTeamGoals.setCellValueFactory(new PropertyValueFactory<>("team_goals"));
        tableTeams.setItems(null);
        tableTeams.setItems(dataTeams);

    }

    @FXML
    private TextField match1team1;
    @FXML
    private TextField match1team2;
    @FXML
    private TextField match2team1;
    @FXML
    private TextField match2team2;
    @FXML
    private TextField match3team1;
    @FXML
    private TextField match3team2;
    @FXML
    private TextField match4team1;
    @FXML
    private TextField match4team2;
    @FXML
    private TextField matchs1team1;
    @FXML
    private TextField matchs1team2;
    @FXML
    private TextField matchs2team1;
    @FXML
    private TextField matchs2team2;
    @FXML
    private TextField matchfteam1;
    @FXML
    private TextField matchfteam2;

}
