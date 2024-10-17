package org.example.utspemrogramanlanjut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField email;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;

    public void registerSubmit(ActionEvent event){
        System.out.println(name.getText());
        System.out.println(email.getText());
        System.out.println(password.getText());

        // Validation
    }
}
