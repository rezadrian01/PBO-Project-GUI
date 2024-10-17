package org.example.utspemrogramanlanjut.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.Main;

public class WelcomeController {
    public void register(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/Register.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void login(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}