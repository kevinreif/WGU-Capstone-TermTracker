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

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_NOTE_ID = "com.c196.termtracker.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.c196.termtracker.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_BODY = "com.c196.termtracker.EXTRA_NOTE_BODY";
    public static final String EXTRA_NOTE_EDIT = "com.c196.termtracker.EXTRA_NOTE_EDIT";

    private EditText noteTitle;
    private EditText noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        noteBody = findViewById(R.id.edit_note_body);
        noteTitle = findViewById(R.id.edit_note_title);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_24);

        Intent intent = getIntent();

        if (intent.getBooleanExtra(EXTRA_NOTE_EDIT, false)) {

            setTitle("Edit Note");

            noteTitle.setText(intent.getStringExtra(EXTRA_NOTE_TITLE));
            noteBody.setText(intent.getStringExtra(EXTRA_NOTE_BODY));

        } else {
            setTitle("Add New Note");
        }

    }

    private void saveNote() {

        String title = noteTitle.getText().toString();
        String body = noteBody.getText().toString();
        int courseID = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a note title", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOTE_TITLE, title);
        data.putExtra(EXTRA_NOTE_BODY, body);
        data.putExtra(EXTRA_COURSE_ID, courseID);

        boolean edit = getIntent().getBooleanExtra(EXTRA_NOTE_EDIT, false);
        if (edit) {

            data.putExtra(EXTRA_NOTE_ID, getIntent().getIntExtra(EXTRA_NOTE_ID, -1));
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
