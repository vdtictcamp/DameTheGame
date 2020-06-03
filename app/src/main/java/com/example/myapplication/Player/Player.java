package com.example.myapplication.Player;

public class Player {

    String password;
    String name;

    public Player(String name, String password){
        this.name=name;
        this.password=password;
    }


    public void setPassword(String password){
        this.password=password;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

}
