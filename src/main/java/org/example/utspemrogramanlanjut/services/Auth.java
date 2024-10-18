package org.example.utspemrogramanlanjut.services;

import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;
import org.example.utspemrogramanlanjut.utils.Data;

public class Auth {
    private Person loggedInUser = null;
    private final Data data = new Data();

    public boolean register(String name, String email, String password, String role, String expertise){
        Person existingPerson = data.searchPersonByEmail(email);

        // If person with that email already used then return false
        if(existingPerson != null){
            return false;
        }

        // If this person is participant
        if(role.trim().equals("participant")){
            Participant participant = new Participant(name, email, password);
        }else{
            Speaker speaker = new Speaker(name, email, password, expertise);
        }

        return true;
    }

    public boolean login(String email, String password){
        Person existingPerson = data.searchPersonByEmail(email);
        // If person with that email not found then return false
        if(existingPerson == null){
            return false;
        }

        // Check password
        if(!existingPerson.validatePassword(password)){
            return false;
        }

        this.loggedInUser = existingPerson;
        return true;
    }

    public void logout(){
        this.loggedInUser = null;
    }
}
