/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Booking;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class CourtFreeTodayController implements Initializable {

    @FXML
    private TableView<Booking> tableView;
    @FXML
    private TableColumn<Booking, String> columnIni;
    @FXML
    private TableColumn<Booking, String> columnEnd;
    @FXML
    private TableColumn<Booking, String> columnCourt;
    @FXML
    private TableColumn<Booking, String> columnMember;
    @FXML
    private TableColumn<Booking, String> columnPaid;
    @FXML
    private Button butToUpdate;
    @FXML
    private Button butToClose;
    
    private ClubDBAccess clubDBAccess;
    
    private ObservableList<Booking> obvList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        //Configuracion de tableView
        columnMember.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getMember().getLogin()));
        columnCourt.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getCourt().getName()));
        columnIni.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().toString()));
        columnEnd.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().plusMinutes(90).toString()));
        columnPaid.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPaid().toString()));
    }    
    
    //Actualizar tabla
    @FXML
    private void updateTable(ActionEvent event) {
        ArrayList<Booking> list = clubDBAccess.getForDayBookings(LocalDate.now());
        if(list.isEmpty()) return;
        obvList = FXCollections.observableList(list);
        tableView.setItems(obvList);
        
        
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) this.butToClose.getScene().getWindow();
        stage.close();
    }
    
}
