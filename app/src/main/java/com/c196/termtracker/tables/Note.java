package com.c196.termtracker.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "note_table",
        foreignKeys =
                @ForeignKey(entity = Course.class,
                        parentColumns = "course_id",
                        childColumns = "course_id",
                        onDelete = CASCADE)
)

public class Note {
    @PrimaryKey(autoGenerate = true)
    private int note_id;
    @ColumnInfo(name = "note_title")
    private String note_title;
    @ColumnInfo(name = "note_body")
    private String note_body;
    @ColumnInfo(name = "course_id")
    private int course_id; //Foreign Key


    public Note(String note_title, String note_body, int course_id) {
        this.note_title = note_title;
        this.note_body = note_body;
        this.course_id = course_id;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public String getNote_body() {
        return note_body;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public void setNote_body(String note_body) {
        this.note_body = note_body;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}

