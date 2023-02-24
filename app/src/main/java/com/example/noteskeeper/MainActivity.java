package com.example.noteskeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteskeeper.Adapter.NotesAdapter;
import com.example.noteskeeper.DataBase.DBHelper;
import com.example.noteskeeper.Listener.RecyclerClickListener;
import com.example.noteskeeper.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addbtn;
    SearchView searchView;
    DBHelper helper;
    List<Notes> notesList = new ArrayList<>();
    NotesAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addbtn = findViewById(R.id.addbtn);
        searchView = findViewById(R.id.searchView);

        helper = DBHelper.getInstance(this);
        notesList = helper.notesDAO().getallNotes();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new NotesAdapter(this, notesList, new RecyclerClickListener() {
            @Override
            public void onClick(Notes notes) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("key", 2);
                intent.putExtra("updatenote", notes);
                startActivityForResult(intent, 303);
            }

            @Override
            public void onLongClick(Notes notes, CardView cardView) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, cardView);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete){
                            helper.notesDAO().delete(notes);
                            notesList.clear();
                            notesList.addAll(helper.notesDAO().getallNotes());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
        recyclerView.setAdapter(adapter);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("key", 1);
                startActivityForResult(intent, 404);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Notes> filteredList = new ArrayList<>();
                for (Notes singleNote : notesList){
                    if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNote().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(singleNote);
                    }
                    adapter.filterlist(filteredList);
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 404){
                Notes notes = (Notes) data.getSerializableExtra("addnote");
                helper.notesDAO().insert(notes);
                notesList.clear();
                notesList.addAll(helper.notesDAO().getallNotes());
                adapter.notifyDataSetChanged();
            }
            else if (requestCode == 303){
                Notes notes = (Notes) data.getSerializableExtra("addnote");
                helper.notesDAO().update(notes.getId(), notes.getTitle(), notes.getNote(), notes.getDate());
                notesList.clear();
                notesList.addAll(helper.notesDAO().getallNotes());
                adapter.notifyDataSetChanged();
            }
        }
    }
}