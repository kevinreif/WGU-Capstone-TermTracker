package com.c196.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.termtracker.tables.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Query("SELECT * FROM term_table")
    LiveData<List<Term>> getTermList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM term_table")
    void deleteAll();
}
