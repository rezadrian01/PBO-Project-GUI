package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.utils.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

    private static Event selectedEvent;

    public Event(String id, String name, String description, String location, String date, String startTime, String endTime) {
        this.id = id;
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
            participants.add(Participant.searchParticipantById(id));
        }
        return participants;
    }
    public ArrayList<Speaker> getSpeakers(){
        Data data = new Data();
        ArrayList<Speaker> speakers = new ArrayList<>();
        for(String id: this.speakers) {
            speakers.add(Speaker.searchSpeakerById(id));
        }
        return speakers;
    }

    // Static method

    public static void setSelectedEvent(Event selectedEvent) {
        Event.selectedEvent = selectedEvent;
    }

    public static Event getSelectedEvent() {
        return Event.selectedEvent;
    }

    public static ArrayList<Event> getEventList(){
        ArrayList<Event> resultEventList = new ArrayList<>();
        try{
            String eventContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Events.json")));
            if(eventContent.isEmpty()){
                return null;
            }
            JSONArray eventList = new JSONArray(eventContent);

            for(int i = 0; i < eventList.length(); i++){
                JSONObject eventObject = eventList.getJSONObject(i);
                Event event = new Event(eventObject.getString("id"),
                        eventObject.getString("name"),
                        eventObject.getString("description"),
                        eventObject.getString("location"),
                        eventObject.getString("date"),
                        eventObject.getString("startTime"),
                        eventObject.getString("endTime")
                );
                // Add all participants
                JSONArray participants = eventObject.getJSONArray("participants");
                for(int j = 0; j < participants.length(); j++){
                    String participantId = participants.getString(j);
                    event.addParticipant(Participant.searchParticipantById(participantId));
                }


                // Add all speakers
                JSONArray speakers = eventObject.getJSONArray("speakers");
                for(int j = 0; j < speakers.length(); j++){
                    String speakerId = speakers.getString(j);
                    event.addSpeaker(Speaker.searchSpeakerById(speakerId));
                }
                resultEventList.add(event);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultEventList;
    }

    public static void save(Event event){
        JSONArray jsonArray;
        try{
            JSONObject json = new JSONObject();
            json.put("id", event.getId());
            json.put("name", event.getName());
            json.put("description", event.getDescription());
            json.put("location", event.getLocation());
            json.put("date", event.getDate());
            json.put("startTime", event.getStartTime());
            json.put("endTime", event.getEndTime());

            // Add all participants
            JSONArray participants = new JSONArray();
            for(Participant p : event.getParticipants()){
                String participant = p.getId();
                participants.put(participant);
            }
            json.put("participants", participants);
            // Add all speakers
            JSONArray speakers = new JSONArray();
            for(Speaker s : event.getSpeakers()){
                String speaker = s.getId();
                speakers.put(speaker);
            }
            json.put("speakers", speakers);

            String eventContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Events.json")));
            if(eventContent.isEmpty()){
                jsonArray = new JSONArray();
            }else{
                jsonArray = new JSONArray(eventContent);
            }
            jsonArray.put(json);
            FileWriter fileWriter = new FileWriter(Data.jsonPath + "Events.json");
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Event> searchEventListByPerson(Person person){
        ArrayList<Event> events = new ArrayList<>();
        try{
            String eventContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Events.json")));
            if(eventContent.isEmpty()){
                return null;
            }
            JSONArray eventLists = new JSONArray(eventContent);
            // Loop each event
            for(int i = 0; i < eventLists.length(); i++){
                JSONObject event = eventLists.getJSONObject(i);
                JSONArray personList;
                if(person instanceof Speaker){
                    personList = event.getJSONArray("speakers");
                }else{
                    personList = event.getJSONArray("participants");
                }
                // Loop each participant or speaker in current event
                for(int p = 0; p < personList.length(); p++){
                    // if id current participants match with current person then create new event object
                    if(personList.getString(p).equals(person.getId())){
                        // Create event object
                        Event e = new Event(event.getString("id"),
                                event.getString("name"),
                                event.getString("description"),
                                event.getString("location"),
                                event.getString("date"),
                                event.getString("startTime"),
                                event.getString("endTime"));
                        events.add(e);
                    }
                }
            }
            return events;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
