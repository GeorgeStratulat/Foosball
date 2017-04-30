package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static sample.DBConnection.getConnection;
import static sample.GlobalVariable.selectedGame;
import static sample.GlobalVariable.selectedTournament;

/**
 * Created by Constantine on 4/30/2017.
 */
public class GameController implements  Initializable{
    @FXML
    private Label Player1Team1Score;
    @FXML
    private Label Player2Team1Score;
    @FXML
    private Label Player1Team2Score;
    @FXML
    private Label Player2Team2Score;
    @FXML
    private Label FirstTeamScore;
    @FXML
    private Label SecondTeamScore;
    public static ObservableList<Players> PlayerData;
    public static ObservableList<Teams> TeamData;
    public static ObservableList<Game> GameData;


    private static final int FETCH_PLAYERS = 1;
    private static final int FETCH_TEAMS = 2;
    private static final int FETCH_GAMES = 3;

    public void initialize(URL url, ResourceBundle rb){
        FETCH_DATA(FETCH_PLAYERS);
        FETCH_DATA(FETCH_TEAMS);
        FETCH_DATA(FETCH_GAMES);
        for(int i = 0,j =0,z=0; i < PlayerData.size(); i++) {
            if (PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getFirstTeamScriptID()).getIdOfTeam()) {
                if(j++ == 0){
                    Player1Team1Score.setText(PlayerData.get(i).getName());
                }
                else{
                    Player2Team1Score.setText(PlayerData.get(i).getName());
                }
            }
            else if (PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getSecondTeamScriptID()).getIdOfTeam()) {
                if(z++ == 0){
                    Player1Team2Score.setText(PlayerData.get(i).getName());
                }
                else{
                    Player2Team2Score.setText(PlayerData.get(i).getName());
                }
            }
        }
        FirstTeamScore.setText(Integer.toString(TeamData.get(GameData.get(selectedGame).getFirstTeamScriptID()).getTeam_goals()));
        SecondTeamScore.setText(Integer.toString(TeamData.get(GameData.get(selectedGame).getSecondTeamScriptID()).getTeam_goals()));
    }
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
                    PlayerData = FXCollections.observableArrayList();

                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `tournament_players` INNER JOIN  `tournament_teams` ON `team_id` = `player_team` WHERE `tournament_id` =" + GlobalVariable.selectedTournament);
                    while (rs.next()) {
                        PlayerData.add(new Players(rs.getInt(1), rs.getString(2), rs.getString(9), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));

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
                    TeamData = FXCollections.observableArrayList();

                    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `tournament_teams` WHERE `tournament_id` = " + selectedTournament);
                    while (rs.next()) {
                        Teams team = new Teams(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),  rs.getInt(5),  rs.getInt(6));
                        ResultSet result = con.createStatement().executeQuery("SELECT `player_name` FROM `tournament_players` WHERE `player_team` = " + rs.getInt(1));
                        result.next();
                        team.addFirstPlayer(result.getString(1));
                        result.next();
                        team.addSecondPlayer(result.getString(1));
                        TeamData.add(team);
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


                        for(int i = 0; i < TeamData.size(); i++) {
                            if (TeamData.get(i).getIdOfTeam() == fistTeamID)
                                firstTeamScriptID = i;
                            else if (TeamData.get(i).getIdOfTeam() == secondTeamID)
                                secondTeamScriptID = i;
                        }
                        GameData.add(new Game(r.getInt(1), r.getInt(2), r.getInt(3), r.getInt(4), r.getInt(5), TeamData.get(firstTeamScriptID).getTeam_name(), TeamData.get(secondTeamScriptID).getTeam_name(), firstTeamScriptID, secondTeamScriptID ));

                    }

                }
                catch (SQLException ex) {
                    System.err.println("Error" + ex);
                }
                break;
        }
    }

    private void UpdateScoreboard(int playerID, int team){
        if(GameData.get(selectedGame).getFirstTeamScore() == 9 || GameData.get(selectedGame).getSecondTeamScore() == 9){
            AlertBox.display("Finalised Game", "      This game has been finalised.\n\n      ");
        }
        else{
            int score,winnerTeam= 0,loserTeam = 0;
            boolean updateTeams = false;
            PlayerData.get(playerID).setGoals(PlayerData.get(playerID).getGoals() + 1);
            if(team == 1){
                score = GameData.get(selectedGame).getFirstTeamScore() + 1;
                GameData.get(selectedGame).setFirstTeamScore(score);
                FirstTeamScore.setText(Integer.toString(GameData.get(selectedGame).getFirstTeamScore()));
                if(score == 9){
                    winnerTeam = GameData.get(selectedGame).getFirstTeamScriptID();
                    loserTeam = GameData.get(selectedGame).getSecondTeamScriptID();

                    TeamData.get(winnerTeam).setTeam_winnings(TeamData.get(winnerTeam).getTeam_winnings() + 1);
                    TeamData.get(winnerTeam).setTeamGames(TeamData.get(winnerTeam).getTeamGames() + 1);
                    TeamData.get(loserTeam).setTeamGames(TeamData.get(loserTeam).getTeamGames() + 1);
                    updateTeams = true;
                }
            }
            else {
                score = GameData.get(selectedGame).getSecondTeamScore() + 1;
                GameData.get(selectedGame).setSecondTeamScore(score);
                SecondTeamScore.setText(Integer.toString(score));
                if(score == 9){
                    winnerTeam = GameData.get(selectedGame).getSecondTeamScriptID();
                    loserTeam = GameData.get(selectedGame).getFirstTeamScriptID();

                    TeamData.get(winnerTeam).setTeam_winnings(TeamData.get(winnerTeam).getTeam_winnings() + 1);
                    TeamData.get(winnerTeam).setTeamGames(TeamData.get(winnerTeam).getTeamGames() + 1);
                    TeamData.get(loserTeam).setTeamGames(TeamData.get(loserTeam).getTeamGames() + 1);
                    updateTeams = true;
                }
            }
            try {
                Connection connHandle = getConnection();
                connHandle.createStatement().executeUpdate("UPDATE `tournament_players` SET `player_goals` = `player_goals` + 1 WHERE `player_id` = " + PlayerData.get(playerID).getId() + " LIMIT 1");
                connHandle.createStatement().executeUpdate("UPDATE `tournament_games` SET `game_team" + team + "_score` = `game_team" + team + "_score` + 1 WHERE `game_id` = " + GameData.get(selectedGame).getGameId() + " LIMIT 1");
                connHandle.createStatement().executeUpdate("UPDATE `tournament_teams` SET `team_goals` = `team_goals` + 1 WHERE `team_id` = " + PlayerData.get(playerID).getTeamID());
                if(updateTeams){
                    connHandle.createStatement().executeUpdate("UPDATE `tournament_teams` SET `team_winnings` = "+ TeamData.get(winnerTeam).getTeam_winnings()+", `team_played_games` =" + TeamData.get(winnerTeam).getTeamGames() +" WHERE `team_id`=" + TeamData.get(winnerTeam).getIdOfTeam());
                    connHandle.createStatement().executeUpdate("UPDATE `tournament_teams` SET `team_played_games` =" + TeamData.get(loserTeam).getTeamGames() +" WHERE `team_id`=" + TeamData.get(loserTeam).getIdOfTeam());
                }
                connHandle.close();
            }
            catch (SQLException Ex){
                System.out.println("SQL ERROR: " + Ex);
            }

        }
    }
    @FXML
    private void player1OfTheFirstTesmScores(ActionEvent e){
        for(int i = 0; i < PlayerData.size(); i++) {
            if (PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getFirstTeamScriptID()).getIdOfTeam()) {
                UpdateScoreboard(i, 1);
                break;
            }
        }
    }
    @FXML
    private void player2OfTheFirstTesmScores(ActionEvent e) throws Exception{
        for(int i = PlayerData.size() - 1; i >= 0; i--)
            if(PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getFirstTeamScriptID()).getIdOfTeam()){
                UpdateScoreboard(i, 1);
                break;
            }
    }
    @FXML
    private void player1OfTheSecondTesmScores(ActionEvent e) throws Exception{
        for(int i = 0; i < PlayerData.size(); i++)
            if(PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getSecondTeamScriptID()).getIdOfTeam()){
                UpdateScoreboard(i, 2);
                break;
            }
    }
    @FXML
    private void player2OfTheSecondTesmScores(ActionEvent e) throws Exception{
        for(int i = PlayerData.size() - 1; i >= 0; i--)
            if(PlayerData.get(i).getTeamID() == TeamData.get(GameData.get(selectedGame).getSecondTeamScriptID()).getIdOfTeam()){
                UpdateScoreboard(i, 2);
                break;
            }
    }
}
