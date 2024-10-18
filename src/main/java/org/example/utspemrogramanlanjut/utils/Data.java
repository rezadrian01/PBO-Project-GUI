package org.example.utspemrogramanlanjut.utils;

import org.example.utspemrogramanlanjut.models.Event;
import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.InputStream;

public class Data {
    private String jsonPath = "src/main/resources/org/example/utspemrogramanlanjut/json/";

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
                JSONObject participant = p.toJSON();
                participants.put(participant);
            }

            // Add all speakers
            JSONArray speakers = new JSONArray();
            for(Speaker s : event.getSpeakers()){
                JSONObject speaker = s.toJSON();
                speakers.put(speaker);
            }

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Events.json");
            if(inputStream == null){
                jsonArray = new JSONArray();
            }else{
                String eventContent = new String(inputStream.readAllBytes());
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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            // If file Persons.json doesn't exist then create empty jsonArray
            if(inputStream == null){
                jsonArray = new JSONArray();
            }else{
                String personContent = new String(inputStream.readAllBytes());
                jsonArray = new JSONArray(personContent);
            }
            // Add new person to file
            jsonArray.put(json);
            FileWriter fileWriter = new FileWriter(this.jsonPath + "Persons.json");
            fileWriter.write(jsonArray.toString(4));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Participant searchParticipantById(String id){
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            if(inputStream == null){
                return null;
            }
            String personContent = new String(inputStream.readAllBytes());
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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            if(inputStream == null){
                return null;
            }
            String personContent = new String(inputStream.readAllBytes());
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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/Persons.json");
            // If file Persons.json doesn't exist then return null
            if(inputStream == null){
                return null;
            }
            String personContent = new String(inputStream.readAllBytes());
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
