package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.interfaces.Role;
import org.example.utspemrogramanlanjut.services.Auth;
import org.example.utspemrogramanlanjut.utils.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Participant extends Person implements Role {

    public Participant(String name, String email, String password){
        super(name, email, password);
        this.role = "Participant";
    }
    public Participant(String id, String name, String email, String password){
        super(id, name, email, password);
        this.role = "Participant";
    }

    @Override
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("email", this.getEmail());
        json.put("password", this.getPassword());
        json.put("role", this.getRole());
        return json;
    }
    public boolean updateProfile(String name, String email){
        // Validation
        Person existingPerson = Person.searchPersonByEmail(email);
        // If current person change the email and person with that email already used then return false
        if(!Auth.getLoggedInUser().getEmail().equals(email) && existingPerson != null){
            return false;
        }

        this.name = name;
        this.email = email;
        return true;
    }

    @Override
    public String getRole(){
        return this.role;
    }

    // Static methods
    public static Participant searchParticipantById(String id){
        try{
            String personContent = new String(Files.readAllBytes(Paths.get(Data.jsonPath + "Persons.json")));
            if(personContent.isEmpty()){
                return null;
            }
            JSONArray personLists = new JSONArray(personContent);

            for(int i = 0; i < personLists.length(); i++){
                JSONObject person = personLists.getJSONObject(i);
                if(person.getString("id").equals(id)){
                    if(person.getString("role").equals("Participant")){
                        Participant participant = new Participant(person.getString("id"), person.getString("name"), person.getString("email"), person.getString("password"));
                        return participant;
                    }

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
