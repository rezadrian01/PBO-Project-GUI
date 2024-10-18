package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.models.Event;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventDetailController implements Initializable {
    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventLocationField;
    @FXML
    private TextField eventDateField;
    @FXML
    private TextArea eventDescriptionArea;
    @FXML
    private TextField eventStartTimeField;
    @FXML
    private TextField eventEndTimeField;
    @FXML
    private Hyperlink backButton;
    @FXML
    private Label errorMessage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String eventName = Event.getSelectedEvent().getName();
        String eventLocation = Event.getSelectedEvent().getLocation();
        String eventDate = Event.getSelectedEvent().getDate();
        String eventDescription = Event.getSelectedEvent().getDescription();
        String eventStartTime = Event.getSelectedEvent().getStartTime();
        String eventEndTime = Event.getSelectedEvent().getEndTime();

        eventNameField.setText(eventName);
        eventLocationField.setText(eventLocation);
        eventDateField.setText(eventDate);
        eventDescriptionArea.setText(eventDescription);
        eventStartTimeField.setText(eventStartTime);
        eventEndTimeField.setText(eventEndTime);

        errorMessage.setVisible(false);
    }

    public void back(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/MyEvents.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void joinEventSubmit(ActionEvent event) {

    }
}
