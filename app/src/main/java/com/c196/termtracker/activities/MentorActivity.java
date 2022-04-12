package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.viewmodel.MentorViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MentorActivity extends AppCompatActivity {
    public static final String EXTRA_MENTOR_ID = "com.c196.termtracker.EXTRA_MENTOR_ID";
    public static final String EXTRA_MENTOR_NAME = "com.c196.termtracker.EXTRA_MENTOR_NAME";
    public static final String EXTRA_MENTOR_PHONE = "com.c196.termtracker.EXTRA_MENTOR_PHONE";
    public static final String EXTRA_MENTOR_EMAIL = "com.c196.termtracker.EXTRA_MENTOR_EMAIL";

    public static final int EDIT_MENTOR_REQUEST = 2;
    private MentorViewModel mentorViewModel;

    private TextView mentorName;
    private TextView mentorPhone;
    private TextView mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        setTitle("Mentor Details");

        mentorName = findViewById(R.id.mentor_name);
        mentorPhone = findViewById(R.id.mentor_phone);
        mentorEmail = findViewById(R.id.mentor_email);

        mentorName.setText(getIntent().getStringExtra(EXTRA_MENTOR_NAME));
        mentorPhone.setText(getIntent().getStringExtra(EXTRA_MENTOR_PHONE));
        mentorEmail.setText(getIntent().getStringExtra(EXTRA_MENTOR_EMAIL));

        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_MENTOR_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditMentorActivity.EXTRA_MENTOR_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Mentor can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_NAME);
            String phone = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_PHONE);
            String email = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_EMAIL);

            mentorName.setText(data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_NAME));
            mentorPhone.setText(data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_PHONE));
            mentorEmail.setText(data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_EMAIL));

            Mentor mentor = new Mentor(name, phone, email);
            mentor.setMentor_id(id);
            mentorViewModel.update(mentor);
            Toast.makeText(this, "Mentor updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Mentor not saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_mentor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_mentor:
                Intent intent = new Intent(MentorActivity.this, AddEditMentorActivity.class);
                intent.putExtra(AddEditMentorActivity.EXTRA_MENTOR_ID, getIntent().getIntExtra(EXTRA_MENTOR_ID, -1));
                intent.putExtra(AddEditMentorActivity.EXTRA_MENTOR_NAME, getIntent().getStringExtra(EXTRA_MENTOR_NAME));
                intent.putExtra(AddEditMentorActivity.EXTRA_MENTOR_PHONE, getIntent().getStringExtra(EXTRA_MENTOR_PHONE));
                intent.putExtra(AddEditMentorActivity.EXTRA_MENTOR_EMAIL, getIntent().getStringExtra(EXTRA_MENTOR_EMAIL));
                intent.putExtra(AddEditMentorActivity.EXTRA_MENTOR_EDIT, true);
                startActivityForResult(intent, EDIT_MENTOR_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}