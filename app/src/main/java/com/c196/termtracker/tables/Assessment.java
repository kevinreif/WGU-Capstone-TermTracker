package com.c196.termtracker.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table",
        foreignKeys =
                @ForeignKey(entity = Course.class,
                        parentColumns = "course_id",
                        childColumns = "course_id",
                        onDelete = CASCADE)
)
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    @ColumnInfo(name = "assessment_name")
    private String assessment_name;
    @ColumnInfo(name = "course_id")
    private int course_id; //Foreign key
    @ColumnInfo(name = "assessment_date")
    private Date assessment_date;
    @ColumnInfo(name = "assessment_type")
    private String assessment_type;

    public Assessment(String assessment_name, int course_id, Date assessment_date, String assessment_type) {
        this.assessment_name = assessment_name;
        this.course_id = course_id;
        this.assessment_date = assessment_date;
        this.assessment_type = assessment_type;
    }

    //Getters
    public int getAssessment_id() {
        return assessment_id;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public int getCourse_id() {
        return course_id;
    }

    public Date getAssessment_date() {
        return assessment_date;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    //Setters
    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setAssessment_date(Date assessment_date) {
        this.assessment_date = assessment_date;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }
}
