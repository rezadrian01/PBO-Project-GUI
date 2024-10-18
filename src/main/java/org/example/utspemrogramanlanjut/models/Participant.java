package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.interfaces.Printable;
import org.example.utspemrogramanlanjut.interfaces.Role;
import org.json.JSONObject;

public class Participant extends Person implements Role, Printable {

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

    @Override
    public String getRole(){
        return this.role;
    }

    @Override
    public void printTicket(Event event){
        System.out.println("Ticket for participant " + this.getName() + " on event " + event.getName());
    }
}
