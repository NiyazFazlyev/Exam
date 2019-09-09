package ru.job4j.exam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ExamListActivity extends AppCompatActivity {
    private final FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.exam_list);
        Fragment fragment = manager.findFragmentById(R.id.exams);
        if (fragment == null) {
            fragment = new ExamListFragment();
            manager.beginTransaction()
                    .add(R.id.exams, fragment)
                    .commit();
        }
    }
}