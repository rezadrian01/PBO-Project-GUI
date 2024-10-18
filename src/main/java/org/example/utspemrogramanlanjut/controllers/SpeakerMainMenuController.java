package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpeakerMainMenuController {
    public void seeAllEvents(){

    }

    public void seeMyEvents(){

    }
    public void myAccount(){

    }
    public void createNewEvent(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/CreateNewEvent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
