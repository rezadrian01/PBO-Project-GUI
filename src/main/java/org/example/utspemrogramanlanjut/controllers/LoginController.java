package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.services.Auth;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorMessage;

    public void loginSubmit(ActionEvent event) {
        try{
            Auth auth = new Auth();
            String email = this.email.getText();
            String password = this.password.getText();
            // Validation
            if(email.trim().isEmpty() || password.trim().isEmpty()) {
                errorMessage.setText("Input cannot be empty");
                return;
            }
            if(!auth.login(email, password)) {
                errorMessage.setText("Login failed");
                return;
            }

            // Redirect to main menu base on role
            if(Auth.getLoggedInUser() instanceof Speaker){
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/SpeakerMainMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }else{
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/ParticipantMainMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

        }catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }
    public void toRegister(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/Register.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }
}
