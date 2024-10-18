package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.utils.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Person {
    private final String id;
    private String name;
    private String email;
    private final String password;
    protected String role;

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }
    public Person(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    protected String getPassword(){
        return this.password;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("email", this.email);
        json.put("password", this.password);
        return json;
    }

    public boolean validatePassword(String password){
        return this.password.equals(password);
    }

    // Static methods
    public static void save(Person person){
        JSONArray jsonArray;
        try{
            JSONObject json;
            if(person instanceof Speaker){
                json = ((Speaker)person).toJSON();
            }else{
                json = ((Participant)person).toJSON();
            }

            // Get all person from file
            String personContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Persons.json")));
            // If file Persons.json doesn't exist then create empty jsonArray
            if(personContent.isEmpty()){
                jsonArray = new JSONArray();
            }else{
                jsonArray = new JSONArray(personContent);
            }
            // Add new person to file
            jsonArray.put(json);
            FileWriter fileWriter = new FileWriter(Data.jsonPath + "Persons.json");
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Person searchPersonByEmail(String email){
        try{
            String personContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Persons.json")));
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
                        Participant participant = new Participant(person.getString("id"), person.getString("name"), person.getString("email"), person.getString("password"));
                        return participant;
                    }else{
                        Speaker speaker = new Speaker(person.getString("id"), person.getString("name"), person.getString("email"), person.getString("password"), person.getString("expertise"));
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
