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
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import static sample.DBConnection.getConnection;
import static sample.GlobalVariable.nameOfTournamentGlobal;

public class TournamentController implements Initializable {

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
    @FXML
    private TableView tournaments;

    private ObservableList<ObservableList> data;


    String Name_Tournament;

    @FXML
    private void createNewTournament(ActionEvent event) throws Exception {
        try {
            Connection con = getConnection();
            String name = name_Tournament.getText();
            nameOfTournamentGlobal = name;
            String databaseName1 = nameOfTournamentGlobal+"teams";
            String databaseName2 = nameOfTournamentGlobal+"players";
            String prize = prize_Tournament.getText();
            String dateStart = date_To_Start.getText();
            String dateFinish = date_To_Finish.getText();
            String sql = "INSERT INTO tournaments VALUES (NULL, '"+name+"', '"+prize+"', '"+dateStart+"', '"+dateFinish+"')";
            con.createStatement().executeUpdate(sql);
            String another_sql = "CREATE TABLE "+databaseName1+"(id int(55) PRIMARY KEY AUTO_INCREMENT, team_name VARCHAR(255), player1_id int(55), player2_id int(55), player1_name VARCHAR(255), player2_name VARCHAR(255), tournament_id int(55), team_goals int(55), winner int(10))";
            con.createStatement().executeUpdate(another_sql);
            String ano_sql = "CREATE TABLE "+databaseName2+"(id int(55) PRIMARY KEY AUTO_INCREMENT, player_name VARCHAR(255), player_team VARCHAR(255), player_date VARCHAR(255), player_email VARCHAR(255), player_goals INT(55))";
            con.createStatement().executeUpdate(ano_sql);
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FirstWindow.fxml"));
        Scene scene = new Scene(root,1024,720);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void generateNewTournament(ActionEvent event) throws Exception {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("General Window.fxml"));
        Scene scene = new Scene(root,1024,720);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
        try {
            if (url.toString().contains("FirstWindow")) {
                getTournaments();
                tournaments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        }
        catch (Exception e){

        }
        // tournaments.getItems().setAll(this.data);
    }

    @FXML
    private void getTournaments() throws Exception {
        Connection con = getConnection();
        //String sql = "SELECT name FROM tournaments LIMIT 4";
        data = FXCollections.observableArrayList();
        try{
            Connection c = DBConnection.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT id,name FROM tournaments ORDER BY id DESC LIMIT 4";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tournaments.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tournaments.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @FXML
    private void viewTournament(ActionEvent event) throws Exception{
        List selected = tournaments.getSelectionModel().getSelectedItems();
        System.out.println(selected.get(0));

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("General Window.fxml"));
        Scene scene = new Scene(root,1024,720);
        stage.setTitle(selected.get(0).toString());
        stage.setScene(scene);
        stage.show();
    }
}
