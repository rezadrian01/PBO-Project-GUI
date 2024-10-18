package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.services.Auth;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> roleField;
    @FXML
    private Label expertiseLabel;
    @FXML
    private TextField expertiseField;
    @FXML
    private Label errorMessage;

    public void registerSubmit(ActionEvent event){
        try{
        Auth auth = new Auth();
        String name = this.nameField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        String role = this.roleField.getValue();
        String expertise = this.expertiseField.getText();

        // Validation
        if(name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || role.trim().isEmpty()){
            errorMessage.setText("Input cannot be empty");
            return;
        }
        if(role.equals("Speaker") && expertise.trim().isEmpty()){
            errorMessage.setText("Please include your expertise if you are a speaker");
            return;
        }
        if(!auth.register(name, email, password, role, expertise)){
            errorMessage.setText("Failed to register.");
            return;
        }
        this.toLogin(event);

        }catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }

    public void toLogin(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            errorMessage.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expertiseLabel.setText("");
        expertiseField.setOpacity(0);
        String[] roles = {"Speaker", "Participant"};
        roleField.getItems().addAll(roles);
        roleField.setOnAction(this::roleCheck);
    }

    public void roleCheck(ActionEvent event){
        String role = this.roleField.getValue();
        if(role.equals("Speaker")){
            expertiseLabel.setText("Expertise");
            expertiseField.setOpacity(1);
        }else if(role.equals("Participant")){
            expertiseLabel.setText("");
            expertiseField.setOpacity(0);
        }
    }

}
