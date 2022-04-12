package com.c196.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Query("SELECT * FROM assessment_table")
    LiveData<List<Assessment>> getAssessmentList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAll();

    @Query("SELECT * FROM assessment_table WHERE course_id =:id")
    LiveData<List<Assessment>> getAssessmentsByCourse(int id);
}
