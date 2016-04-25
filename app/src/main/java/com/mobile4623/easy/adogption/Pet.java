package com.mobile4623.easy.adogption;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Guille on 4/10/2016.
 */
public class Pet implements Parcelable {

    // to consider: price, characteristics?
    private String petID="";
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

    //  parcelable constructor, Parcel must be built in this order
    private Pet(Parcel in) {
        petID = in.readString();
        name = in.readString();
        breed = in.readString();
        age = in.readString();
        animal = in.readString();
        picture = in.readString();
        description = in.readString();
        location = in.readString();
    }

    public void setPetID(String id){petID = id;}
    public void setName(String n){name = n;}
    public void setBreed(String b){breed = b;}
    public void setAge(String a){age = a;}
    public void setAnimal(String a){animal = a;}
    public void setPicture(String p){picture = p;}
    public void setDescription(String d){description = d;}
    public void setLocation(String l){location = l;}

    public String getPetID(){return petID;}
    public String getName() {return name;}
    public String getBreed() {return breed;}
    public String getAge() {return age;}
    public String getAnimal() {return animal;}
    public String getPicture() {return picture;}
    public String getDescription() {return description;}
    public String getLocation() {return location;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(petID);
        dest.writeString(name);
        dest.writeString(breed);
        dest.writeString(age);
        dest.writeString(animal);
        dest.writeString(picture);
        dest.writeString(description);
        dest.writeString(location);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>() {
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

}
