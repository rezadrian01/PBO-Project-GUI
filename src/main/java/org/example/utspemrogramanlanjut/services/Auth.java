package org.example.utspemrogramanlanjut.services;

import org.example.utspemrogramanlanjut.models.Participant;
import org.example.utspemrogramanlanjut.models.Person;
import org.example.utspemrogramanlanjut.models.Speaker;

public class Auth {
    private static Person loggedInUser = null;

    public boolean register(String name, String email, String password, String role, String expertise){
        Person existingPerson = Person.searchPersonByEmail(email);

        // If person with that email already used then return false
        if(existingPerson != null){
            return false;
        }
        // If this person is participant
        if(role.trim().equalsIgnoreCase("participant")){
            Participant participant = new Participant(name, email, password);
            Person.save(participant);
        }else{
            Speaker speaker = new Speaker(name, email, password, expertise);
            Person.save(speaker);
        }

        return true;
    }

    public boolean login(String email, String password){
        Person existingPerson = Person.searchPersonByEmail(email);
        // If person with that email not found then return false
        if(existingPerson == null){
            return false;
        }
        // Check password
        if(!existingPerson.validatePassword(password)){
            return false;
        }
        Auth.loggedInUser = existingPerson;
        return true;
    }

    public static void logout(){
        Auth.loggedInUser = null;
    }

    public static Person getLoggedInUser(){
        return Auth.loggedInUser;
    }
}
