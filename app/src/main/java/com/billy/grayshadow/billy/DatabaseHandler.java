package com.billy.grayshadow.billy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrayShadow on 12/22/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbItemPrice";

    // Contacts table name
    private static final String TABLE_PRICE_LIST = "priceList";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("MOHIT", "5");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PRICE_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRICE + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE_LIST);
        Log.e("MOHIT", "6");

        // Create tables again
        onCreate(db);
    }

    public void deleteData()
    {
        Log.e("MOHIT", "7");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRICE_LIST);
    }

    /*public void onOpen(SQLiteDatabase db)
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE FROM " + TABLE_PRICE_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE_LIST);
        onCreate(db);
    }*/

    // Adding new contact
    public void addPrice(int id, float price)
    {
        Log.e("MOHIT", "8");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_PRICE, price); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_PRICE_LIST, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public float getPrice(int id)
    {
        Log.e("MOHIT", "9");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRICE_LIST, new String[] { KEY_ID,
                        KEY_PRICE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // return contact
            return Float.parseFloat(cursor.getString(1));
        }
        return 0.0f;
    }

    // Getting All Contacts
    public List<Float> getAllPrices()
    {
        Log.e("MOHIT", "10");
        List<Float> priceList = new ArrayList<Float>();
        float floatTempPrice;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRICE_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                floatTempPrice = Float.parseFloat(cursor.getString(1));
                // Adding contact to list
                priceList.add(floatTempPrice);
            } while (cursor.moveToNext());
        }

        // return contact list
        return priceList;
    }

    // Getting contacts Count
    public int getPricesCount()
    {
        Log.e("MOHIT", "11");
        int count=0;
        String countQuery = "SELECT  * FROM " + TABLE_PRICE_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}