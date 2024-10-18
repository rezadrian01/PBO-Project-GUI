package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.utspemrogramanlanjut.models.Event;
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


    public void createNewEventSubmit(ActionEvent event) {
        Data data = new Data();

        String eventId = UUID.randomUUID().toString();
        String eventName = eventNameField.getText();
        String eventLocation = eventLocationField.getText();
        String eventDescription = eventDescriptionArea.getText();
        LocalDate eventDate = eventDatePicker.getValue();
        String formattedEventDate = eventDate.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        String eventStartTime = eventStartTimeField.getText();
        String eventEndTime = eventEndTimeField.getText();

        // Validation

        Event newEvent = new Event(eventId, eventName, eventDescription, eventLocation, formattedEventDate, eventStartTime, eventEndTime);
        data.addEventData(newEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessage.setVisible(false);
    }
}
