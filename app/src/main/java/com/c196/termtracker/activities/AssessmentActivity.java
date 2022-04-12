package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.utilities.CourseStatus;
import com.c196.termtracker.utilities.MyReceiver;
import com.c196.termtracker.viewmodel.AssessmentViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_ID = "com.c196.termtracker.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_NAME = "com.c196.termtracker.EXTRA_TERM_NAME";
    public static final String EXTRA_TERM_START = "com.c196.termtracker.EXTRA_TERM_START";
    public static final String EXTRA_TERM_END = "com.c196.termtracker.EXTRA_TERM_END";
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME = "com.c196.termtracker.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_START = "com.c196.termtracker.EXTRA_COURSE_START";
    public static final String EXTRA_COURSE_END = "com.c196.termtracker.EXTRA_COURSE_END";
    public static final String EXTRA_ASSESSMENT_ID = "com.c196.termtracker.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_NAME = "com.c196.termtracker.EXTRA_ASSESSMENT_NAME";
    public static final String EXTRA_ASSESSMENT_DATE = "com.c196.termtracker.EXTRA_ASSESSMENT_DATE";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.c196.termtracker.EXTRA_ASSESSMENT_TYPE";

    public static final int EDIT_ASSESSMENT_REQUEST = 2;
    private AssessmentViewModel assessmentViewModel;
    public static int numAlert = 100;

    private TextView termName;
    private TextView termStart;
    private TextView termEnd;
    private TextView courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView assessmentName;
    private TextView assessmentDate;
    private TextView assessmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        setTitle("Assessment Details");

        termName = findViewById(R.id.term_name);
        termStart = findViewById(R.id.term_start);
        termEnd = findViewById(R.id.term_end);

        courseName = findViewById(R.id.course_name);
        courseStart = findViewById(R.id.course_start);
        courseEnd = findViewById(R.id.course_end);

        assessmentName = findViewById(R.id.assessment_name);
        assessmentDate = findViewById(R.id.assessment_date);
        assessmentType = findViewById(R.id.assessment_type);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        //termName.setText(getIntent().getStringExtra(EXTRA_TERM_NAME));
        //termStart.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_START, -1)).toUpperCase());
        //termEnd.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_END, -1)).toUpperCase());

        //courseName.setText(getIntent().getStringExtra(EXTRA_COURSE_NAME));
        //courseStart.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_START, -1)).toUpperCase());
        //courseEnd.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_END, -1)).toUpperCase());

        assessmentName.setText(getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME));
        assessmentDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_ASSESSMENT_DATE, -1)).toUpperCase());
        assessmentType.setText(getIntent().getStringExtra(EXTRA_ASSESSMENT_TYPE));

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_NAME);
            int courseID = data.getIntExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, -1);
            Date date = new Date(data.getLongExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE, -1));
            String type = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE);


            //Refresh
            assessmentName.setText(name);
            assessmentDate.setText(formatter.format(date).toUpperCase());
            assessmentType.setText(type);

            //Add
            Assessment assessment = new Assessment(name, courseID, date, type);
            assessment.setAssessment_id(id);
            assessmentViewModel.update(assessment);
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }

    }



    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_assessment:
                Intent intent = new Intent(AssessmentActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_NAME, getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE, getIntent().getLongExtra(EXTRA_ASSESSMENT_DATE, -1));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE, getIntent().getStringExtra(EXTRA_ASSESSMENT_TYPE));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_EDIT, true);
                startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void assessmentNotification(View view) {
        DateFormat formatter = new SimpleDateFormat("MMM d y");

        Intent intent = new Intent (AssessmentActivity.this, MyReceiver.class);
        intent.putExtra("key", "Assessment " + getIntent().getStringExtra(EXTRA_ASSESSMENT_NAME)
                + " for course " + getIntent().getStringExtra(EXTRA_COURSE_NAME) + " is today!");
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this, numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Long date = getIntent().getLongExtra(EXTRA_ASSESSMENT_DATE, -1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
    }
}
