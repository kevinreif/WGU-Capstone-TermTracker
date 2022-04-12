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
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.adapter.TermAdapter;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.viewmodel.CourseViewModel;
import com.c196.termtracker.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TermListActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        setTitle("Terms List");

        FloatingActionButton buttonAddTerm = findViewById(R.id.add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermListActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.submitList(terms);
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
                int courses = courseViewModel.getNumberOfCoursesForTerm(adapter.getTermAt(viewHolder.getAdapterPosition()).getTerm_id());

                if (courses > 0) {
                    Toast.makeText(TermListActivity.this, "Unable to delete term due to assigned courses", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return;
                }
                termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TermListActivity.this, "Term deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //To Term
        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(TermListActivity.this, TermActivity.class);
                intent.putExtra(TermActivity.EXTRA_TERM_ID, term.getTerm_id());
                intent.putExtra(TermActivity.EXTRA_TERM_NAME, term.getTerm_name());
                intent.putExtra(TermActivity.EXTRA_TERM_START, term.getTerm_start().getTime());
                intent.putExtra(TermActivity.EXTRA_TERM_END, term.getTerm_end().getTime());
                startActivity(intent);
            }
        });
    }

    //Add
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DateFormat formatter = new SimpleDateFormat();

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {


            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_NAME);
            Date start = new Date(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_START, 1));
            Date end = new Date(data.getLongExtra(AddEditTermActivity.EXTRA_TERM_END, 1));

            Term term = new Term(title, start, end);
            termViewModel.insert(term);

            Toast.makeText(this, "Term saved", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_terms:
                termViewModel.deleteAllTerms();
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}