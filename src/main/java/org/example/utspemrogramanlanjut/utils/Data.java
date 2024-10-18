package org.example.utspemrogramanlanjut.utils;

import org.example.utspemrogramanlanjut.models.Event;
import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Data {
    private final String jsonPath = "src/main/resources/org/example/utspemrogramanlanjut/json/";

    public ArrayList<Event> getEventList(){
        ArrayList<Event> resultEventList = new ArrayList<Event>();
        try{
            String eventContent = new String(Files.readAllBytes(Paths.get(jsonPath + "Events.json")));
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
                    String participant = participants.getString(j);
                    participants.put(this.searchParticipantById(participant));
                }
                // Add all speakers
                JSONArray speakers = eventObject.getJSONArray("speakers");
                for(int j = 0; j < speakers.length(); j++){
                    String speaker = speakers.getString(j);
                    speakers.put(this.searchSpeakerById(speaker));
                }
                resultEventList.add(event);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultEventList;
    }

    public void addEventsData(Event event){
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

            // Add all speakers
            JSONArray speakers = new JSONArray();
            for(Speaker s : event.getSpeakers()){
                String speaker = s.getId();
                speakers.put(speaker);
            }

            String eventContent = new String(Files.readAllBytes(Paths.get(jsonPath + "Events.json")));
            if(eventContent.isEmpty()){
                jsonArray = new JSONArray();
            }else{
                jsonArray = new JSONArray(eventContent);
            }
            jsonArray.put(json);
            FileWriter fileWriter = new FileWriter(this.jsonPath + "Events.json");
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addPersonsData(Person person){
        JSONArray jsonArray;
        try{
            JSONObject json;
        if(person instanceof Speaker){
            json = ((Speaker)person).toJSON();
        }else{
            json = ((Participant)person).toJSON();
        }

            // Get all person from file
            String personContent = new String(Files.readAllBytes(Paths.get(this.jsonPath + "Persons.json")));
            // If file Persons.json doesn't exist then create empty jsonArray
            if(personContent.isEmpty()){
                jsonArray = new JSONArray();
            }else{
                jsonArray = new JSONArray(personContent);
            }
            // Add new person to file
            jsonArray.put(json);
            FileWriter fileWriter = new FileWriter(this.jsonPath + "Persons.json");
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Participant searchParticipantById(String id){
        try{
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            String personContent = new String(Files.readAllBytes(Paths.get(this.jsonPath + "Persons.json")));
            if(personContent.isEmpty()){
                return null;
            }
            JSONArray personLists = new JSONArray(personContent);

            for(int i = 0; i < personLists.length(); i++){
                JSONObject person = personLists.getJSONObject(i);
                if(person.getString("id").equals(id)){

                    if(person.getString("role").equals("Participant")){
                        Participant participant = new Participant(person.getString("name"), person.getString("email"), person.getString("password"));
                        return participant;
                    }

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Speaker searchSpeakerById(String id){
        try{
            String personContent = new String(Files.readAllBytes(Paths.get(this.jsonPath + "Persons.json")));
            if(personContent.isEmpty()){
                return null;
            }
            JSONArray personLists = new JSONArray(personContent);

            for(int i = 0; i < personLists.length(); i++){
                JSONObject person = personLists.getJSONObject(i);
                if(person.getString("id").equals(id)){

                    if(person.getString("role").equals("Speaker")){
                        Speaker speaker = new Speaker(person.getString("name"), person.getString("email"), person.getString("password"), person.getString("expertise"));
                        return speaker;
                    }

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Person searchPersonByEmail(String email){
        try{
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            String personContent = new String(Files.readAllBytes(Paths.get(this.jsonPath + "Persons.json")));
            // If file Persons.json doesn't exist then return null
            if(personContent.isEmpty()){
                return null;
            }
            JSONArray personLists = new JSONArray(personContent);

            // Search person by email
            for(int i = 0; i < personLists.length(); i++){
                JSONObject person = personLists.getJSONObject(i);
                if(person.getString("email").equals(email)){
                    // create new person object
                    if(person.getString("role").equals("Participant")){
                        Participant participant = new Participant(person.getString("name"), person.getString("email"), person.getString("password"));
                        return participant;
                    }else{
                        Speaker speaker = new Speaker(person.getString("name"), person.getString("email"), person.getString("password"), person.getString("expertise"));
                        return speaker;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
