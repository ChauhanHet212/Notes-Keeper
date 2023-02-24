package com.example.noteskeeper.Listener;

import androidx.cardview.widget.CardView;

import com.example.noteskeeper.Models.Notes;

public interface RecyclerClickListener {
    void onClick(Notes notes);

    void onLongClick(Notes notes, CardView cardView);
}
