package com.example.noteskeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.noteskeeper.DataBase.DBHelper;
import com.example.noteskeeper.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    ImageView backbtn;
    EditText edttitle, edtnote;
    FloatingActionButton savebtn;
    DBHelper helper;
    Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().hide();

        backbtn = findViewById(R.id.backbtn);
        edttitle = findViewById(R.id.edttitle);
        edtnote = findViewById(R.id.edtnote);
        savebtn = findViewById(R.id.savebtn);

        int i = getIntent().getIntExtra("key", 0);

        notes = new Notes();

        if (i == 2){
            notes = (Notes) getIntent().getSerializableExtra("updatenote");
            edttitle.setText(notes.getTitle());
            edtnote.setText(notes.getNote());
        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edttitle.getText().toString();
                String note = edtnote.getText().toString();
                if (title.isEmpty()){
                    Toast.makeText(AddActivity.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                } else if (note.isEmpty()){
                    Toast.makeText(AddActivity.this, "Please Add Description", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                    Date date = new Date();

                    notes.setTitle(title);
                    notes.setNote(note);
                    notes.setDate(format.format(date));

                    Intent intent = new Intent();
                    intent.putExtra("addnote",notes);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}