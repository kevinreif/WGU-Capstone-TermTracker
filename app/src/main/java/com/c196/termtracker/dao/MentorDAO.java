package com.c196.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;

import java.util.List;

@Dao
public interface MentorDAO {
    @Query("SELECT * FROM mentor_table")
    LiveData<List<Mentor>> getMentorList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mentor mentor);

    @Update
    void update(Mentor mentor);

    @Delete
    void delete(Mentor mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAll();

    @Query("SELECT * FROM mentor_table WHERE mentor_id = :id")
    Mentor getMentorByID(int id);
}
