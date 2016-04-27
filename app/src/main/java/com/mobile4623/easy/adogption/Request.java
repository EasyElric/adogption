package com.mobile4623.easy.adogption;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guille on 4/26/2016.
 */
public class Request implements Parcelable{

    private String sender="";
    private String receiver="";
    private String message ="";

    public Request(){
        //constructor code
    }

    //  parcelable constructor, Parcel must be built in this order
    private Request(Parcel in) {
        sender = in.readString();
        receiver = in.readString();
        message = in.readString();
    }

    public void setSender(String id){sender = id;}
    public void setReceiver(String n){receiver = n;}
    public void setMessage(String b){message = b;}

    public String getSender(){return sender;}
    public String getReceiver() {return receiver;}
    public String getMessage() {return message;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(receiver);
        dest.writeString(message);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
