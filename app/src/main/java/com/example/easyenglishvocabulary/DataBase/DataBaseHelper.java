package com.example.easyenglishvocabulary.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.easyenglishvocabulary.MyApplication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public  static String dbName="eev.db";

    public DataBaseHelper(Context context) {
        super(context,dbName,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        ActiveRecord[] activeRecords = MyApplication.getModelsClass();
        for (ActiveRecord activeRecord: activeRecords) {
            String tableName = activeRecord.getTableName();
            HashMap<String, String> columns = activeRecord.getColumnsTypes();
            String command = "CREATE TABLE " + tableName + " (";

            Iterator<Map.Entry<String, String>> iterator = columns.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> column = iterator.next();
                command += column.getKey() + " " + column.getValue();
                if (iterator.hasNext()) {
                    command += ",";
                }
            }

            command += ");";

            db.execSQL(command);

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
