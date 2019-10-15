package com.webcreo.nirmal.mukthi.temp.model;

import android.content.Context;

import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelRealEstate implements Serializable {

    private String location;
    private String type;
    private String price;
    private String size;
    private String score;

    private boolean hasLocation = false;
    private boolean hasType = false;
    private boolean hasPrice = false;
    private boolean hasSize = false;
    private boolean hasScore = false;

    public ModelRealEstate()
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
        this.hasScore = true;
    }

    public boolean isHasScore() {
        return hasScore;
    }
}