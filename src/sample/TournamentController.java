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
import static sample.GlobalVariable.selectedTournament;



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
    private Button save_New_Team;
    @FXML
    private TableView tournaments;

    private ObservableList<ObservableList> data;

    private int[] tournametsSQLID = new int[15];

    @FXML
    private void createNewTournament(ActionEvent event) throws Exception {

        try {
            Connection con = getConnection();
            String query = "INSERT INTO tournaments(`tournament_name`,`tournament_prize`, `tournament_dateToStart`,`tournament_dateToFinish`) " +
                    "VALUES ('"+name_Tournament.getText()+"', '"+prize_Tournament.getText()+"', '"+date_To_Start.getText()+"', '"+date_To_Finish.getText()+"')";
            con.createStatement().executeUpdate(query);
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
    }

    @FXML
    private void getTournaments() throws Exception {
        Connection con = getConnection();
        data = FXCollections.observableArrayList();
        try{
            Connection c = DBConnection.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT tournament_id,tournament_name FROM tournaments ORDER BY tournament_id DESC LIMIT 10";
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
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            int z = 0;
            while(rs.next()){
                //Iterate Row
                tournametsSQLID[z++] = rs.getInt("tournament_id");
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    //
                    System.out.println(row.add(rs.getString(i)));
                }
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
        selectedTournament = tournametsSQLID[tournaments.getSelectionModel().getSelectedIndex()];

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("General Window.fxml"));
        Scene scene = new Scene(root,1024,720);
        stage.setTitle(selected.get(0).toString());
        stage.setScene(scene);
        stage.show();
    }
}
