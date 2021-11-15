package com.example.easyenglishvocabulary.DataBase;

import java.util.HashMap;
import java.util.Map;

public class GlossaryModel extends ActiveRecord{

    private static final String TABLE_NAME = "GLOSSARY";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COLUMN_ENGLISH = "english";
    private static final String COLUMN_ENGLISH_TYPE  = "TEXT";
    private static final String COLUMN_RUSSIAN = "russian";
    private static final String COLUMN_RUSSIAN_TYPE  = "TEXT";
    private static final String COLUMN_CORRECT_ANSWERS = "correctAnswers";
    private static final String COLUMN_CORRECT_ANSWERS_TYPE  = "INTEGER";

    public int id;
    public String english;
    public String russian;
    public int correctAnswers;

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumns() {
        return new String[]{COLUMN_ID, COLUMN_ENGLISH, COLUMN_RUSSIAN, COLUMN_CORRECT_ANSWERS};
    }

    @Override
    protected HashMap<String, String> getColumnsTypes() {
        HashMap<String, String> types = new HashMap<String, String>();
        types.put(COLUMN_ID, COLUMN_ID_TYPE);
        types.put(COLUMN_ENGLISH, COLUMN_ENGLISH_TYPE);
        types.put(COLUMN_RUSSIAN, COLUMN_RUSSIAN_TYPE);
        types.put(COLUMN_CORRECT_ANSWERS, COLUMN_CORRECT_ANSWERS_TYPE);
        return types;
    }

    @Override
    public boolean save() {
        HashMap<String, Object> values = new HashMap<String, Object>();
        if (!isNewRecord) {
            values.put(COLUMN_ID, id);
        }
        values.put(COLUMN_ENGLISH, english);
        values.put(COLUMN_RUSSIAN, russian);
        values.put(COLUMN_CORRECT_ANSWERS, correctAnswers);
        return saveToDB(values, id) != -1;
    }

    @Override
    public boolean delete(){
        return delete(id);
    }


}
