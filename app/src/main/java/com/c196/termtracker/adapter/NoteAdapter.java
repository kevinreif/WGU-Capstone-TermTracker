package com.c196.termtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.termtracker.R;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.tables.Term;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNote_id() == newItem.getNote_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNote_body().equals(newItem.getNote_body()) &&
                    oldItem.getCourse_id() == newItem.getCourse_id() &&
                    oldItem.getNote_title().equals(newItem.getNote_title());
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View NoteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_note, parent, false);
        return new NoteViewHolder(NoteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = getItem(position);

        holder.noteTitle.setText(currentNote.getNote_title());
        holder.noteBody.setText(currentNote.getNote_body());

    }


    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitle;
        private TextView noteBody;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteBody = itemView.findViewById(R.id.note_body);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

