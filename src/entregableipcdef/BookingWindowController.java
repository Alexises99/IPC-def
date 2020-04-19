/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Booking;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class BookingWindowController implements Initializable {

    @FXML
    private ChoiceBox<Court> choiceCourt;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Booking> tableView;
    @FXML
    private TableColumn<Booking, String> columnDate;
    @FXML
    private TableColumn<Booking, String> columnHourStrat;
    @FXML
    private TableColumn<Booking, String> columnHourEnd;
    @FXML
    private TableColumn<Booking, String> columnMember;
    @FXML
    private Button butBooking;
    @FXML
    private Button butToSearch;
    @FXML
    private Button butCancel;
    
    private ClubDBAccess clubDBAccess;
    
    private ObservableList<Booking> data;
    
    private Member member;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        //ChoiceBox para elegir pista
        ArrayList<Court> list = new ArrayList();
        list.add(clubDBAccess.getCourts().get(0));
        list.add(clubDBAccess.getCourts().get(1));
        list.add(clubDBAccess.getCourts().get(2));
        list.add(clubDBAccess.getCourts().get(3));
        ObservableList<Court> listObv = FXCollections.observableArrayList(list);
        choiceCourt.setItems(listObv);
        //Para que salga el nombre de la pista
        choiceCourt.setConverter(new StringConverter<Court>(){
             public String toString(Court c) {
                 return c.getName();
            }
            @Override
            public Court fromString(String s) {
                    return null ;
            }
        });
        
        //Dias anteriores a hoy tachados
        datePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
            @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0 );
                }
            };
        });
        
        
                
        //Configuracion columnas de tableView
        
        columnDate.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBookingDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        columnHourStrat.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().toString()));
        columnHourEnd.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFromTime().plusMinutes(90).toString()));
        columnMember.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getMember().getLogin()));
        
        
    }
    
    //Metodo que empleara ControlPanelWindowController para actualizar this.member
    public void passMember(Member member){
        this.member = member;
    }

    //Extrae de una lista de reservas las fechas y horas a una lista solo de LocalDateTime
    private ArrayList<LocalDateTime> toLocalDateTime(ArrayList<Booking> list){
        ArrayList<LocalDateTime> l = new ArrayList();
        for(int i = 0;i < list.size();i++){
            l.add(list.get(i).getBookingDate());
        }
        return l;
    }
    
    //Accion que ocurre al poner buscar reservas
    @FXML
    private void pickDate(ActionEvent event) {
        //Miembro falso que indica una pista libre
        Member memberNull = new Member();
        memberNull.setName("Vacia");
        memberNull.setLogin("Vacia");
        //Comprobacion de que la pista esta libre sin nadie que la emplee
        LocalDate date = datePicker.getValue();
        ArrayList<Booking> listBook = clubDBAccess.getCourtBookings(choiceCourt.getValue().getName(), date);
        ArrayList<LocalDateTime> listDates = toLocalDateTime(listBook);
        LocalTime lt = LocalTime.of(9, 0);
        if(!listDates.contains(LocalDateTime.of(date,lt))){
           
            listBook.add(new Booking(LocalDateTime.of(date,lt),date,lt,true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(90)))){
            
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(90)),date,lt.plusMinutes(90),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(180)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(180)),date,lt.plusMinutes(180),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(270)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(270)),date,lt.plusMinutes(270),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(360)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(360)),date,lt.plusMinutes(360),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(450)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(450)),date,lt.plusMinutes(450),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(540)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(540)),date,lt.plusMinutes(540),true,choiceCourt.getValue(),memberNull));
        }
        if(!listDates.contains(LocalDateTime.of(date,lt.plusMinutes(630)))){
            listBook.add(new Booking(LocalDateTime.of(date,lt.plusMinutes(630)),date,lt.plusMinutes(630),true,choiceCourt.getValue(),memberNull));
        }
        
        data = FXCollections.observableList(listBook);
        //Ordenar la lista por fechas
        data.sort((o1,o2) -> o1.getBookingDate().compareTo(o2.getBookingDate()));
        tableView.setItems(data);
       
    }

    //Al pulsar reservar
    @FXML
    private void bookingCourt(ActionEvent event) {
        //Miembro para comprobar
        Member memberNull = new Member();
        memberNull.setName("Vacia");
        memberNull.setLogin("Vacia");
        int index = tableView.getSelectionModel().getSelectedIndex();
         
        //Error si no se ha seleccionado nada
        if(index < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la Reserva");
            alert.setHeaderText("No has seleccionado ninguna hora libre");
            alert.setContentText("Por favor, selecciona una pista libre");
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
        //Reserva disponible
        Booking b = tableView.getSelectionModel().getSelectedItem();
        //Comprobar que la pista no esta asignada
        if(!b.getMember().getName().equals(memberNull.getName())){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la Reserva");
                alert.setHeaderText("No has seleccionado ninguna pista libre");
                alert.setContentText("Por favor, selecciona una pista libre");
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
        //Comprobacion de si hay tarjeta
        if(member.checkHasCreditInfo()){
            b.setPaid(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion de Pago");
            alert.setHeaderText("Reserva Pagada");
            alert.setContentText("Su reserva ha sido pagada con exito");
            ButtonType buttonTypeOne = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonTypeOne);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get() == buttonTypeOne){
                    alert.close();
                }
            }   
        }
        
        else if(!member.checkHasCreditInfo()){
            b.setPaid(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion de Pago");
            alert.setHeaderText("Reserva No Pagada");
            alert.setContentText("Su reserva NO ha sido pagada, DEBERA SER PAGADA ANTES DE ENTRAR A PISTA");
            ButtonType buttonTypeOne = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonTypeOne);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get() == buttonTypeOne){
                    alert.close();
                }
            }
        }
        
        //Asignar miembro a la reserva y pista y actualizar
        b.setMember(member);
        b.setCourt(choiceCourt.getValue());
        tableView.getItems().set(index,b);
        clubDBAccess.getBookings().add(b);
        clubDBAccess.saveDB();
        
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) this.butCancel.getScene().getWindow();
        stage.close();
       

    }
    
    class CourtListCell extends ListCell<Court>{
        @Override
        protected void updateItem(Court item, boolean empty){
            super.updateItem(item, empty); // Obligatoria esta llamada
            if (item==null || empty) setText(null);
             else setText(item.getName());
        }   
    }
    
}
