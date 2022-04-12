package com.c196.termtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.termtracker.database.AppRepository;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allAssessments = repository.getAllAssessments();
    }

    public void insert(Assessment assessment) {
        repository.insertAssessment(assessment);
    }

    public void update(Assessment assessment) {
        repository.updateAssessment(assessment);
    }

    public void delete(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }

    public void deleteAllAssessments() {
        repository.deleteAllAssessments();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getAssessmentsByCourse(int id) {
        return repository.getAssessmentsByCourse(id);
    }


}
