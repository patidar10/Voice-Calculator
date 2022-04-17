package com.example.skisan;

public class userdata {
    String number;
    String name;
    String occupation;
    String address;
    String tradertype;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTradertype() {
        return tradertype;
    }

    public void setTradertype(String tradertype) {
        this.tradertype = tradertype;
    }

    public userdata(String number, String name, String occupation, String address, String tradertype) {
        this.number = number;
        this.name = name;
        this.occupation = occupation;
        this.address = address;
        this.tradertype=tradertype;
    }
    public userdata()
    {

    }
}
