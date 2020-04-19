/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Booking;
import model.Member;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class ViewBookingWindowController implements Initializable {

    @FXML
    private TableView<Booking> tableView;
    @FXML
    private TableColumn<Booking, String> columnDate;
    @FXML
    private TableColumn<Booking, String> columnCourt;
    @FXML
    private TableColumn<Booking, String> columnIni;
    @FXML
    private TableColumn<Booking, String> columnEnd;
    @FXML
    private TableColumn<Booking, String> columnPaid;
    @FXML
    private Button butAccept;
    @FXML
    private Button butCancel;
    @FXML
    private Button butToDel;
    
    private ObservableList<Booking> obvList;
    
    private ClubDBAccess clubDBAccess;
    
    private Member member;
    
    private String login;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
       clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
       columnDate.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBookingDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
       columnCourt.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getCourt().getName()));
       columnIni.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().toString()));
       columnEnd.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().plusMinutes(90).toString()));
       columnPaid.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPaid().toString()));
       
       
        
    }    
    @FXML
    private void updateTable(ActionEvent event) {
        ArrayList<Booking> list = clubDBAccess.getUserBookings(member.getLogin());
        if(list.isEmpty()) return;
        list.sort((o1,o2) -> o1.getBookingDate().compareTo(o2.getBookingDate()));
        ArrayList<Booking> listDef = listMax(list);
        obvList = FXCollections.observableList(listDef);
        tableView.setItems(obvList);

    }
   
    @FXML
    private void closeWindow(ActionEvent event) {
       Stage stage = (Stage) this.butAccept.getScene().getWindow();
       stage.close();
       

    }
   @FXML
    private void deleteBook(ActionEvent event) {
       int index = tableView.getSelectionModel().getSelectedIndex();
       if(index < 0){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error eliminando reserva");
             alert.setHeaderText("No has seleccionado ninguna reserva");
             alert.setContentText("Por favor, selecciona una reserva");
             ButtonType buttonTypeOne = new ButtonType("OK");
             alert.getButtonTypes().setAll(buttonTypeOne);
             Optional<ButtonType> result = alert.showAndWait();
             if(result.isPresent()){
                if(result.get() == buttonTypeOne){
                    alert.close();
                }
            }
            return;
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            obvList.get(index).getBookingDate().isAfter(now);
            
            if(obvList.get(index).getBookingDate().minusHours(24).isBefore(now)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error eliminando reserva");
                alert.setHeaderText("Se ha cumplido el tiempo limite de 24 horas");
                alert.setContentText("No puedes cancelar la reserva,disculpe las molestias");
                ButtonType buttonTypeOne = new ButtonType("OK");
                alert.getButtonTypes().setAll(buttonTypeOne);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == buttonTypeOne){
                        alert.close();
                    }
                }
                return;
            }
            
            clubDBAccess.getBookings().remove(obvList.get(index));
            clubDBAccess.saveDB();
            obvList.remove(index);
                
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion de Reserva");
            alert.setHeaderText("Reserva eliminada con exito");
            alert.setContentText("Su reserva ha sido eliminada con exito");
            ButtonType buttonTypeOne = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonTypeOne);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get() == buttonTypeOne){
                    alert.close();
                }
            }  
        }
    }
    
    private ArrayList<Booking> listMax(ArrayList<Booking> list){
        ArrayList<Booking> listDef = new ArrayList();
        for(int i = 0; i < 10 && i < list.size(); i++){
            listDef.add(list.get(i));
        }
        return listDef;
    }
    
    public void passMember(Member member){
        this.member = member;
    }
    
}
