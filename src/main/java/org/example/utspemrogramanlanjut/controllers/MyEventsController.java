package org.example.utspemrogramanlanjut.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.utspemrogramanlanjut.models.Event;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.services.Auth;
import org.example.utspemrogramanlanjut.utils.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyEventsController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private Label fallbackText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Person currentPerson = Auth.getLoggedInUser();
        Data data = new Data();
//        ArrayList<Event> events = data.searchEventListByPerson(currentPerson);
//        if(events.isEmpty()) {
//            return;
//        }
        String[] temps = {"Satu", "Dua", "Tiga"};
        // loop each event
        for(String temp : temps) {
            fallbackText.setOpacity(0);
//            Label label = new Label();
//            label.setText(event.getName());

            Label label = new Label();
            label.setText(temp);
            label.setFont(new Font("Arial", 20));
            Hyperlink link = new Hyperlink();
            link.setText("See detail");
            link.setOnAction(e -> {
                System.out.println(temp);
                // Event.setSelectedEvent(event);
            });
            link.setFont(new Font("Arial", 15));

            HBox hbox = new HBox();
            hbox.getChildren().addAll(label, link);
            hbox.setSpacing(300);
            hbox.setAlignment(Pos.CENTER);

            container.setSpacing(10);
            container.getChildren().addAll(hbox);
        }

    }
}
