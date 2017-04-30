package sample;

import com.sun.javafx.text.TextLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.*;

import static sample.DBConnection.getConnection;
import static sample.GlobalVariable.selectedTournament;
import static sample.GlobalVariable.selectedGame;

public class Controller {

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
    private TableColumn<Players, String> columnBirthday;
    @FXML
    private TableColumn<Players, String> columnGoals;
    @FXML
    private javafx.scene.control.TableView<Players> tableSt;

    public static ObservableList<Players> dataPlayers;
    public static ObservableList<Teams> dataTeams;
    public static ObservableList<Game> GameData;


    private static final int FETCH_PLAYERS = 1;
    private static final int FETCH_TEAMS = 2;
    private static final int FETCH_GAMES = 3;

    public void FETCH_DATA(int type){
        switch (type){
            /*
            ================================================================
                                LOAD PLAYERS DATA
            ================================================================
            */
            case FETCH_PLAYERS:
                try {
                    Connection con = getConnection();
                    dataPlayers = FXCollections.observableArrayList();

                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `tournament_players` INNER JOIN  `tournament_teams` ON `team_id` = `player_team` WHERE `tournament_id` =" + GlobalVariable.selectedTournament);
                    while (rs.next()) {
                        dataPlayers.add(new Players(rs.getInt(1), rs.getString(2), rs.getString(9), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));

                    }
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error:" + ex);
                }
                break;
            /*
            ================================================================
                                LOAD TEAMS DATA
            ================================================================
             */
            case FETCH_TEAMS:
                try {
                    Connection con = getConnection();
                    dataTeams = FXCollections.observableArrayList();

                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `tournament_teams` WHERE `tournament_id` = " + selectedTournament);
                    while (rs.next()) {
                        Teams team = new Teams(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),  rs.getInt(5),  rs.getInt(6));
                        ResultSet result = con.createStatement().executeQuery("SELECT `player_name` FROM `tournament_players` WHERE `player_team` = " + rs.getInt(1));
                        result.next();
                        team.addFirstPlayer(result.getString(1));
                        result.next();
                        team.addSecondPlayer(result.getString(1));
                        dataTeams.add(team);
                    }
                    con.close();
                } catch (SQLException ex) {
                    System.err.println("Error" + ex);
                }
                break;
            /*
            ================================================================
                                LOAD GAMES DATA
            ================================================================
             */
            case FETCH_GAMES:
                try {
                    Connection connHandle = getConnection();
                    ResultSet r = connHandle.createStatement().executeQuery("SELECT * FROM `tournament_games` WHERE `tournament_id` =" + selectedTournament);
                    GameData = FXCollections.observableArrayList();
                    while(r.next()){

                        int
                                firstTeamScriptID = 0,
                                secondTeamScriptID = 0,
                                fistTeamID = r.getInt(2),
                                secondTeamID = r.getInt(3);


                        for(int i =0; i < dataTeams.size(); i++) {
                            if (dataTeams.get(i).getIdOfTeam() == fistTeamID)
                                firstTeamScriptID = i;
                            else if (dataTeams.get(i).getIdOfTeam() == secondTeamID)
                                secondTeamScriptID = i;
                        }
                        GameData.add(new Game(r.getInt(1), r.getInt(2), r.getInt(3), r.getInt(4), r.getInt(5), dataTeams.get(firstTeamScriptID).getTeam_name(), dataTeams.get(secondTeamScriptID).getTeam_name(), firstTeamScriptID, secondTeamScriptID ));

                    }

                }
                catch (SQLException ex) {
                    System.err.println("Error" + ex);
                }
                break;
        }
    }

    @FXML
    public void loadPlayer() {
        FETCH_DATA(FETCH_PLAYERS);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        columnGoals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        tableSt.setItems(null);
        tableSt.setItems(dataPlayers);
        System.out.println(dataPlayers);
    }


    @FXML
    private void editPlayer(ActionEvent event) throws FileNotFoundException {

        Players player;
        player = tableSt.getSelectionModel().getSelectedItem();
        name_Player.setText(player.getName());
        date_Player.setText(player.getBirthday());
        email_Player.setText(player.getEmail());

    }


    @FXML
    private void saveEdit_Player(ActionEvent event) throws FileNotFoundException {

        Players player = tableSt.getSelectionModel().getSelectedItem();
        try {
            Connection con = getConnection();
            String query = "UPDATE `tournament_players` SET player_name='" + name_Player.getText() + "', player_birthday='" + date_Player.getText() + "',player_email='" +email_Player.getText()+"' WHERE `player_id` = " + player.getId();
            con.createStatement().executeUpdate(query);
            name_Player.setText("");
            date_Player.setText("");
            email_Player.setText("");
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        loadPlayer();
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
            int team_id;

            con.createStatement().executeUpdate("INSERT INTO `tournament_teams` (`tournament_id`, `team_name`) VALUES('"+ selectedTournament +"', '" + name_Team  + "')");
            ResultSet query_result = con.createStatement().executeQuery("SELECT `team_id` FROM `tournament_teams` ORDER BY `team_id` DESC LIMIT 1");
            query_result.next();
            team_id = query_result.getInt(1);
            con.createStatement().executeUpdate("INSERT INTO `tournament_players`(`player_name`,`player_team`, `player_birthday`, `player_email`) VALUES ('" + name_Player1 +"', " + team_id  + ", '" + date_Player1 + "', '" + email_Player1 + "')");
            con.createStatement().executeUpdate("INSERT INTO `tournament_players`(`player_name`,`player_team`, `player_birthday`, `player_email`) VALUES ('" + name_Player2 +"', " + team_id  + ", '" + date_Player2 + "', '" + email_Player2 + "')");
            con.createStatement().executeUpdate("DELETE FROM `tournament_games` WHERE `tournament_id` =" + selectedTournament);


            con.close();

            new_Team_Name.setText("");
            new_Player1_Name.setText("");
            new_Player1_Date.setText("");
            new_Player1_Email.setText("");
            new_Player2_Name.setText("");
            new_Player2_Date.setText("");
            new_Player2_Email.setText("");
            Teams team = new Teams(team_id,selectedTournament,name_Team, 0, 0 ,0);
            team.addFirstPlayer(name_Player1);
            team.addSecondPlayer(name_Player2);
            tableTeams.getItems().add(team);


        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

    }



    @FXML
    private void deleteTeam(ActionEvent event) throws FileNotFoundException {
        Teams teams = tableTeams.getSelectionModel().getSelectedItem();
        try {
            Connection con = getConnection();
            con.createStatement().executeUpdate("DELETE FROM `tournament_teams` WHERE `team_id`=" + teams.getIdOfTeam());
            con.createStatement().executeUpdate("DELETE FROM `tournament_games` WHERE `tournament_id` =" + selectedTournament);
            con.createStatement().executeUpdate("DELETE FROM `tournament_players` WHERE `player_team` =" +  teams.getIdOfTeam());
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
        loadTeam();
    }

    @FXML
    private void loadTeam() throws FileNotFoundException {
        FETCH_DATA(FETCH_TEAMS);
        columnNameTeam.setCellValueFactory(new PropertyValueFactory<>("team_name"));
        columnPlayer1Name.setCellValueFactory(new PropertyValueFactory<>("player1_name"));
        columnPlayer2Name.setCellValueFactory(new PropertyValueFactory<>("player2_name"));
        columnTeamGoals.setCellValueFactory(new PropertyValueFactory<>("team_goals"));
        tableTeams.setItems(null);
        tableTeams.setItems(dataTeams);
    }

    @FXML
    private TableColumn<Teams, String> columnFirstTeam;
    @FXML
    private TableColumn<Teams, String> columnSecondTeam;
    @FXML
    private TableColumn<Teams, String> columnScore;
    @FXML
    private javafx.scene.control.TableView<Game> tableGames;

    @FXML
    private void loadGames(){
        FETCH_DATA(FETCH_GAMES);
        columnFirstTeam.setCellValueFactory(new PropertyValueFactory<>("firstTeamName"));
        columnSecondTeam.setCellValueFactory(new PropertyValueFactory<>("secondTeamName"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("Score"));

        tableGames.setItems(null);
        tableGames.setItems(GameData);
    }


    @FXML
    private void StartGame(ActionEvent event){
        try {
            selectedGame = tableGames.getSelectionModel().getSelectedIndex();
            loadGames();
            if(GameData.get(selectedGame).getFirstTeamScore() == 9 || GameData.get(selectedGame).getSecondTeamScore() == 9){
                AlertBox.display("Invalid game","\nThis game has been already played.\n\n\n");
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Match.fxml"));


                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            }




        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void ResetGames(ActionEvent e){
        try{
            Connection connHandle = getConnection();
            connHandle.createStatement().executeUpdate("DELETE FROM `tournament_games` WHERE `tournament_id` =" + selectedTournament);
            boolean invalidNumberOfTeams = true;
            for(int i = 1; i < dataTeams.size(); i++){
                if(Math.pow(2, i) == dataTeams.size())
                    invalidNumberOfTeams = false;
            }
            if(invalidNumberOfTeams){
                AlertBox.display("Invalid Number of Teams", "   There must be n^2 number of teams.    \n\n\n\n");
            }
            else{
                /*
                    =========================================================
                            GENERATE MATCHES ALGORITHM
                    =========================================================
                 */
                GameData = FXCollections.observableArrayList();
                Random r = new Random();
                int numberOfGames = 0, firstTeam, secondTeam;
                ArrayList<Integer> usedTeams = new ArrayList<>();
                while(numberOfGames != dataTeams.size() / 2){
                    boolean condition = false;
                    int x;
                    while(!condition){
                        firstTeam = r.nextInt(dataTeams.size()) + 1;
                        secondTeam = r.nextInt(dataTeams.size()) + 1;
                        if(usedTeams.indexOf(firstTeam) == -1 && usedTeams.indexOf(secondTeam) == -1 && firstTeam != secondTeam){
                            usedTeams.add(firstTeam);
                            usedTeams.add(secondTeam);
                            condition = true;
                            connHandle.createStatement().executeUpdate("INSERT INTO `tournament_games` (`game_team1`,`game_team2`,`tournament_id`) VALUES ('" + dataTeams.get(firstTeam-1).getIdOfTeam() +"', '" + dataTeams.get(secondTeam-1).getIdOfTeam() +"', '" + selectedTournament +"' )");
                            ResultSet re = connHandle.createStatement().executeQuery("SELECT `game_id` FROM `tournament_games` ORDER BY game_id DESC LIMIT 1");
                            re.next();
                            GameData.add(new Game(re.getInt(1),  dataTeams.get(firstTeam-1).getIdOfTeam(), dataTeams.get(secondTeam-1).getIdOfTeam(), 0, 0, dataTeams.get(firstTeam-1).getTeam_name(), dataTeams.get(secondTeam-1).getTeam_name(), firstTeam, secondTeam ));
                        }
                    }
                    connHandle.createStatement().executeUpdate("UPDATE `tournament_teams` SET `team_winnings` = 0, `team_played_games` = 0 WHERE `tournament_id` ="  +selectedTournament);
                    numberOfGames++;
                }


                columnFirstTeam.setCellValueFactory(new PropertyValueFactory<>("firstTeamName"));
                columnSecondTeam.setCellValueFactory(new PropertyValueFactory<>("secondTeamName"));
                columnScore.setCellValueFactory(new PropertyValueFactory<>("Score"));

                tableGames.setItems(null);
                tableGames.setItems(GameData);
            }



            connHandle.close();
        }
        catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }
    @FXML
    private void NextStage(ActionEvent e){
        FETCH_DATA(FETCH_TEAMS);
        int n = 0, z = dataTeams.size(),LastNumberOfWinnings = 0, finishedGames = 0, sum = 0,unfinishedGames = 0;

        for(int i = 0; i < dataTeams.size(); i++){
            if(dataTeams.get(i).getTeam_winnings() > LastNumberOfWinnings)
                LastNumberOfWinnings = dataTeams.get(i).getTeam_winnings();
        }
        boolean updatequery = false;
        for(int i = 0; i < GameData.size(); i++){
            if(GameData.get(i).getSecondTeamScore() == 9 || GameData.get(i).getFirstTeamScore() == 9){
                finishedGames++;
            }
            else
                unfinishedGames++;
        }
        while(z > 1){
            n++;
            z /= 2;
        }
        for(int i = 1; i <= LastNumberOfWinnings ; i++){
            sum += Math.pow(2, n - i);
        }
        System.out.println(sum);
        System.out.println(finishedGames);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        if(sum == finishedGames && finishedGames != 0 && unfinishedGames == 0)
            updatequery = true;


        if(updatequery){
            ArrayList<Integer> Teams = new ArrayList<>();
            int numberOfGames = 0, firstTeam, secondTeam;
            for (int i = 0; i < dataTeams.size(); i++) {
                if(dataTeams.get(i).getTeam_winnings() == LastNumberOfWinnings){
                    Teams.add(i);
                }
            }
            int size = Teams.size();
            Collections.shuffle(Teams);
            for(int i = 0; i < Teams.size(); i+=2){
                    firstTeam = Teams.get(i);
                    secondTeam = Teams.get(i+1);
                    try {
                            Connection connHandle = getConnection();
                            connHandle.createStatement().executeUpdate("INSERT INTO `tournament_games` (`game_team1`,`game_team2`,`tournament_id`) VALUES ('" + dataTeams.get(firstTeam).getIdOfTeam() + "', '" + dataTeams.get(secondTeam).getIdOfTeam() + "', '" + selectedTournament + "' )");
                            ResultSet re = connHandle.createStatement().executeQuery("SELECT `game_id` FROM `tournament_games` ORDER BY game_id DESC LIMIT 1");
                            re.next();
                            GameData.add(new Game(re.getInt(1),  dataTeams.get(firstTeam).getIdOfTeam(), dataTeams.get(secondTeam).getIdOfTeam(), 0, 0, dataTeams.get(firstTeam).getTeam_name(), dataTeams.get(secondTeam).getTeam_name(), firstTeam, secondTeam ));
                        }
                        catch (SQLException Ex){
                            System.out.println("SQL ERROR: " + Ex);
                    }

            }

        }
        loadGames();
    }



}
