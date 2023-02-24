package com.example.noteskeeper.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.noteskeeper.Models.Notes;

import java.util.List;

@Dao
public interface NotesDAO {

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    List<Notes> getallNotes();

    @Insert
    void insert(Notes notes);

    @Query("UPDATE Notes SET title = :title, note = :note, date = :date WHERE id = :id")
    void update(int id, String title, String note, String date);

    @Delete
    void delete(Notes notes);
}
