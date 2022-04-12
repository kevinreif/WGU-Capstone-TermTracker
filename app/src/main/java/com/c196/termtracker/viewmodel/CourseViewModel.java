package com.c196.termtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.c196.termtracker.database.AppRepository;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allCourses = repository.getAllCourses();

    }

    public void insert(Course course) {
        repository.insertCourse(course);
    }

    public void update(Course course) {
        repository.updateCourse(course);
    }

    public void delete(Course course) {
        repository.deleteCourse(course);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Course>> getCoursesByTerm(int id) {
        return repository.getCoursesByTerm(id);
    }

    public int getNumberOfCoursesForTerm(int id) {
        return repository.getNumberOfCoursesForTerm(id);
    }

}
