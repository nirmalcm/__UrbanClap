package com.webcreo.nirmal.mukthi.temp.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.webcreo.nirmal.mukthi.temp.activity.ActivityLoginPhone;

import java.util.HashMap;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

public class ManagerSession
{
    SharedPreferences sharedPreferences;
    Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PERMISSION_ALL = "PERMISSION_ALL";
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PREF_NAME = "ACCOUNT";

    private static final String PREF_NAME_INDIVIDUAL = "ACCOUNT_INDIVIDUAL";

    private static final String PREF_NAME_MANAGER = "ACCOUNT_MANAGER";

    private static final String PREF_NAME_USER = "ACCOUNT_USER";

    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_USER_FIRSTNAME = "firstname";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_USER_LASTNAME = "lastname";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_ID = "id";

    public static final String KEY_IMAGESTRING = "imageString";

    public static final String KEY_HASLOCATION = "haveLocation";

    public static final String KEY_HASFAVORITES = "haveFavorites";
    public static final String KEY_IS_SKIPPED = "isSkipped";

    private static final String IS_DATABASE_CREATED = "isDatabaseCreated";
    private static final String IS_HARDCODE_PRODUCTS_ADDED = "isHardcodeProductsAdded";

    public static final String KEY_NAME = "name";
    public static final String KEY_PROFILEPICTUREPATH = "profilePicturePath";

    private static final String HAS_PROFILE_PICTURE = "HasProfilePicture";

    public ManagerSession(Context context)
    {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.putBoolean(PERMISSION_ALL,false);
    }

    public ManagerSession(Context context, int type)
    {
        this.context = context;

        if (type == 0)
            sharedPreferences = this.context.getSharedPreferences(PREF_NAME_MANAGER, PRIVATE_MODE);
        else if (type == 1)
            sharedPreferences = this.context.getSharedPreferences(PREF_NAME_INDIVIDUAL, PRIVATE_MODE);
        else if (type == 2)
            sharedPreferences = this.context.getSharedPreferences(PREF_NAME_USER, PRIVATE_MODE);

        editor = sharedPreferences.edit();
        editor.putBoolean(PERMISSION_ALL,false);
    }

    public boolean Login(String email, String password)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
        return true;
    }

    public boolean Login(String id, String firstName, String lastName, String email, String password)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_USER_FIRSTNAME, firstName);
        editor.putString(KEY_USER_LASTNAME, lastName);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
        return true;
    }

    public boolean login(String phoneNumber)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.commit();
        return true;
    }

    public boolean setUserEmail(String email)
    {
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
        return true;
    }
    /*public boolean logout()
    {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, ActivityLoginEmail.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        return true;
    }*/

    public boolean setProfileName(String profileName)
    {
        editor.putString(KEY_NAME,profileName);
        editor.commit();
        return true;
    }

    public String getProfileName()
    {
        return sharedPreferences.getString(KEY_NAME,null);
    }

    public boolean setLocation(String location)
    {
        setHasLocation(true);
        editor.putString(KEY_LOCATION,location);
        editor.commit();
        return true;
    }

    public String getLocation()
    {
        if(getHasLocation()){
            return sharedPreferences.getString(KEY_LOCATION,null);
        }
        else {
            return "Getting location...";
        }
    }

    public boolean getHasLocation()
    {
        return sharedPreferences.getBoolean(KEY_HASFAVORITES,false);
    }

    public void setHasLocation(boolean haveLocation)
    {
        editor.putBoolean(KEY_HASLOCATION,haveLocation);
        editor.commit();
    }

    public boolean setProfilePicturePath(String profilePicturePath)
    {
        editor.putString(KEY_PROFILEPICTUREPATH,profilePicturePath);
        editor.commit();
        return true;
    }

    public String getProfilePicturePath()
    {
        return sharedPreferences.getString(KEY_PROFILEPICTUREPATH,null);
    }

    public boolean setImageString(String imageString)
    {
        editor.putString(KEY_IMAGESTRING,imageString);
        editor.commit();
        return true;
    }

    public String getImageString()
    {
        return sharedPreferences.getString(KEY_IMAGESTRING,null);
    }

    public boolean getHasFavorites()
    {
        return sharedPreferences.getBoolean(KEY_HASFAVORITES,false);
    }

    public void setHasFavorites(boolean haveFavorites)
    {
        editor.putBoolean(KEY_HASFAVORITES,true);
        editor.commit();
    }

    public boolean isSkipped()
    {
        return sharedPreferences.getBoolean(KEY_IS_SKIPPED,false);
    }

    public void setIsSkipped(boolean isSkipped)
    {
        editor.putBoolean(KEY_IS_SKIPPED,isSkipped);
        editor.commit();
    }

    public String getUserEmail()
    {
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }

    public String getUserFullName()
    {
        return sharedPreferences.getString(KEY_USER_FIRSTNAME,null)+" "+sharedPreferences.getString(KEY_USER_LASTNAME,null);
    }

    public String getUserId()
    {
        return sharedPreferences.getString(KEY_USER_ID,null);
    }

    public void checkLogin()
    {
        if(!this.isLoggedIn())
        {
            Intent i = new Intent(context, ActivityLoginPhone.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String, String> GetUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USER_FIRSTNAME, sharedPreferences.getString(KEY_USER_FIRSTNAME, null));
        user.put(KEY_USER_LASTNAME, sharedPreferences.getString(KEY_USER_LASTNAME, null));
        user.put(KEY_USER_EMAIL, sharedPreferences.getString(KEY_USER_EMAIL, null));
        user.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));
        return user;
    }

    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}