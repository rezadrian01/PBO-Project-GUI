package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.services.Auth;

import java.net.URL;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField roleField;
    @FXML
    private Hyperlink backButton;
    @FXML
    private Label expertiseLabel;
    @FXML
    private TextField expertiseField;
    @FXML
    private Label errorMessage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person currentUser = Auth.getLoggedInUser();
        errorMessage.setVisible(false);
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        if(currentUser instanceof Speaker){
            expertiseField.setText(((Speaker) currentUser).getExpertise());
            roleField.setText("Speaker");
        }else{
            expertiseLabel.setOpacity(0);
            expertiseField.setOpacity(0);
            roleField.setText("Participant");
        }
        roleField.setDisable(true);
    }
    public void updateSubmit(ActionEvent event){
        String name = nameField.getText();
        String email = emailField.getText();
        String expertise = expertiseField.getText();
        if(Auth.getLoggedInUser() instanceof Speaker){
            if(!((Speaker) Auth.getLoggedInUser()).updateProfile(name, email, expertise)){
                errorMessage.setVisible(true);
                errorMessage.setText("Email already used");
                return;
            }
            Person.updateSave(Auth.getLoggedInUser());
        }else{
            if(!((Participant)Auth.getLoggedInUser()).updateProfile(name, email)){
                errorMessage.setVisible(true);
                errorMessage.setText("Email already used");
                return;
            }
            Person.updateSave(Auth.getLoggedInUser());
        }
        this.back(event);
    }
    public void back(ActionEvent event){
        try{
            String fxmlPath = "/org/example/utspemrogramanlanjut/fxml/ParticipantMainMenu.fxml";
            if(Auth.getLoggedInUser() instanceof Speaker){
                fxmlPath = "/org/example/utspemrogramanlanjut/fxml/SpeakerMainMenu.fxml";
            }
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            errorMessage.setVisible(true);
            errorMessage.setText(e.getMessage());
        }
    }
}
