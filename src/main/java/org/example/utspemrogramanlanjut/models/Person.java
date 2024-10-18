package org.example.utspemrogramanlanjut.models;

import org.json.JSONObject;

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

    public String getId() {
        return id;
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

}
