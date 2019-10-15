package com.webcreo.nirmal.mukthi.temp.model;

import java.io.Serializable;
import java.util.List;

public class ModelUser implements Serializable {

    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userImage;

    private String userStatus;

    private boolean isSelected;

    private List<ModelBase> userServices;

    public ModelUser(){

    }

    public ModelUser(String userId, String userFirstName, String userLastName, String userEmail, String userPassword, String userImage){
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userImage = userImage;
    }

    public ModelUser(String userId, String userFirstName, String userLastName, String userEmail, String userPassword){
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public ModelUser(String userFirstName, String userLastName, String userEmail, String userPassword){
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public ModelUser(String userEmail, String userPassword)
    {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public void setUserEmail(String name){
        this.userEmail = name;
    }

    public String getUserPassword(){
        return this.userPassword;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public List<ModelBase> getUserServices() {
        return userServices;
    }

    public void setUserServices(List<ModelBase> userServices) {
        this.userServices = userServices;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}