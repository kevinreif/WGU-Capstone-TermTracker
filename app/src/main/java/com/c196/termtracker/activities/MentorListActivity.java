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
import com.c196.termtracker.adapter.MentorAdapter;
import com.c196.termtracker.adapter.TermAdapter;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.viewmodel.MentorViewModel;
import com.c196.termtracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MentorListActivity extends AppCompatActivity {
    public static final String EXTRA_MENTOR_ID = "com.c196.termtracker.EXTRA_MENTOR_ID";
    public static final String EXTRA_MENTOR_NAME = "com.c196.termtracker.EXTRA_MENTOR_NAME";
    public static final String EXTRA_MENTOR_PHONE = "com.c196.termtracker.EXTRA_MENTOR_PHONE";
    public static final String EXTRA_MENTOR_EMAIL = "com.c196.termtracker.EXTRA_MENTOR_EMAIL";
    public static final String EXTRA_MENTOR_SELECT = "com.c196.termtracker.EXTRA_MENTOR_SELECT";

    private MentorViewModel mentorViewModel;
    public static final int ADD_MENTOR_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);
        setTitle("Mentor List");

        FloatingActionButton buttonAddTerm = findViewById(R.id.add_mentor);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MentorListActivity.this, AddEditMentorActivity.class);
                startActivityForResult(intent, ADD_MENTOR_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.mentor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MentorAdapter adapter = new MentorAdapter();
        recyclerView.setAdapter(adapter);

        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        mentorViewModel.getAllMentors().observe(this, new Observer<List<Mentor>>() {
            @Override
            public void onChanged(List<Mentor> mentors) {
                adapter.submitList(mentors);
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
                mentorViewModel.delete(adapter.getMentorAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MentorListActivity.this, "Mentor deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MentorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Mentor mentor) {
                boolean select = getIntent().getBooleanExtra(EXTRA_MENTOR_SELECT, false);
                int mentorID = mentor.getMentor_id();


                if (select) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_MENTOR_SELECT, mentorID);

                    setResult(RESULT_OK, data);
                    finish();
                    return;

                }
                Intent intent = new Intent(MentorListActivity.this, MentorActivity.class);
                intent.putExtra(MentorActivity.EXTRA_MENTOR_ID, mentor.getMentor_id());
                intent.putExtra(MentorActivity.EXTRA_MENTOR_NAME, mentor.getMentor_name());
                intent.putExtra(MentorActivity.EXTRA_MENTOR_PHONE, mentor.getMentor_phone());
                intent.putExtra(MentorActivity.EXTRA_MENTOR_EMAIL, mentor.getMentor_email());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_MENTOR_REQUEST && resultCode == RESULT_OK) {


            String name = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_NAME);
            String phone = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_PHONE);
            String email = data.getStringExtra(AddEditMentorActivity.EXTRA_MENTOR_EMAIL);

            Mentor mentor = new Mentor(name, phone, email);
            mentorViewModel.insert(mentor);

            Toast.makeText(this, "Mentor saved", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Mentor not saved", Toast.LENGTH_SHORT).show();
        }

    }
}