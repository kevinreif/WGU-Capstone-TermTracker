package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.adapter.NoteAdapter;
import com.c196.termtracker.tables.Assessment;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Note;
import com.c196.termtracker.utilities.CourseStatus;
import com.c196.termtracker.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID = "com.c196.termtracker.EXTRA_COURSE_ID";

    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        setTitle("Course Notes List");

        FloatingActionButton buttonAddTerm = findViewById(R.id.add_mentor);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        //Note RecyclerView
        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getNotesByCourse(getIntent().getIntExtra(EXTRA_COURSE_ID, -1)).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
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
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteListActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        //To Note
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                intent.putExtra(NoteActivity.EXTRA_COURSE_ID, getIntent().getIntExtra(EXTRA_COURSE_ID, -1));
                intent.putExtra(NoteActivity.EXTRA_NOTE_ID, note.getNote_id());
                intent.putExtra(NoteActivity.EXTRA_NOTE_TITLE, note.getNote_title());
                intent.putExtra(NoteActivity.EXTRA_NOTE_BODY, note.getNote_body());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE);
            String body = data.getStringExtra(AddEditNoteActivity.EXTRA_NOTE_BODY);
            int courseID = data.getIntExtra(AddEditNoteActivity.EXTRA_COURSE_ID, -1);


            Note note = new Note(title, body, courseID);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not added", Toast.LENGTH_SHORT).show();
        }

    }
}
