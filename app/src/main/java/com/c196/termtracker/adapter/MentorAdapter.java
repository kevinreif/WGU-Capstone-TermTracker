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
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MentorAdapter extends ListAdapter<Mentor, MentorAdapter.MentorViewHolder> {
    private OnItemClickListener listener;

    public MentorAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Mentor> DIFF_CALLBACK = new DiffUtil.ItemCallback<Mentor>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mentor oldItem, @NonNull Mentor newItem) {
            return oldItem.getMentor_id() == newItem.getMentor_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mentor oldItem, @NonNull Mentor newItem) {
            return oldItem.getMentor_name().equals(newItem.getMentor_name()) &&
                    oldItem.getMentor_phone().equals(newItem.getMentor_phone()) &&
                    oldItem.getMentor_email().equals(newItem.getMentor_email());
        }
    };

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MentorView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_mentor, parent, false);
        return new MentorViewHolder(MentorView);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor currentMentor = getItem(position);

        holder.mentorName.setText(currentMentor.getMentor_name());
        holder.mentorPhone.setText(currentMentor.getMentor_phone());
        holder.mentorEmail.setText(currentMentor.getMentor_email());
    }


    public Mentor getMentorAt(int position) {
        return getItem(position);
    }

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private TextView mentorName;
        private TextView mentorPhone;
        private TextView mentorEmail;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.mentor_name);
            mentorPhone = itemView.findViewById(R.id.mentor_phone);
            mentorEmail = itemView.findViewById(R.id.mentor_email);

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
        void onItemClick(Mentor mentor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
