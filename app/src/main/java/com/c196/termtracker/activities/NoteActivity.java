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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.viewmodel.MentorViewModel;
import com.c196.termtracker.viewmodel.NoteViewModel;

public class NoteActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";
    public static final String EXTRA_NOTE_ID = "com.c196.termtracker.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.c196.termtracker.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_BODY = "com.c196.termtracker.EXTRA_NOTE_BODY";

    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;

    private TextView noteTitle;
    private TextView noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle("Note Details");

        noteTitle = findViewById(R.id.note_title);
        noteBody = findViewById(R.id.note_body);

        noteTitle.setText(getIntent().getStringExtra(EXTRA_NOTE_TITLE));
        noteBody.setText(getIntent().getStringExtra(EXTRA_NOTE_BODY));

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_NOTE_ID, -1);
            System.out.println(id);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE);
            String body = data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_BODY);
            int courseID = data.getIntExtra(AddEditNoteActivity.EXTRA_COURSE_ID, -1);


            noteTitle.setText(data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE));
            noteBody.setText(data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_BODY));

            Note note = new Note(title, body, courseID);
            note.setNote_id(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not updated", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_note:
                Intent intent = new Intent(NoteActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_ID, getIntent().getIntExtra(EXTRA_NOTE_ID, -1));
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE, getIntent().getStringExtra(EXTRA_NOTE_TITLE));
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_BODY, getIntent().getStringExtra(EXTRA_NOTE_BODY));
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_EDIT, true);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void shareNote(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra(EXTRA_NOTE_BODY));
        sendIntent.putExtra(Intent.EXTRA_TITLE, getIntent().getStringExtra(EXTRA_NOTE_TITLE));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }
}