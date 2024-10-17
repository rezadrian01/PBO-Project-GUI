package org.example.utspemrogramanlanjut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    public void loginSubmit(ActionEvent event) {
        System.out.println(email.getText());
        System.out.println(password.getText());
        // Validation
    }
}
