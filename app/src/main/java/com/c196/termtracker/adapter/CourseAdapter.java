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
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Term;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseAdapter extends ListAdapter<Course, CourseAdapter.CourseViewHolder> {
    private OnItemClickListener listener;

    public CourseAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<Course>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCourse_id() == newItem.getCourse_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCourse_name().equals(newItem.getCourse_name()) &&
                    oldItem.getCourse_start().equals(newItem.getCourse_start()) &&
                    oldItem.getCourse_end().equals(newItem.getCourse_end());
        }
    };

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View CourseView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_course, parent, false);
        return new CourseViewHolder(CourseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = getItem(position);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        holder.courseName.setText(currentCourse.getCourse_name());
        holder.courseStart.setText(formatter.format(currentCourse.getCourse_start()).toUpperCase());
        holder.courseEnd.setText(formatter.format(currentCourse.getCourse_end()).toUpperCase());
    }


    public Course getCourseAt(int position) {
        return getItem(position);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseName;
        private TextView courseStart;
        private TextView courseEnd;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            courseStart = itemView.findViewById(R.id.course_start);
            courseEnd = itemView.findViewById(R.id.course_end);

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
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
