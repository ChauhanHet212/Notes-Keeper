package com.example.noteskeeper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteskeeper.Listener.RecyclerClickListener;
import com.example.noteskeeper.Models.Notes;
import com.example.noteskeeper.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    Context context;
    List<Notes> list;
    RecyclerClickListener listener;

    public NotesAdapter(Context context, List<Notes> list, RecyclerClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.list_item_title.setText(list.get(position).getTitle());
        holder.list_item_title.setSelected(true);

        holder.list_item_note.setText(list.get(position).getNote());

        holder.list_item_date.setText(list.get(position).getDate());
        holder.list_item_date.setSelected(true);

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    public void filterlist(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView notes_container;
        TextView list_item_title, list_item_note, list_item_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notes_container = itemView.findViewById(R.id.notes_container);
            list_item_title = itemView.findViewById(R.id.list_item_title);
            list_item_note = itemView.findViewById(R.id.list_item_note);
            list_item_date = itemView.findViewById(R.id.list_item_date);
        }
    }
}
