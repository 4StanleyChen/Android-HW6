package com.example.anley.hw6;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteEditor extends AppCompatActivity {
    int pos;
    EditText etTitle;
    EditText etBody;
    SQLiteDatabase db;
    ArrayList<String> titlelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Intent intent = getIntent();
        pos = intent.getIntExtra("POSITION", -1);
        etTitle = (EditText)findViewById(R.id.et_title);
        etBody = (EditText)findViewById(R.id.et_body);
    }

    protected void onResume(){
        super.onResume();

        DBOpenHelper openhelper = new DBOpenHelper(this);
        db = openhelper.getWritableDatabase();

        titlelist = NoteDB.getTitleList(db);

        if(pos != -1) {
            String title = titlelist.get(pos);
            etTitle.setText(title);
            etBody.setText(NoteDB.getBody(db, title));
        }
        else{
            etTitle.setText("");
            etBody.setText("");
        }
    }

    public void onPause(){
        super.onPause();
        String title = etTitle.getText().toString();

        if(title.length()==0){
            Toast.makeText(NoteEditor.this, "標題不得為空白，此添加無效", Toast.LENGTH_LONG).show();
        }
        else{
            NoteDB.addNote(db, etTitle.getText().toString(), etBody.getText().toString());
        }

    }
}
