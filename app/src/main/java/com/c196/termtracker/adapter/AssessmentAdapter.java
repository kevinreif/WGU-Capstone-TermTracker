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
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Term;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssessmentAdapter extends ListAdapter<Assessment, AssessmentAdapter.AssessmentViewHolder> {
    private OnItemClickListener listener;

    public AssessmentAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Assessment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Assessment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            return oldItem.getAssessment_id() == newItem.getAssessment_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            return oldItem.getAssessment_name().equals(newItem.getAssessment_name()) &&
                    oldItem.getAssessment_date().equals(newItem.getAssessment_date());
        }
    };

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View AssessmentView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_assessment, parent, false);
        return new AssessmentViewHolder(AssessmentView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessment currentAssessment = getItem(position);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        holder.assessmentName.setText(currentAssessment.getAssessment_name());
        holder.assessmentDate.setText(formatter.format(currentAssessment.getAssessment_date()).toUpperCase());
    }


    public Assessment getAssessmentAt(int position) {
        return getItem(position);
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private TextView assessmentName;
        private TextView assessmentDate;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentDate = itemView.findViewById(R.id.assessment_date);

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
        void onItemClick(Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}