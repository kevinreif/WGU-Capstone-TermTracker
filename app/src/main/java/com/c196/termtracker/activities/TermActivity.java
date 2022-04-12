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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.adapter.CourseAdapter;
import com.c196.termtracker.adapter.TermAdapter;
import com.c196.termtracker.tables.Course;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.utilities.CourseStatus;
import com.c196.termtracker.viewmodel.CourseViewModel;
import com.c196.termtracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TermActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_ID = "com.c196.termtracker.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_NAME = "com.c196.termtracker.EXTRA_TERM_NAME";
    public static final String EXTRA_TERM_START = "com.c196.termtracker.EXTRA_TERM_START";
    public static final String EXTRA_TERM_END = "com.c196.termtracker.EXTRA_TERM_END";

    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    private TextView termName;
    private TextView termStart;
    private TextView termEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        setTitle("Term Details");

        termName = findViewById(R.id.term_name);
        termStart = findViewById(R.id.term_start);
        termEnd = findViewById(R.id.term_end);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        termName.setText(getIntent().getStringExtra(EXTRA_TERM_NAME));
        termStart.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_START, -1)).toUpperCase());
        termEnd.setText(formatter.format(getIntent().getLongExtra(EXTRA_TERM_END, -1)).toUpperCase());


        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCoursesByTerm(getIntent().getIntExtra(EXTRA_TERM_ID, -1)).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.submitList(courses);
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
                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TermActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //To Course
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(TermActivity.this, CourseActivity.class);
                intent.putExtra(CourseActivity.EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
                intent.putExtra(CourseActivity.EXTRA_TERM_NAME, getIntent().getStringExtra(EXTRA_TERM_NAME));
                intent.putExtra(CourseActivity.EXTRA_TERM_START, getIntent().getLongExtra(EXTRA_TERM_START, -1));
                intent.putExtra(CourseActivity.EXTRA_TERM_END, getIntent().getLongExtra(EXTRA_TERM_END, -1));
                intent.putExtra(CourseActivity.EXTRA_COURSE_ID, course.getCourse_id());
                intent.putExtra(CourseActivity.EXTRA_COURSE_NAME, course.getCourse_name());
                intent.putExtra(CourseActivity.EXTRA_COURSE_START, course.getCourse_start().getTime());
                intent.putExtra(CourseActivity.EXTRA_COURSE_END, course.getCourse_end().getTime());
                intent.putExtra(CourseActivity.EXTRA_COURSE_STATUS, course.getCourse_status());
                intent.putExtra(CourseActivity.EXTRA_COURSE_MENTOR_ID, course.getMentor_id());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DateFormat formatter = new SimpleDateFormat("MMM d y");

        if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_TERM_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Term can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_NAME);
            Date start = new Date(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_START, 1));
            Date end = new Date(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_END, 1));

            termName.setText(data.getStringExtra(AddEditTermActivity.EXTRA_TERM_NAME));
            termStart.setText(formatter.format(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_START, -1)).toUpperCase());
            termEnd.setText(formatter.format(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_END, -1)).toUpperCase());

            Term term = new Term(title, start, end);
            term.setTerm_id(id);
            termViewModel.update(term);
            Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {




            String name = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_NAME);
            int termID = data.getIntExtra(AddEditCourseActivity.EXTRA_TERM_ID, -1);
            Date start = new Date(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_START, -1));
            Date end = new Date(data.getLongExtra(AddEditCourseActivity.EXTRA_COURSE_END, -1));
            CourseStatus status = (CourseStatus) data.getSerializableExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS);
            int mentorID = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_ID, -1);
            
            Course course = new Course(name, termID, start, end, status, mentorID);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course Added", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_COURSE_REQUEST && resultCode != RESULT_OK) {
            Toast.makeText(this, "Course not added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_term:
                Intent intent = new Intent(TermActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_NAME, getIntent().getStringExtra(EXTRA_TERM_NAME));
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_START, getIntent().getLongExtra(EXTRA_TERM_START, -1));
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_END, getIntent().getLongExtra(EXTRA_TERM_END, -1));
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_EDIT, true);
                startActivityForResult(intent, EDIT_TERM_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void addNewCourse(View view) {
        Intent intent = new Intent(TermActivity.this, AddEditCourseActivity.class);
        intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, getIntent().getIntExtra(EXTRA_TERM_ID, -1));
        startActivityForResult(intent, ADD_COURSE_REQUEST);
    }
}