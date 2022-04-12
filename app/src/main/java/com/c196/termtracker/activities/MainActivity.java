package com.c196.termtracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c196.termtracker.R;
import com.c196.termtracker.database.AppDatabase;
import com.c196.termtracker.database.TestData;
import com.c196.termtracker.tables.Mentor;
import com.c196.termtracker.tables.Term;
import com.c196.termtracker.viewmodel.CourseViewModel;
import com.c196.termtracker.viewmodel.MentorViewModel;
import com.c196.termtracker.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout constraintLayout =  findViewById(R.id.main_layout);

        Button toTermsButton = new Button(getApplicationContext());
        toTermsButton.setText("    View Terms    ");
        toTermsButton.setId(R.id.toTermsButton);
        toTermsButton.setBackgroundColor(getResources().getColor(R.color.blue));

        set.constrainHeight(toTermsButton.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(toTermsButton.getId(), ConstraintSet.WRAP_CONTENT);

        set.connect(toTermsButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(toTermsButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(toTermsButton.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);


        constraintLayout.addView(toTermsButton);
        setContentView(constraintLayout);
        set.applyTo(constraintLayout);

        toTermsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TermListActivity.class);
                startActivity(intent);
            }
        });

    }



    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_mentors:
                Intent intent = new Intent(MainActivity.this, MentorListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

