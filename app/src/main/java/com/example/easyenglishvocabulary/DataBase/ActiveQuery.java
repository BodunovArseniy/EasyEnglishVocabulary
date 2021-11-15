package com.example.easyenglishvocabulary.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easyenglishvocabulary.MyApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ActiveQuery<T extends ActiveRecord> {

    private Class<T> tClass;

    public ActiveQuery(Class<T> tClass){
        this.tClass = tClass;
    }

    public ArrayList<T> getFromDB(String where){
        ArrayList<T> records = new ArrayList<T>();
        try {
            SQLiteDatabase db = MyApplication.getDatabase();

            String tableName = (tClass.newInstance()).getTableName();
            String[] columns = (tClass.newInstance()).getColumns();

            Cursor cursor = db.query(
                    tableName, // таблица
                    columns,   // столбцы
                    where,   // столбцы для условия WHERE
                    null,// значения для условия WHERE
                    null,    // Don't group the rows
                    null,      // Don't filter by row groups
                    null);     // порядок сортировки

            int[] columnIndexes = new int[columns.length];
            for (int i = 0; i < columns.length; i++) {
                columnIndexes[i] = cursor.getColumnIndex(columns[i]);
            }

            while (cursor.moveToNext()){
                T record = tClass.newInstance();
                record.isNewRecord = false;
                for (int i = 0; i < columns.length; i++) {
                    int type = cursor.getType(columnIndexes[i]);
                    Object value = null;
                    switch (type){
                        case Cursor.FIELD_TYPE_INTEGER: value = cursor.getInt(columnIndexes[i]);
                                                        break;
                        case Cursor.FIELD_TYPE_FLOAT: value = cursor.getFloat(columnIndexes[i]);
                            break;
                        case Cursor.FIELD_TYPE_STRING: value = cursor.getString(columnIndexes[i]);
                            break;
                    }
                    setFieldValue(record, columns[i], value);
                }
                records.add(record);
            }

            cursor.close();

            return records;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean setFieldValue(ActiveRecord activeRecord, String fieldName, Object value){
        Class<?> c = activeRecord.getClass();
        try {
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);

            f.set(activeRecord, value);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
