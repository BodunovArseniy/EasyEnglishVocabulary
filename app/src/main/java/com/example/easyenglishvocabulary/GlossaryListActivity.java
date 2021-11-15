package com.example.easyenglishvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.easyenglishvocabulary.Adapters.GlossaryAdapter;
import com.example.easyenglishvocabulary.DataBase.ActiveQuery;
import com.example.easyenglishvocabulary.DataBase.GlossaryModel;

import java.util.ArrayList;

public class GlossaryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary_list);

        ArrayList<GlossaryModel> list = new ActiveQuery<GlossaryModel>(GlossaryModel.class).getFromDB(null);
        GlossaryModel[] array = list.toArray(new GlossaryModel[0]);
        ListView listView = (ListView) findViewById(R.id.glossary_list_view);

        GlossaryAdapter adapter = new GlossaryAdapter(this, array);
        listView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.addItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlossaryListActivity.this, GlossaryActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent intent = new Intent(GlossaryListActivity.this, GlossaryActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
    }
}