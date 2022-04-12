package com.c196.termtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.termtracker.database.AppRepository;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insertNote(note);
    }

    public void update(Note note) {
        repository.updateNote(note);
    }

    public void delete(Note note) {
        repository.deleteNote(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getNotesByCourse(int id) {
        return repository.getNotesByCourse(id);
    }

}
