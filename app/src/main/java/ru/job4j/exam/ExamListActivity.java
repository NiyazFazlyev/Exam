package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.job4j.exam.pass.PassActivity;

public class ExamListActivity extends AppCompatActivity
        implements ExamListFragment.onTitleClickListener {
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

    @Override
    public void onTitleClicked(Exam exam){
        Intent intent = new Intent(this, PassActivity.class);
        intent.putExtra("examId", exam.getId());
        intent.putExtra("desc", exam.getDesc());
        startActivity(intent);
    }
}