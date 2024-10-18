package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.utils.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Event {
    private final String id;
    private String name;
    private String description;
    private String location;
    private String date;
    private String startTime;
    private String endTime;

    private ArrayList<String> participants = new ArrayList<String>();
    private ArrayList<String> speakers = new ArrayList<String>();

    public Event(String name, String description, String location, String date, String startTime, String endTime) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateEvent(String name, String description, String location, String date, String startTime, String endTime) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("description", this.description);
        json.put("location", this.location);
        json.put("date", this.date);
        json.put("startTime", this.startTime);
        json.put("endTime", this.endTime);


        return json;
    }

    public void addParticipant(Participant participant){
        this.participants.add(participant.getId());
    }
    // Overloading
    public void addParticipant(String name, String email, String password){
        Participant participant = new Participant(name, email, password);
        this.participants.add(participant.getId());
    }

    public void addSpeaker(Speaker speaker){
        this.speakers.add(speaker.getId());
    }
    // Overloading
    public void addSpeaker(String name, String email, String password, String expertise){
        Speaker speaker = new Speaker(name, email, password, expertise);
        this.speakers.add(speaker.getId());
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLocation(){
        return this.location;
    }
    public String getDate(){
        return this.date;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.endTime;
    }

    public ArrayList<Participant> getParticipants(){
        Data data = new Data();
        ArrayList<Participant> participants = new ArrayList<Participant>();
        for(String id: this.participants) {
            participants.add(data.searchParticipantById(id));
        }
        return participants;
    }
    public ArrayList<Speaker> getSpeakers(){
        Data data = new Data();
        ArrayList<Speaker> speakers = new ArrayList<Speaker>();
        for(String id: this.speakers) {
            speakers.add(data.searchSpeakerById(id));
        }
        return speakers;
    }
}
