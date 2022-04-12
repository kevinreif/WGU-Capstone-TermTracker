package com.c196.termtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.c196.termtracker.R;
import com.c196.termtracker.tables.Term;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermAdapter extends ListAdapter<Term, TermAdapter.TermViewHolder> {
    private OnItemClickListener listener;

    public TermAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Term> DIFF_CALLBACK = new DiffUtil.ItemCallback<Term>() {
        @Override
        public boolean areItemsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            return oldItem.getTerm_id() == newItem.getTerm_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            return oldItem.getTerm_name().equals(newItem.getTerm_name()) &&
                    oldItem.getTerm_start().equals(newItem.getTerm_start()) &&
                    oldItem.getTerm_end().equals(newItem.getTerm_end());
        }
    };

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View TermView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_term, parent, false);
        return new TermViewHolder(TermView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term currentNote = getItem(position);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        holder.termName.setText(currentNote.getTerm_name());
        holder.termStart.setText(formatter.format(currentNote.getTerm_start()).toUpperCase());
        holder.termEnd.setText(formatter.format(currentNote.getTerm_end()).toUpperCase());
    }


    public Term getTermAt(int position) {
        return getItem(position);
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private TextView termName;
        private TextView termStart;
        private TextView termEnd;

        private TermViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.term_name);
            termStart = itemView.findViewById(R.id.term_start);
            termEnd = itemView.findViewById(R.id.term_end);

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
        void onItemClick(Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
