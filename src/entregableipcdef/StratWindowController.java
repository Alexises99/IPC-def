/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Member;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class StratWindowController implements Initializable {

    @FXML
    private TextField loginFIeld;
    @FXML
    private TextField passField;
    @FXML
    private Label labelToOpen;
    @FXML
    private Label labelToSeeCourt;
    @FXML
    private Button butToClose;
    @FXML
    private Button butToLogin;
    @FXML
    private Label labelErr;
    
    private ClubDBAccess clubDBAccess;
    
    private Member member;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        
        labelErr.setVisible(false);
    }    

    @FXML
    private void openSignUp(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpWindow.fxml"));
        Scene sc = new Scene(root);
        Stage st = new Stage();
        st.setScene(sc);
        st.show();
    }

    @FXML
    private void openCourtWIndow(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourtFreeToday.fxml"));
        Scene sc = new Scene(root);
        Stage st = new Stage();
        st.setScene(sc);
        st.show();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) this.butToClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openControlPanel(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
        this.member = clubDBAccess.getMemberByCredentials(loginFIeld.getText(),passField.getText());
        
            if(this.member == null){
                labelErr.setDisable(false);
                labelErr.setVisible(true);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la Aplicacion");
                alert.setHeaderText("Error en Inicio de sesion");
                alert.setContentText("Login o Password incorrecto");
                ButtonType buttonTypeOne = new ButtonType("OK");
                alert.getButtonTypes().setAll(buttonTypeOne);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == buttonTypeOne){
                        alert.close();
                    }
                }
            
            }
            else{   
                Parent root = FXMLLoader.load(getClass().getResource("ControlPanelWindow.fxml"));
                Scene sc = new Scene(root);
                Stage st = new Stage();
                st.setScene(sc);
                st.show();
                Stage stage = (Stage) this.butToClose.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void openContolPanel(ActionEvent event) throws IOException {
        this.member = clubDBAccess.getMemberByCredentials(loginFIeld.getText(),passField.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanelWindow.fxml"));
        Parent root = loader.load();
        ControlPanelWindowController controller = loader.getController();
        controller.passMember(this.member);
        
        if(member == null){
            labelErr.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la Aplicacion");
            alert.setHeaderText("Error en Inicio de sesion");
            alert.setContentText("Login o Password incorrecto");
            ButtonType buttonTypeOne = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonTypeOne);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get() == buttonTypeOne){
                    alert.close();
                }
            }   
        }
        else{
            Scene sc = new Scene(root);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            //st.setResizable(false);
          
        }
    }
    
    private void passMemberCredentials(Member member) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingWindow.fxml"));
        Parent root = loader.load();
        BookingWindowController controller = loader.getController();
        controller.passMember(member);
    }
    
    
}
