package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.fragment.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_TERM_ID = "com.c196.termtracker.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_NAME = "com.c196.termtracker.EXTRA_TERM_NAME";
    public static final String EXTRA_TERM_START = "com.c196.termtracker.EXTRA_TERM_START";
    public static final String EXTRA_TERM_END = "com.c196.termtracker.EXTRA_TERM_END";
    public static final String EXTRA_TERM_EDIT = "com.c196.termtracker.EXTRA_TERM_EDIT";

    private EditText termTitle;
    private Calendar time;
    private TextView tvDate;
    private Button btPickDate;
    private Date startDate = new Date();
    private TextView displayEndDate;
    private Button pickEndDate;
    private Date endDate = new Date();
    private int dateSelector;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        tvDate = findViewById(R.id.tvDate);
        btPickDate = findViewById(R.id.btPickDate);
        displayEndDate = findViewById(R.id.display_end_date);
        pickEndDate = findViewById(R.id.end_date_button);


        termTitle = findViewById(R.id.edit_term_name);

        btPickDate.setOnClickListener(new View.OnClickListener() {
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

        if (intent.getBooleanExtra(EXTRA_TERM_EDIT, false)) {
            DateFormat formatter = new SimpleDateFormat("MMM d y");

            setTitle("Edit Term");
            termTitle.setText(intent.getStringExtra(EXTRA_TERM_NAME));
            tvDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_START, -1)).toUpperCase());
            startDate.setTime(intent.getLongExtra(EXTRA_TERM_START, -1));
            displayEndDate.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_END, -1)).toUpperCase());
            endDate.setTime(getIntent().getLongExtra(EXTRA_TERM_END, -1));

            System.out.println(getIntent().getLongExtra(EXTRA_TERM_START, -1));


        } else {
            setTitle("Add Term");
        }
    }


    private void saveTerm() {
        time = Calendar.getInstance();

        String title = termTitle.getText().toString();
        Date start = startDate;
        Date end = endDate;

        System.out.println(title);

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_LONG);
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TERM_NAME, title);
        data.putExtra(EXTRA_TERM_START, start.getTime());
        data.putExtra(EXTRA_TERM_END, end.getTime());


        boolean edit = getIntent().getBooleanExtra(EXTRA_TERM_EDIT, false);
        if (edit) {

            data.putExtra(EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
        }
        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
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
            tvDate.setText(selectedDate);
            startDate = calender.getTime();
        } else if (dateSelector == 2) {
            displayEndDate.setText(selectedDate);
            endDate = calender.getTime();
        }

    }
}