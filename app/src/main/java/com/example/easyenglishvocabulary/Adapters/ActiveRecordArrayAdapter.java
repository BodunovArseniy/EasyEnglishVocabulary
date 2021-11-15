package com.example.easyenglishvocabulary.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easyenglishvocabulary.DataBase.ActiveRecord;

import java.lang.reflect.Field;

public class ActiveRecordArrayAdapter extends ArrayAdapter<ActiveRecord> {

    int layoutResourse;

    public ActiveRecordArrayAdapter(@NonNull Context context, int resource, @NonNull ActiveRecord[] objects) {
        super(context, resource, objects);
        layoutResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ActiveRecord item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(layoutResourse, null);
        }

        for (String field: item.getColumns()) {
            int id = convertView.getResources().getIdentifier(field, "id", "com.example.easyenglishvocabulary");
            View view = convertView.findViewById(id);
            if (view instanceof TextView){
                ((TextView) view).setText(getFieldValue(item, field));
            }
        }

        return convertView;
    }

    private String getFieldValue(ActiveRecord activeRecord, String fieldName){
        Class<?> c = activeRecord.getClass();
        String value;
        try {

            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);

            value = f.get(activeRecord).toString();
        } catch (Exception e){
            value = "Ошибка доступа";
        }
        return value;
    }
}
