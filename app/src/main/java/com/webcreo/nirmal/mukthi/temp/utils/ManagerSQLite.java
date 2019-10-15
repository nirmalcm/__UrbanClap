package com.webcreo.nirmal.mukthi.temp.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManagerSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "cart";

    private static final String TABLE_CART = "table_cart";

    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_ITEM_ID = "item_id";

    public ManagerSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM_ID + " TEXT"+ ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public boolean addToCart(String categoryId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID,categoryId);

        db.insert(TABLE_CART, null, values);
        db.close();

        return true;
    }

    public boolean isInCart(String categoryId)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String Query = "select * from "+TABLE_CART+" where "+ KEY_ITEM_ID +" ='"+ categoryId+"'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isCartEmpty()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select * from "+TABLE_CART;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return false;
        }
        else{
            return true;
        }
    }

    public List<String> getItemsIdsInCart(){

        //Get all Categorys with given user id from favorites table
        List<String> categoryIds = new ArrayList<>();

        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select * from "+TABLE_CART;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){

            if (cursor.moveToFirst()) {
                do {
                    String categoryId = cursor.getString(2);
                    categoryIds.add(categoryId);
                } while (cursor.moveToNext());
            }
        }
        else{
            cursor.close();
            return null;
        }

        return categoryIds;
    }

    public boolean removeFromCart(String CategoryId) {

        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select * from "+TABLE_CART+" where "+ KEY_ITEM_ID +"='"+ CategoryId+"'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(KEY_ITEM_ID);
            String id = cursor.getString(columnIndex);
            db.delete(TABLE_CART, KEY_ITEM_ID + " = ?",
                    new String[] { String.valueOf(id) });
            db.close();
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }
}