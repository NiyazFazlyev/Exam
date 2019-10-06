package ru.job4j.exam.pass;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.job4j.exam.R;

public class PassActivity extends AppCompatActivity implements DescriptionFragment.onStartButtonClickListener {
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_activity);
        Fragment fragment = fm.findFragmentById(R.id.pass_container);
        if (fragment == null) {
            fragment = new DescriptionFragment();
            Bundle arguments = getIntent().getExtras();
            fragment.setArguments(arguments);
            fm.beginTransaction()
                    .add(R.id.pass_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onStartButtonClicked(int examId) {
        Bundle bundle = new Bundle();
        bundle.putInt("examId", examId);
        Fragment fragment = new PassFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.pass_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
