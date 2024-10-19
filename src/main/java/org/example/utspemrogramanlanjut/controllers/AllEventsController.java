package org.example.utspemrogramanlanjut.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.utspemrogramanlanjut.models.Event;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.services.Auth;
import org.example.utspemrogramanlanjut.utils.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllEventsController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private Label fallbackText;
    @FXML
    private Hyperlink backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Data data = new Data();
        ArrayList<Event> events = Event.getEventList();

        if (events.isEmpty()) {
            return;
        }
        fallbackText.setVisible(false);

        for (Event event : events) {
            Label label = new Label();
            label.setText(event.getName());
            label.setFont(new Font("Arial", 20));
            label.setAlignment(Pos.CENTER_LEFT);

            Hyperlink link = new Hyperlink();
            link.setText("See detail");
            link.setFont(new Font("Arial", 15));
            link.setAlignment(Pos.BASELINE_RIGHT);
            link.setOnAction(e -> {
                Event.setSelectedEvent(event);
                this.seeEventDetail(e);
            });

            HBox hbox = new HBox(label, link);
            HBox.setHgrow(label, Priority.ALWAYS);
            hbox.setSpacing(10);
            container.getChildren().add(hbox);
        }
    }

    public void seeEventDetail(ActionEvent event) {
        try{
            EventDetailController.setBeforeMenu("allEvents");
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/utspemrogramanlanjut/fxml/EventDetail.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void back(ActionEvent event) {
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
            e.printStackTrace();
        }

    }
}
