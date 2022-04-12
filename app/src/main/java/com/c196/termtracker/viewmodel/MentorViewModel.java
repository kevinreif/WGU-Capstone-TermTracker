package com.c196.termtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.termtracker.database.AppRepository;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Mentor>> allMentors;

    public MentorViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allMentors = repository.getAllMentors();
    }

    public void insert(Mentor mentor) {
        repository.insertMentor(mentor);
    }

    public void update(Mentor mentor) {
        repository.updateMentor(mentor);
    }

    public void delete(Mentor mentor) {
        repository.deleteMentor(mentor);
    }

    public void deleteAllMentors() {
        repository.deleteAllMentors();
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return allMentors;
    }

    public Mentor getMentorByID(int id) {
        return repository.getMentorByID(id);
    }

}
