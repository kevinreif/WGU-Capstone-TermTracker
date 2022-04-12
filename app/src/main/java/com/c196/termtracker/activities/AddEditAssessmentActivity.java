package com.c196.termtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.fragment.DatePicker;
import com.c196.termtracker.utilities.CourseStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ID = "com.c196.termtracker.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_NAME = "com.c196.termtracker.EXTRA_ASSESSMENT_NAME";
    public static final String EXTRA_ASSESSMENT_DATE = "com.c196.termtracker.EXTRA_ASSESSMENT_DATE";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.c196.termtracker.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_EDIT = "com.c196.termtracker.EXTRA_ASSESSMENT_EDIT";


    private EditText assessmentName;
    private TextView displayDate;
    private Button pickDate;
    private Date startDate = new Date();
    private RadioButton performanceRadio;
    private RadioButton objectiveRadio;
    private String assessmentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        assessmentName = findViewById(R.id.edit_assessment_name);

        displayDate = findViewById(R.id.assessment_date);
        pickDate = findViewById(R.id.assessment_pick_date);

        performanceRadio = findViewById(R.id.performance);
        objectiveRadio = findViewById(R.id.objective);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePickerDialogFragment;
                datePickerDialogFragment = new DatePicker();
                datePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        Intent intent = getIntent();

        if (intent.getBooleanExtra(EXTRA_ASSESSMENT_EDIT, false)) {
            DateFormat formatter = new SimpleDateFormat("MMM d y");

            setTitle("Edit Assessment");
            assessmentName.setText(intent.getStringExtra(EXTRA_ASSESSMENT_NAME));
            displayDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_ASSESSMENT_DATE, -1)).toUpperCase());
            startDate.setTime(intent.getLongExtra(EXTRA_ASSESSMENT_DATE, -1));

            System.out.println(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));

            if (getIntent().getStringExtra(EXTRA_ASSESSMENT_TYPE).equals("Performance")) {
                performanceRadio.setChecked(true);
            } else {
                objectiveRadio.setChecked(true);
            }


        } else {
            setTitle("Add New Assessment");
        }

        System.out.println(getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
    }

    private void saveAssessment() {

        String name = assessmentName.getText().toString();
        Date date = startDate;
        int courseID = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);

        if (performanceRadio.isChecked()) {
            assessmentType = "Performance";
        } else if (objectiveRadio.isChecked()) {
            assessmentType = "Objective";
        } else {
            Toast.makeText(this, "Please select an assessment type", Toast.LENGTH_SHORT);
            return;
        }

        String type = assessmentType;

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name", Toast.LENGTH_SHORT);
            return;
        }

        System.out.println(courseID);

        Intent data = new Intent();
        data.putExtra(EXTRA_ASSESSMENT_NAME, name);
        data.putExtra(EXTRA_ASSESSMENT_DATE, date.getTime());
        data.putExtra(EXTRA_COURSE_ID, courseID);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);

        System.out.println(getIntent().getBooleanExtra(EXTRA_ASSESSMENT_EDIT, false));
        System.out.println(getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1));

        boolean edit = getIntent().getBooleanExtra(EXTRA_ASSESSMENT_EDIT, false);
        if (edit) {

            data.putExtra(EXTRA_ASSESSMENT_ID, getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1));
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                saveAssessment();
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

        displayDate.setText(selectedDate);
        startDate = calender.getTime();

    }

}
