package com.c196.termtracker.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.c196.termtracker.utilities.CourseStatus;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table",
        foreignKeys = {
                @ForeignKey(entity = Term.class,
                        parentColumns = "term_id",
                        childColumns = "term_id",
                        onDelete = CASCADE),

                @ForeignKey(entity = Mentor.class,
                        parentColumns = "mentor_id",
                        childColumns = "mentor_id",
                        onDelete = CASCADE)
        })

public class Course {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "course_id")
    private int course_id;
    @ColumnInfo(name = "course_name")
    private String course_name;
    @ColumnInfo(name = "term_id")
    private int term_id; //Foreign Key
    @ColumnInfo(name = "course_start")
    private Date course_start;
    @ColumnInfo(name = "course_end")
    private Date course_end;
        @ColumnInfo(name = "course_status")
    private CourseStatus course_status;
    @ColumnInfo(name = "mentor_id")
    private int mentor_id; //Foreign key



    public Course(String course_name, int term_id, Date course_start, Date course_end, CourseStatus course_status, int mentor_id) {
        this.course_name = course_name;
        this.term_id = term_id;
        this.course_start = course_start;
        this.course_end = course_end;
        this.course_status = course_status;
        this.mentor_id = mentor_id;
    }

    //Getters
    public int getCourse_id() {
        return course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public int getTerm_id() {
        return term_id;
    }

    public Date getCourse_start() {
        return course_start;
    }

    public Date getCourse_end() {
        return course_end;
    }

    public CourseStatus getCourse_status() {
        return course_status;
    }

    public int getMentor_id() {
        return mentor_id;
    }

    //Setters
    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public void setCourse_start(Date course_start) {
        this.course_start = course_start;
    }

    public void setCourse_end(Date course_end) {
        this.course_end = course_end;
    }

    public void setCourse_status(CourseStatus course_status) {
        this.course_status = course_status;
    }

    public void setMentor_id(int mentor_id) {
        this.mentor_id = mentor_id;
    }
}
