/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Member;

/**
 * FXML Controller class
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 */
public class SignUpWindowController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField tlfField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private TextField cardField;
    @FXML
    private Button butAccept;
    @FXML
    private Button butCancel;
    @FXML
    private ChoiceBox<ImageView> choiceImg;
    @FXML
    private TextField csvField;
    @FXML
    private ImageView ima1;
    
    ClubDBAccess clubDBAccess;
    
    private Member member;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        ima1.setVisible(true);
    }    
    
    //Comprobar si el login es correcto
    private boolean testLogin(String login){
       if(login.contains(" ")) return false;
       else return true;
    }
    
    private boolean sameLogin(String login){
        for(int i = 0; i < clubDBAccess.getMembers().size(); i++ ){
            if(clubDBAccess.getMembers().get(i).getLogin().equals(login)) return true;
        }
        return false;
        
    }
    
    //Informa de errores empleada en saveSignUp
    private void showAlertError(String header,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la Aplicacion");
                alert.setHeaderText(header);
                alert.setContentText(content);
                ButtonType buttonTypeOne = new ButtonType("OK");
                alert.getButtonTypes().setAll(buttonTypeOne);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    if(result.get() == buttonTypeOne){
                        alert.close();
                    }
    }
    }

    @FXML
    private void saveSignUp(ActionEvent event) {
        
        if(nameField.getText().length() == 0){
            showAlertError("Nombre incorrecto","Por favor, introduzca un nombre correcto");
            return;
        }
        if(lastNameField.getText().length() == 0){
            showAlertError("Apellidos incorrectos","Por favor, introduzca un apellido correcto");
            return;
        }
        if(tlfField.getText().length() !=  9){
            showAlertError("Telefono incorrecto","Por favor, introduzca un telefono correcto");
            return;
        }
        
        if(!testLogin(loginField.getText())){
            showAlertError("Hay espacios en el Login","Por favor, elimina los espacios");
            return;
        }
        if(sameLogin(loginField.getText())){
            showAlertError("Login ocupado","Por favor, introduzca otro");
            return;
        }
        
        if(passField.getText().length() < 6){
            showAlertError("Contraseña muy corta","Por favor, añade mas caracteres");
            return;
        }
            
        if(cardField.getText().length() != 16 && cardField.getText().length() >= 1){
            showAlertError("Faltan digitos en el Numero de Tarjeta","Por favor, corriga el Numero de Tarjeta");
            return;
        }

        if(csvField.getText().length() < 3 && cardField.getText().length() >= 1){
            showAlertError("CSV es incorrecto","Por favor arreglelo");
            return;
        }
        
        
        //Falta el choicebox de imagenes
        member = new Member(nameField.getText(),lastNameField.getText(),tlfField.getText(),loginField.getText(),
                                      passField.getText(),cardField.getText(),csvField.getText(),null);//Falta imagen
        
        clubDBAccess.getMembers().add(member);
        clubDBAccess.saveDB();
        
        //SE DEBERIA CERRAR PERO NO QUIERE CERRARSE
        Stage stage = (Stage) this.butAccept.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) this.butCancel.getScene().getWindow();
        stage.close();
        
    }
    
}
