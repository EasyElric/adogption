package com.mobile4623.easy.adogption;

/**
 * Created by Guille on 4/10/2016.
 */
public class Pet {

    // to consider: price, characteristics?
    private String name ="";
    private String breed="";
    private String age="";
    private String animal="";
    private String picture="";
    private String description="";
    private String location="";

    public Pet(){
        //constructor code
    }

    public void setName(String n){name = n;}
    public void setBreed(String b){name = b;}
    public void setAge(String a){name = a;}
    public void setAnimal(String a){name = a;}
    public void setPicture(String p){name = p;}
    public void setDescription(String d){name = d;}
    public void setLocation(String l){name = l;}

    public String getName() {return name;}
    public String getBreed() {return breed;}
    public String getAge() {return age;}
    public String getAnimal() {return animal;}
    public String getPicture() {return picture;}
    public String getDescription() {return description;}
    public String getLocation() {return location;}
}
