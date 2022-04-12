package com.c196.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.c196.termtracker.tables.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getNoteList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table WHERE course_id = :id")
    LiveData<List<Note>> getNotesByCourse(int id);
}
