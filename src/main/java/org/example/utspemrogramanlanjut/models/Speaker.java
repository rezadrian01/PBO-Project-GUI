package org.example.utspemrogramanlanjut.models;

import org.example.utspemrogramanlanjut.interfaces.Printable;
import org.example.utspemrogramanlanjut.interfaces.Role;
import org.json.JSONObject;

public class Speaker extends Person implements Role, Printable {
    private String expertise;

    public Speaker(String name, String email, String password, String expertise){
        super(name, email, password);
        this.expertise = expertise;
        this.role = "Speaker";
    }

    public String getExpertise() {
        return this.expertise;
    }
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @Override
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("email", this.getEmail());
        json.put("password", this.getPassword());
        json.put("expertise", this.getExpertise());
        json.put("role", this.getRole());
        return json;
    }

    @Override
    public String getRole(){
        return this.role;
    }
    @Override
    public void printTicket(Event event){
        System.out.println("Ticket for speaker " + this.getName() + " on event " + event.getName());
    }
}
