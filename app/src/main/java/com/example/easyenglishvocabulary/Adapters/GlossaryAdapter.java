package com.example.easyenglishvocabulary.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.easyenglishvocabulary.DataBase.ActiveRecord;
import com.example.easyenglishvocabulary.DataBase.GlossaryModel;
import com.example.easyenglishvocabulary.R;

public class GlossaryAdapter extends ActiveRecordArrayAdapter{

    static int layout = R.layout.gloassary_list_item;

    public GlossaryAdapter(@NonNull Context context, @NonNull GlossaryModel[] objects) {
        super(context, layout, objects);
    }
}
