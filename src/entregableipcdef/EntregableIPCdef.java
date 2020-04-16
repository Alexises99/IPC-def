/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipcdef;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ALEJANDRO SANCHEZ Y RUBEN MORALES
 * 
 * ->FALTA AÑADIR LA SELECCION DE IMAGENES EN EL REGISTRO(Si puede emplear las de su propio pc mejor).
 * ->CREO QUE QUEDARIA BIEN PONER LA IMAGEN DEL USUARIO EN GESTIONAR RESERVAS.
 * ->AÑADIR UNA IMAGEN AL CLUB AL INICIO Y EN RESERVAR.
 * ->PONER QUE EL USUARIO PUDE CAMBIAR SUS DATOS.
 * ->QUE CUANDO SE HABRA GESTIONAR RESERVAS Y VER LAS PISTAS HOY SIN LOGEARSE SE ACTUALIZE LA TABLA SOLO SIN TENER QUE DARLE A UN BOTON,
 *    SI LO PONGO EN INITIALIZE ESA VERGA EXPLOTA CON ERROR.
 */

public class EntregableIPCdef extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StratWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Introduce Login y Password");
        stage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
