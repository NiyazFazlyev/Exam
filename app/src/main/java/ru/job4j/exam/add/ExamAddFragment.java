package ru.job4j.exam.add;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.job4j.exam.R;


public class ExamAddFragment extends Fragment {

    private OnAddExamButtonClickListener callback;

    public interface OnAddExamButtonClickListener {
        void onAddExamButtonClicked(String title);
        void onNewQuestionButtonClicked();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_add, container, false);
        final EditText name = view.findViewById(R.id.name);
        Button newQuestion = view.findViewById(R.id.new_question);
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(
                btn -> {
                    callback.onAddExamButtonClicked(name.getText().toString());
                }
        );
        newQuestion.setOnClickListener(
                btn -> {
                    callback.onNewQuestionButtonClicked();
                }
        );
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnAddExamButtonClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }
}
