/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Member;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class ControlPanelWindowController implements Initializable {

    @FXML
    private Button butBooking;
    @FXML
    private Button butSeeBookings;
    
    private Member member;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    @FXML
    private void openBookingWindow(ActionEvent event) throws IOException {
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingWindow.fxml"));
            Parent root = loader.load();
            BookingWindowController controller = loader.getController();
            controller.passMember(member);
           
             
                Scene sc = new Scene(root);
                Stage st = new Stage();
                st.setScene(sc);
                st.show();
    }

    @FXML
    private void openSeeBookingsWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBookingWindow.fxml"));
            Parent root = loader.load();
            ViewBookingWindowController controller = loader.getController();
            controller.passMember(member);
           
             
                Scene sc = new Scene(root);
                Stage st = new Stage();
                st.setScene(sc);
                st.show();
                
                
    }

    
    
    
    
    public void passMember(Member member){
        this.member = member;
    }
    
}
