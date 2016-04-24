package com.mobile4623.easy.adogption;

import java.io.Serializable;

/**
 * Created by Guille on 4/10/2016.
 */
public class Pet implements Serializable {

    // to consider: price, characteristics?
    private String name ="";
    private String breed="";
    private String age="";
    private String animal="";
    private String picture="";
    private String description="";
    private String location="";
    private String petID="";

    public Pet(){
        //constructor code
    }

    public void setName(String n){name = n;}
    public void setBreed(String b){breed = b;}
    public void setAge(String a){age = a;}
    public void setAnimal(String a){animal = a;}
    public void setPicture(String p){picture = p;}
    public void setDescription(String d){description = d;}
    public void setLocation(String l){location = l;}
    public void setPetID(String id){petID = id;}

    public String getName() {return name;}
    public String getBreed() {return breed;}
    public String getAge() {return age;}
    public String getAnimal() {return animal;}
    public String getPicture() {return picture;}
    public String getDescription() {return description;}
    public String getLocation() {return location;}
    public String getPetID(){return petID;}
}
