package com.example.noteskeeper.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteskeeper.Models.Notes;

@Database(entities = Notes.class, exportSchema = false, version = 1)
public abstract class DBHelper extends RoomDatabase {
    public static final String DB_NAME = "NotesDB";
    public static DBHelper database;

    public synchronized static DBHelper getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context, DBHelper.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }

    public abstract NotesDAO notesDAO();
}
