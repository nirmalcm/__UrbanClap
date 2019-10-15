package com.webcreo.nirmal.mukthi.temp.model;

import java.io.Serializable;

public class ModelHomeService implements Serializable {

    private String location;
    private String type;
    private String price;
    private String size;

    private boolean hasLocation = false;
    private boolean hasType = false;
    private boolean hasPrice = false;
    private boolean hasSize = false;

    public ModelHomeService()
    {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        this.hasLocation = true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.hasType = true;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        this.hasPrice = true;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
        this.hasSize = true;
    }

    public boolean isHasLocation() {
        return hasLocation;
    }

    public boolean isHasType() {
        return hasType;
    }

    public boolean isHasPrice() {
        return hasPrice;
    }

    public boolean isHasSize() {
        return hasSize;
    }
}