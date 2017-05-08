package com.example.anley.hw6;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HW6 extends AppCompatActivity {
    Button add;
    ListView lv;
    SQLiteDatabase db;
    ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw6);


        add = (Button) findViewById(R.id.addNote);
        lv = (ListView) findViewById(R.id.LV);
        add.setOnClickListener(addANote);
        lv.setOnItemClickListener(click);

    }
    protected void onResume(){
        super.onResume();
        DBOpenHelper openhelper = new DBOpenHelper(this);
        db = openhelper.getWritableDatabase();
        titles = NoteDB.getTitleList(db);

        ArrayAdapter<String> adapter =new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, titles);
        lv.setAdapter(adapter);
    }

   @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    OnClickListener addANote = new OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(HW6.this, NoteEditor.class);
            intent.putExtra("POSITION", -1);
            startActivity(intent);
        }
    };

    OnItemClickListener click = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v,
                                int position, long id) {
            Intent intent = new Intent();
            intent.setClass(HW6.this,
                    NoteEditor.class);
            intent.putExtra("POSITION", position);
            startActivity(intent);
        }
    };

}
