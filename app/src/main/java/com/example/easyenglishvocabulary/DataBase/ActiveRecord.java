package com.example.easyenglishvocabulary.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.easyenglishvocabulary.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ActiveRecord {

    boolean isNewRecord = true;

    protected abstract String getTableName();
    public abstract String[] getColumns();
    protected abstract HashMap<String, String> getColumnsTypes();
    public abstract boolean save();
    public abstract boolean delete();

    protected long saveToDB(HashMap<String, Object> values, long id) {
        SQLiteDatabase db = getDatabase();
        if (db == null) return -1;
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, Object> value: values.entrySet()) {
            contentValues.put(value.getKey(),value.getValue().toString());
        }
        if (isNewRecord) {
            return db.insert(getTableName(), null, contentValues);
        } else {
            long rowAffected = db.update(getTableName(), contentValues, "id = " + id, null);
            return rowAffected == 1 ? id : -1;
        }
    }

    protected boolean delete(long id){
        SQLiteDatabase db = getDatabase();
        if (db == null || isNewRecord) return false;
        db.beginTransaction();
        int rowAffected = db.delete(getTableName(),"id = " + id, null);
        if (rowAffected > 1){
            Log.e("database", "delete failure", new Exception("При удалении одной записи было затронуто несколько."));
        } else {
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        return (rowAffected == 1);
    }


    protected SQLiteDatabase getDatabase(){
        SQLiteDatabase db = null;
        try {
            db = MyApplication.getDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }

}
