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
import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.services.Auth;

import java.net.URL;
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
    @FXML
    private Button joinButton;

    private static String menuBefore;

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
        if(!EventDetailController.menuBefore.equals("allEvents")){
            joinButton.setVisible(false);
        }
        if(Auth.getLoggedInUser() instanceof Speaker){
            joinButton.setText("Join as speaker");
        }
    }

    public void back(ActionEvent event) {
        try{
            String fxmlPath = "/org/example/utspemrogramanlanjut/fxml/MyEvents.fxml";
            if(EventDetailController.menuBefore.equals("allEvents")){
                fxmlPath = "/org/example/utspemrogramanlanjut/fxml/AllEvents.fxml";
            }
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static String getBeforeMenu(){
        return EventDetailController.menuBefore;
    }
    public static void setBeforeMenu(String menu){
        EventDetailController.menuBefore = menu;
    }
    public void joinEventSubmit(ActionEvent event) {
        Event currentEvent = Event.getSelectedEvent();
        if(Auth.getLoggedInUser() instanceof Speaker){
            currentEvent.addSpeaker((Speaker)Auth.getLoggedInUser());
        }else{
            currentEvent.addParticipant((Participant)Auth.getLoggedInUser());
        }
        Event.updateSave(currentEvent);
        this.back(event);
    }
}
