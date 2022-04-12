package com.c196.termtracker.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.c196.termtracker.dao.AssessmentDAO;
import com.c196.termtracker.dao.CourseDAO;
import com.c196.termtracker.dao.MentorDAO;
import com.c196.termtracker.dao.NoteDAO;
import com.c196.termtracker.dao.TermDAO;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.tables.Term;

import java.util.List;

public class AppRepository {

    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;
    private MentorDAO mentorDAO;
    private NoteDAO noteDAO;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Mentor>> allMentors;
    private LiveData<List<Note>> allNotes;


    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        mentorDAO = db.mentorDAO();
        noteDAO = db.noteDAO();
        allTerms = termDAO.getTermList();
        allCourses = courseDAO.getCourseList();
        allAssessments = assessmentDAO.getAssessmentList();
        allMentors = mentorDAO.getMentorList();
        allNotes = noteDAO.getNoteList();
    }

    //Terms
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void insertTerm(Term term) {
        new InsertTermAsyncTask(termDAO).execute(term);
    }

    public void updateTerm(Term term) {
        new UpdateTermAsyncTask(termDAO).execute(term);
    }

    public void deleteTerm(Term term) {
        new DeleteTermAsyncTask(termDAO).execute(term);
    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termDAO).execute();
    }


    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDAO termDAO;

        private InsertTermAsyncTask(TermDAO termDAO) {
            this.termDAO = termDAO;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.insert(terms[0]);
            return null;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDAO termDAO;

        private UpdateTermAsyncTask(TermDAO termDAO) {
            this.termDAO = termDAO;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.update(terms[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDAO termDAO;

        private DeleteTermAsyncTask(TermDAO termDAO) {
            this.termDAO = termDAO;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.delete(terms[0]);
            return null;
        }
    }

    private static class DeleteAllTermsAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDAO termDAO;

        private DeleteAllTermsAsyncTask(TermDAO termDAO) {
            this.termDAO = termDAO;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.deleteAll();
            return null;
        }
    }

    //Courses
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Course>> getCoursesByTerm(int id) {
        return courseDAO.getAllCoursesByTerm(id);
    }

    public int getNumberOfCoursesForTerm(int id) {
        return courseDAO.getNumberOfCoursesForTerm(id);
    }

    public void insertCourse(Course course) {
        new InsertCourseAsyncTask(courseDAO).execute(course);
    }

    public void updateCourse(Course course) {
        new UpdateCourseAsyncTask(courseDAO).execute(course);
    }

    public void deleteCourse(Course course) {
        new DeleteCourseAsyncTask(courseDAO).execute(course);
    }

    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(courseDAO).execute();
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO courseDAO;

        private InsertCourseAsyncTask(CourseDAO courseDAO) {
            this.courseDAO = courseDAO;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDAO.insert(courses[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO courseDAO;

        private UpdateCourseAsyncTask(CourseDAO courseDAO) {
            this.courseDAO = courseDAO;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDAO.update(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO courseDAO;

        private DeleteCourseAsyncTask(CourseDAO courseDAO) {
            this.courseDAO = courseDAO;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDAO.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO courseDAO;

        private DeleteAllCoursesAsyncTask(CourseDAO courseDAO) {
            this.courseDAO = courseDAO;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDAO.deleteAll();
            return null;
        }
    }

    //Assessments
    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getAssessmentsByCourse(int id) {
        return assessmentDAO.getAssessmentsByCourse(id);
    }

    public void insertAssessment(Assessment assessment) {
        new InsertAssessmentAsyncTask(assessmentDAO).execute(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        new UpdateAssessmentAsyncTask(assessmentDAO).execute(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        new DeleteAssessmentAsyncTask(assessmentDAO).execute(assessment);
    }

    public void deleteAllAssessments() {
        new DeleteAllAssessmentsAsyncTask(assessmentDAO).execute();
    }


    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDAO assessmentDAO;

        private InsertAssessmentAsyncTask(AssessmentDAO assessmentDAO) {
            this.assessmentDAO = assessmentDAO;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDAO.insert(assessments[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDAO assessmentDAO;

        private UpdateAssessmentAsyncTask(AssessmentDAO assessmentDAO) {
            this.assessmentDAO = assessmentDAO;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDAO.update(assessments[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDAO assessmentDAO;

        private DeleteAssessmentAsyncTask(AssessmentDAO assessmentDAO) {
            this.assessmentDAO = assessmentDAO;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDAO.delete(assessments[0]);
            return null;
        }
    }

    private static class DeleteAllAssessmentsAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDAO assessmentDAO;

        private DeleteAllAssessmentsAsyncTask(AssessmentDAO assessmentDAO) {
            this.assessmentDAO = assessmentDAO;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDAO.deleteAll();
            return null;
        }
    }

    //Mentors
    public LiveData<List<Mentor>> getAllMentors() {
        return allMentors;
    }

    public Mentor getMentorByID(int id) {
        return mentorDAO.getMentorByID(id);
    }

    public void insertMentor(Mentor mentor) {
        new InsertMentorAsyncTask(mentorDAO).execute(mentor);
    }

    public void updateMentor(Mentor mentor) {
        new UpdateMentorAsyncTask(mentorDAO).execute(mentor);
    }

    public void deleteMentor(Mentor mentor) {
        new DeleteMentorAsyncTask(mentorDAO).execute(mentor);
    }

    public void deleteAllMentors() {
        new DeleteAllMentorsAsyncTask(mentorDAO).execute();
    }


    private static class InsertMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDAO mentorDAO;

        private InsertMentorAsyncTask(MentorDAO mentorDAO) {
            this.mentorDAO = mentorDAO;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDAO.insert(mentors[0]);
            return null;
        }
    }

    private static class UpdateMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDAO mentorDAO;

        private UpdateMentorAsyncTask(MentorDAO mentorDAO) {
            this.mentorDAO = mentorDAO;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDAO.update(mentors[0]);
            return null;
        }
    }

    private static class DeleteMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDAO mentorDAO;

        private DeleteMentorAsyncTask(MentorDAO mentorDAO) {
            this.mentorDAO = mentorDAO;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDAO.delete(mentors[0]);
            return null;
        }
    }

    private static class DeleteAllMentorsAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDAO mentorDAO;

        private DeleteAllMentorsAsyncTask(MentorDAO mentorDAO) {
            this.mentorDAO = mentorDAO;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDAO.deleteAll();
            return null;
        }
    }

    //Notes
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getNotesByCourse(int id) {
        return noteDAO.getNotesByCourse(id);
    }

    public void insertNote(Note note) {
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDAO).execute();
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private DeleteAllNotesAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.deleteAll();
            return null;
        }
    }
}
