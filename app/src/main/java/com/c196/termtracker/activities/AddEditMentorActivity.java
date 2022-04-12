package com.c196.termtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.c196.termtracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditMentorActivity extends AppCompatActivity {
    public static final String EXTRA_MENTOR_ID = "com.c196.termtracker.EXTRA_MENTOR_ID";
    public static final String EXTRA_MENTOR_NAME = "com.c196.termtracker.EXTRA_MENTOR_NAME";
    public static final String EXTRA_MENTOR_PHONE = "com.c196.termtracker.EXTRA_MENTOR_PHONE";
    public static final String EXTRA_MENTOR_EMAIL = "com.c196.termtracker.EXTRA_MENTOR_EMAIL";
    public static final String EXTRA_MENTOR_EDIT = "com.c196.termtracker.EXTRA_MENTOR_EDIT";

    private EditText mentorName;
    private EditText mentorPhone;
    private EditText mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_mentor);

        mentorName = findViewById(R.id.edit_mentor_name);
        mentorPhone = findViewById(R.id.edit_mentor_phone);
        mentorEmail = findViewById(R.id.edit_mentor_email);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        Intent intent = getIntent();

        if (intent.getBooleanExtra(EXTRA_MENTOR_EDIT, false)) {

            setTitle("Edit Mentor");
            System.out.println(intent.getStringExtra(EXTRA_MENTOR_NAME));

            mentorName.setText(intent.getStringExtra(EXTRA_MENTOR_NAME));
            mentorPhone.setText(intent.getStringExtra(EXTRA_MENTOR_PHONE));
            mentorEmail.setText(intent.getStringExtra(EXTRA_MENTOR_EMAIL));

        } else {
            setTitle("Add New Mentor");
        }

    }

    private void saveMentor() {

        String name = mentorName.getText().toString();
        String phone = mentorPhone.getText().toString();
        String email = mentorEmail.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a  mentor name", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_MENTOR_NAME, name);
        data.putExtra(EXTRA_MENTOR_PHONE, phone);
        data.putExtra(EXTRA_MENTOR_EMAIL, email);

        boolean edit = getIntent().getBooleanExtra(EXTRA_MENTOR_EDIT, false);
        if (edit) {

            data.putExtra(EXTRA_MENTOR_ID, getIntent().getIntExtra(EXTRA_MENTOR_ID, -1));
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_mentor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_mentor:
                saveMentor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
