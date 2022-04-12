package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.adapter.AssessmentAdapter;
import com.c196.termtracker.adapter.CourseAdapter;
import com.c196.termtracker.adapter.NoteAdapter;
import com.c196.termtracker.adapter.TermAdapter;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.utilities.CourseStatus;
import com.c196.termtracker.utilities.MyReceiver;
import com.c196.termtracker.viewmodel.AssessmentViewModel;
import com.c196.termtracker.viewmodel.CourseViewModel;
import com.c196.termtracker.viewmodel.MentorViewModel;
import com.c196.termtracker.viewmodel.NoteViewModel;
import com.c196.termtracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_ID = "com.c196.termtracker.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_NAME = "com.c196.termtracker.EXTRA_TERM_NAME";
    public static final String EXTRA_TERM_START = "com.c196.termtracker.EXTRA_TERM_START";
    public static final String EXTRA_TERM_END = "com.c196.termtracker.EXTRA_TERM_END";
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME = "com.c196.termtracker.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_START = "com.c196.termtracker.EXTRA_COURSE_START";
    public static final String EXTRA_COURSE_STATUS = "com.c196.termtracker.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_MENTOR_ID = "com.c196.termtracker.EXTRA_COURSE_MENTOR_ID";
    public static final String EXTRA_COURSE_END = "com.c196.termtracker.EXTRA_COURSE_END";


    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    public static int numAlert;

    private AssessmentViewModel assessmentViewModel;
    private CourseViewModel courseViewModel;
    private MentorViewModel mentorViewModel;

    private TextView termName;
    private TextView termStart;
    private TextView termEnd;
    private TextView courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseMentorName;
    private TextView courseMentorPhone;
    private TextView courseMentorEmail;
    private TextView courseStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setTitle("Course Details");

        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);

        //termName = findViewById(R.id.term_name);
        //termStart = findViewById(R.id.term_start);
        //termEnd = findViewById(R.id.term_end);

        courseName = findViewById(R.id.course_name);
        courseStart = findViewById(R.id.course_start);
        courseEnd = findViewById(R.id.course_end);
        courseMentorName = findViewById(R.id.mentor_name);
        courseMentorPhone = findViewById(R.id.mentor_phone_display);
        courseMentorEmail = findViewById(R.id.mentor_email_display);

        courseStatus = findViewById(R.id.status);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        //termName.setText(getIntent().getStringExtra(EXTRA_TERM_NAME));
        //termStart.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_START, -1)).toUpperCase());
        //termEnd.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_END, -1)).toUpperCase());

        courseName.setText(getIntent().getStringExtra(EXTRA_COURSE_NAME));
        courseStart.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_START, -1)).toUpperCase());
        courseEnd.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_END, -1)).toUpperCase());
        courseMentorName.setText(mentorViewModel.getMentorByID(getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1)).getMentor_name());
        courseMentorPhone.setText(mentorViewModel.getMentorByID(getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1)).getMentor_phone());
        courseMentorEmail.setText(mentorViewModel.getMentorByID(getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1)).getMentor_email());

        courseStatus.setText(getIntent().getSerializableExtra(EXTRA_COURSE_STATUS).toString());

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        //Assessment RecyclerView
        RecyclerView assessmentRecyclerView = findViewById(R.id.assessment_recycler_view);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentRecyclerView.setHasFixedSize(true);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter();
        assessmentRecyclerView.setAdapter(assessmentAdapter);


        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessmentsByCourse(getIntent().getIntExtra(EXTRA_COURSE_ID, -1)).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                assessmentAdapter.submitList(assessments);
            }
        });

        //Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(assessmentAdapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CourseActivity.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(assessmentRecyclerView);

        //To Assessment
        assessmentAdapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                Intent intent = new Intent(CourseActivity.this, AssessmentActivity.class);
                intent.putExtra(AssessmentActivity.EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
                intent.putExtra(AssessmentActivity.EXTRA_TERM_NAME, getIntent().getStringExtra(EXTRA_TERM_NAME));
                intent.putExtra(AssessmentActivity.EXTRA_TERM_START, getIntent().getLongExtra(EXTRA_TERM_START, -1));
                intent.putExtra(AssessmentActivity.EXTRA_TERM_END, getIntent().getLongExtra(EXTRA_TERM_END, -1));
                intent.putExtra(AssessmentActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                intent.putExtra(AssessmentActivity.EXTRA_COURSE_NAME, getIntent().getStringExtra(EXTRA_COURSE_NAME));
                intent.putExtra(AssessmentActivity.EXTRA_COURSE_START, getIntent().getLongExtra(EXTRA_COURSE_START, -1));
                intent.putExtra(AssessmentActivity.EXTRA_COURSE_END, getIntent().getLongExtra(EXTRA_COURSE_END, -1));
                intent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_ID, assessment.getAssessment_id());
                intent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_NAME, assessment.getAssessment_name());
                intent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_DATE, assessment.getAssessment_date().getTime());
                intent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_TYPE, assessment.getAssessment_type());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_NAME);
            int termID = data.getIntExtra(AddEditCourseActivity.EXTRA_TERM_ID, -1);
            Date start = new Date(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_START, -1));
            Date end = new Date(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_END, -1));
            CourseStatus status = (CourseStatus) data.getSerializableExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS);
            int mentorID = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, -1);

            //Refresh
            courseName.setText(data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_NAME));
            courseStart.setText(formatter.format(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_START, -1)).toUpperCase());
            courseEnd.setText(formatter.format(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_END, -1)).toUpperCase());
            courseMentorName.setText(mentorViewModel.getMentorByID(data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, -1)).getMentor_name());
            courseMentorPhone.setText(mentorViewModel.getMentorByID(data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, -1)).getMentor_phone());
            courseMentorEmail.setText(mentorViewModel.getMentorByID(data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, -1)).getMentor_email());

            courseStatus.setText(data.getSerializableExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS).toString());

            Course course = new Course(name, termID, start, end, status, mentorID);
            course.setCourse_id(id);
            courseViewModel.update(course);
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {


            String name = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_NAME);
            int courseID = data.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);
            Date date = new Date(data.getLongExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE, -1));
            String type = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE);


            Assessment assessment = new Assessment(name, courseID, date, type);
            assessmentViewModel.insert(assessment);

            Toast.makeText(this, "Assessment Added", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode != RESULT_OK) {
            Toast.makeText(this, "Assessment not added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_course:
                Intent intent = new Intent(CourseActivity.this, AddEditCourseActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_NAME, getIntent().getStringExtra(EXTRA_COURSE_NAME));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_START, getIntent().getLongExtra(EXTRA_COURSE_START, -1));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_END, getIntent().getLongExtra(EXTRA_COURSE_END, -1));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, getIntent().getSerializableExtra(EXTRA_COURSE_STATUS));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_EDIT, true);
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void addNewAssessment(View view) {
        Intent intent = new Intent(CourseActivity.this, AddEditAssessmentActivity.class);
        intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
        startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
    }

    public void goToNotes(View view) {
        Intent intent = new Intent(CourseActivity.this, NoteListActivity.class);
        intent.putExtra(NoteListActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
        startActivity(intent);
    }

    public void setStartNotification(View view) {
        DateFormat formatter = new SimpleDateFormat("MMM d y");


        Intent intent = new Intent (CourseActivity.this, MyReceiver.class);
        intent.putExtra("key", getIntent().getStringExtra(EXTRA_COURSE_NAME) + " starts today, "
                + formatter.format(getIntent().getLongExtra(AddEditCourseActivity.EXTRA_COURSE_START, -1)).toUpperCase() + "!");
        PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this, numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Long date = getIntent().getLongExtra(EXTRA_COURSE_START, -1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
    }

    public void setEndNotification(View view) {
        DateFormat formatter = new SimpleDateFormat("MMM d y");

        Intent intent = new Intent (CourseActivity.this, MyReceiver.class);
        intent.putExtra("key", getIntent().getStringExtra(EXTRA_COURSE_NAME) + " ends today, "
                + formatter.format(getIntent().getLongExtra(AddEditCourseActivity.EXTRA_COURSE_END, -1)).toUpperCase() + "!");
        PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this, numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Long date = getIntent().getLongExtra(EXTRA_COURSE_END, -1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
    }
}
