package com.c196.termtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.termtracker.database.AppRepository;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(Term term) {
        repository.insertTerm(term);
    }

    public void update(Term term) {
        repository.updateTerm(term);
    }

    public void delete(Term term) {
        repository.deleteTerm(term);
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

}
