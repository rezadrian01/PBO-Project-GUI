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
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.services.Auth;
import org.example.utspemrogramanlanjut.utils.Data;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class CreateNewEventController implements Initializable {
    @FXML
    private Label errorMessage;
    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventLocationField;
    @FXML
    private TextArea eventDescriptionArea;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextField eventStartTimeField;
    @FXML
    private TextField eventEndTimeField;
    @FXML
    private Hyperlink backButton;


    public void createNewEventSubmit(ActionEvent event) {
        Data data = new Data();

        if(eventDatePicker.getValue() == null) {
            errorMessage.setVisible(true);
            errorMessage.setText("Please select a date");
            return;
        }

        String eventId = UUID.randomUUID().toString();
        String eventName = eventNameField.getText();
        String eventLocation = eventLocationField.getText();
        String eventDescription = eventDescriptionArea.getText();
        LocalDate eventDate = eventDatePicker.getValue();
        String formattedEventDate = eventDate.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        String eventStartTime = eventStartTimeField.getText();
        String eventEndTime = eventEndTimeField.getText();

        // Validation
        if(eventName.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty() || formattedEventDate.isEmpty() || eventStartTime.isEmpty() || eventEndTime.isEmpty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Please enter a valid event");
            return;
        }

        Event newEvent = new Event(eventId, eventName, eventDescription, eventLocation, formattedEventDate, eventStartTime, eventEndTime);
        newEvent.addSpeaker((Speaker)Auth.getLoggedInUser());
        data.addEventData(newEvent);
        this.back(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessage.setVisible(false);
    }

    public void back(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/SpeakerMainMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
