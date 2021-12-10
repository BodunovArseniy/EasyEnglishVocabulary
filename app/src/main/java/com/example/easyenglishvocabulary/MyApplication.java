package com.example.easyenglishvocabulary;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.easyenglishvocabulary.DataBase.ActiveRecord;
import com.example.easyenglishvocabulary.DataBase.DataBaseHelper;
import com.example.easyenglishvocabulary.DataBase.GlossaryModel;
import com.example.easyenglishvocabulary.Translaters.MicrosoftTranslator;
import com.example.easyenglishvocabulary.Translaters.Translator;

public class MyApplication extends android.app.Application {

    private static SQLiteDatabase database;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static SQLiteDatabase getDatabase() throws Exception {
        if (database == null){
            DataBaseHelper helper = new DataBaseHelper(appContext);
            database = helper.getWritableDatabase();
        }
        return database;
    }

    public static ActiveRecord[] getModelsClass(){
        return new ActiveRecord[]{new GlossaryModel()};
    }

    public static Translator getTranslator(){
        return new MicrosoftTranslator();
    }

}
