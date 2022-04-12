package com.c196.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.termtracker.tables.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course_table")
    LiveData<List<Course>> getCourseList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course_table")
    void deleteAll();

    @Query("SELECT * FROM course_table WHERE term_id = :id")
    LiveData<List<Course>> getAllCoursesByTerm(int id);

    @Query("SELECT COUNT(*) FROM course_table WHERE term_id = :id")
    int getNumberOfCoursesForTerm(int id);
}
