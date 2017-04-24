package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.*;

import static sample.DBConnection.getConnection;

public class MatchController {

    @FXML
    private Button buttonGenerateMatches;
    private ObservableList<ObservableList> data;

    @FXML
    private void generateMatches() throws Exception{
        System.out.println("generate matches");
        getTeams();
    }

    private void getTeams() {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM teams WHERE tournament_id=7 AND winner=1");
            ArrayList teams = new ArrayList();
            while (rs.next()) {
                teams.add(rs.getInt(1));
            }
            Collections.shuffle(teams);
            for(int i=0; i < teams.size()/2; i++) {
                System.out.println("Match " + i + ": " + teams.get(i) + " vs " + teams.get(i+teams.size()/2));
                createMatch((int)teams.get(i), (int)teams.get(i+teams.size()/2));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createMatch(int team1_id, int team2_id) {
        try {
            String sql = "INSERT INTO matches(team1_id,team2_id) VALUES ('"+team1_id+"','"+team2_id+"')";

            Connection con = getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
