package com.encryptionmanager;



//Class that is returned after a method is called
public class Callback {

    //Depending upon the status, it will either have a message or not.
    public Status status;
    public String message;

    //Callback class will only require a status (enum) and a message (data sent back).
    public Callback(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public enum Status {
        Success,
        //Add more errors here.
        Error
    }

}
