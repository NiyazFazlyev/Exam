package ru.job4j.exam.pass;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.job4j.exam.Exam;
import ru.job4j.exam.ExamListFragment;
import ru.job4j.exam.R;

public class DescriptionFragment extends Fragment {
    private onStartButtonClickListener callback;

    public interface onStartButtonClickListener {
        void onStartButtonClicked(int examId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description, container, false);
        TextView desc = view.findViewById(R.id.exam_desc);
        Bundle arguments = getArguments();
        desc.setText(arguments.getString("desc"));
        Button start = view.findViewById(R.id.start);
        start.setOnClickListener(
                btn -> {
                    callback.onStartButtonClicked(arguments.getInt("examId"));
                }
        );

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (onStartButtonClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }
}