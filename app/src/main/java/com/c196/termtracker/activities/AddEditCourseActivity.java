package com.c196.termtracker.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.adapter.AssessmentAdapter;
import com.c196.termtracker.adapter.MentorAdapter;
import com.c196.termtracker.fragment.DatePicker;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.utilities.CourseStatus;
import com.c196.termtracker.viewmodel.MentorViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEditCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_TERM_ID = "com.c196.termtracker.EXTRA_TERM_ID";
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME = "com.c196.termtracker.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_START = "com.c196.termtracker.EXTRA_COURSE_START";
    public static final String EXTRA_COURSE_END = "com.c196.termtracker.EXTRA_COURSE_END";
    public static final String EXTRA_COURSE_STATUS = "com.c196.termtracker.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_MENTOR_ID = "com.c196.termtracker.EXTRA_COURSE_MENTOR_ID";
    public static final String EXTRA_COURSE_EDIT = "com.c196.termtracker.EXTRA_TERM_EDIT";


    private static final int SELECT_MENTOR_REQUEST = 1;

    private EditText courseName;

    private ArrayAdapter<CourseStatus> courseStatusArrayAdapter;
    private Spinner courseStatusSpinner;
    private MentorViewModel mentorViewModel;

    private TextView displayStartDate;
    private Button pickDateStart;
    private Date startDate = new Date();
    private TextView displayEndDate;
    private Button pickEndDate;
    private Date endDate = new Date();
    private TextView mentorName;
    private int dateSelector;
    private int courseMentorID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        courseName = findViewById(R.id.edit_course_name);

        courseStatusSpinner = findViewById(R.id.course_status_spinner);
        courseStatusArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CourseStatus.values());
        courseStatusSpinner.setAdapter(courseStatusArrayAdapter);

        displayStartDate = findViewById(R.id.display_course_start_date);
        pickDateStart = findViewById(R.id.course_start_date_button);
        displayEndDate = findViewById(R.id.display_course_end_date);
        pickEndDate = findViewById(R.id.course_end_date_button);

        mentorName = findViewById(R.id.display_mentor_name);

        courseMentorID = getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1);



        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);


        pickDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelector = 1;
                DatePicker datePickerDialogFragment;
                datePickerDialogFragment = new DatePicker();
                datePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        pickEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelector = 2;
                DatePicker datePickerDialogFragment;
                datePickerDialogFragment = new DatePicker();
                datePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        Intent intent = getIntent();

        if (intent.getBooleanExtra(EXTRA_COURSE_EDIT, false)) {
            DateFormat formatter = new SimpleDateFormat("MMM d y");

            setTitle("Edit Course");
            courseName.setText(intent.getStringExtra(EXTRA_COURSE_NAME));
            displayStartDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_START, -1)).toUpperCase());
            startDate.setTime(intent.getLongExtra(EXTRA_COURSE_START, -1));
            displayEndDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_COURSE_END, -1)).toUpperCase());
            endDate.setTime(getIntent().getLongExtra(EXTRA_COURSE_END, -1));
            mentorName.setText(mentorViewModel.getMentorByID(getIntent().getIntExtra(EXTRA_COURSE_MENTOR_ID, -1)).getMentor_name());

            System.out.println(getIntent().getSerializableExtra(EXTRA_COURSE_STATUS));
            CourseStatus status = ((CourseStatus) getIntent().getSerializableExtra(EXTRA_COURSE_STATUS));
            courseStatusSpinner.setSelection(status.ordinal());


        } else {
            setTitle("Add New Course");
        }

    }

    private void saveCourse() {

        String title = courseName.getText().toString();
        Date start = startDate;
        Date end = endDate;
        int termID = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        int mentorID = courseMentorID;

        int statusInt = courseStatusSpinner.getSelectedItemPosition();
        CourseStatus status = CourseStatus.values()[statusInt];




        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_LONG);
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NAME, title);
        data.putExtra(EXTRA_COURSE_START, start.getTime());
        data.putExtra(EXTRA_COURSE_END, end.getTime());
        data.putExtra(EXTRA_TERM_ID, termID);
        data.putExtra(EXTRA_COURSE_MENTOR_ID, mentorID);
        data.putExtra(EXTRA_COURSE_STATUS, status);


        boolean edit = getIntent().getBooleanExtra(EXTRA_COURSE_EDIT, false);
        if (edit) {

            data.putExtra(EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        DateFormat formatter = new SimpleDateFormat("MMM d y");

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month);
        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());
        if (dateSelector == 1) {
            displayStartDate.setText(selectedDate);
            startDate = calender.getTime();
        } else if (dateSelector == 2) {
            displayEndDate.setText(selectedDate);
            endDate = calender.getTime();
        }

    }

    public void selectMentor(View view) {
        Intent intent = new Intent(AddEditCourseActivity.this, MentorListActivity.class);
        intent.putExtra(MentorListActivity.EXTRA_MENTOR_SELECT, true);
        startActivityForResult(intent, SELECT_MENTOR_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_MENTOR_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MentorListActivity.EXTRA_MENTOR_SELECT, -1);

            if (id == -1) {
                Toast.makeText(this, "Mentor can't be chosen", Toast.LENGTH_SHORT).show();
                return;
            }

            courseMentorID = id;
            String tempMentorName = mentorViewModel.getMentorByID(id).getMentor_name();
            mentorName.setText(tempMentorName);

        }
    }
}
