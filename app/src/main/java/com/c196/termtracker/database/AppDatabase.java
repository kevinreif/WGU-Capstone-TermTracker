package com.c196.termtracker.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
import com.c196.termtracker.utilities.CourseStatus;

import java.io.NotActiveException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class, Note.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract MentorDAO mentorDAO();
    public abstract NoteDAO noteDAO();

    private static final String DB_NAME = "app_database.db";
    private static AppDatabase instance;


    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDAO termDAO;
        private CourseDAO courseDAO;
        private AssessmentDAO assessmentDAO;
        private MentorDAO mentorDAO;
        private NoteDAO noteDAO;

        private PopulateDbAsyncTask(AppDatabase db) {
            termDAO = db.termDAO();
            courseDAO = db.courseDAO();
            assessmentDAO = db.assessmentDAO();
            mentorDAO = db.mentorDAO();
            noteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Calendar start;
            Calendar end;
            start = Calendar.getInstance();
            end = Calendar.getInstance();


            termDAO.insert((new Term("Spring", start.getTime(), end.getTime())));
            termDAO.insert((new Term("Summer", start.getTime(), end.getTime())));
            termDAO.insert((new Term("Winter", start.getTime(), end.getTime())));
            termDAO.insert((new Term("Fall", start.getTime(), end.getTime())));

            mentorDAO.insert((new Mentor("Kevin Reif", "360-921-7918", "kevin.reif@gmail.com")));
            mentorDAO.insert((new Mentor("KevDawg", "360-921-7918", "kevin.reif@gmail.com")));

            courseDAO.insert((new Course("C196", 1, start.getTime(), end.getTime(),  CourseStatus.COMPLETED, 1)));
            courseDAO.insert((new Course("C196", 1, start.getTime(), end.getTime(),  CourseStatus.COMPLETED, 1)));
            courseDAO.insert((new Course("C196", 2, start.getTime(), end.getTime(),  CourseStatus.COMPLETED, 1)));

            assessmentDAO.insert((new Assessment("Performance", 1, start.getTime(), "Performance")));
            assessmentDAO.insert((new Assessment("Objective", 1, start.getTime(), "Objective")));




            return null;
        }


    }
}

