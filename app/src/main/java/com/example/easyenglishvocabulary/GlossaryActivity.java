package com.example.easyenglishvocabulary;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyenglishvocabulary.DataBase.ActiveQuery;
import com.example.easyenglishvocabulary.DataBase.GlossaryModel;
import com.example.easyenglishvocabulary.Translaters.HTTPRequest;
import com.example.easyenglishvocabulary.Translaters.MicrosoftTranslator;
import com.example.easyenglishvocabulary.Translaters.Translator;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.easyenglishvocabulary.databinding.ActivityGlossaryBinding;

public class GlossaryActivity extends AppCompatActivity {

    GlossaryModel model;
    Translator translator;
    EditText englishTextInput;
    EditText russianTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);

        englishTextInput = (EditText) findViewById(R.id.englishET);
        russianTextInput = (EditText) findViewById(R.id.russianET);

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isDeleted = model.delete();
                Toast.makeText(GlossaryActivity.this, isDeleted ? "Запись успешно удалена" : "Запись не успешно не удалена", Toast.LENGTH_SHORT).show();
                if (isDeleted){
                    Intent intent = new Intent(GlossaryActivity.this, GlossaryListActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(v -> {
            model.english = englishTextInput.getText().toString();
            model.russian = russianTextInput.getText().toString();
            boolean isSaved = model.save();
            Toast.makeText(this, isSaved ? "Запись успешно сохранена" : "Запись не сохранена", Toast.LENGTH_LONG).show();
            if (isSaved) {
                Intent intent = new Intent(GlossaryActivity.this, GlossaryListActivity.class);
                startActivity(intent);
            }
        });

        translator = MyApplication.getTranslator();
        translator.setPreRequestCallback(new HTTPRequest.Callback() {
            @Override
            public void handleResult(String result) {
                Log.i("HTTP START", "htttp start");
            }
        });
        translator.setPostRequestCallback(new HTTPRequest.Callback() {
            @Override
            public void handleResult(String result) {
                russianTextInput.setText(result);
            }
        });

        Button translate = (Button) findViewById(R.id.translateBTN);
        translate.setOnClickListener(v -> {
            String english = englishTextInput.getText().toString();
            translator.translate(english);
        });

        int modelId = getIntent().getIntExtra("id", -1);
        if (modelId != -1){
            model = new ActiveQuery<GlossaryModel>(GlossaryModel.class).getFromDB("id = " + modelId).get(0);
            englishTextInput.setText(model.english);
            russianTextInput.setText(model.russian);
        } else {
            model = new GlossaryModel();
        }

    }

}