package ru.job4j.exam.pass;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import ru.job4j.exam.R;
import ru.job4j.exam.store.ExamDbSchema;
import ru.job4j.exam.store.Store;

public class PassFragment extends Fragment {
    private Store store;
    Cursor questions;
    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Integer> correctAnswers = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.pass_fragment, container, false);
        Bundle args = getArguments();
        this.store = Store.getStore(getContext());
        getQuestions(String.valueOf(args.getInt("examId")));
        setQuestionText();
        questions.moveToNext();
        Button next = view.findViewById(R.id.next);
        next.setOnClickListener(btn -> {
            RadioGroup variants = view.findViewById(R.id.variants);
            answers.add(variants.getCheckedRadioButtonId());
            if (!questions.isAfterLast()) {
                setQuestionText();
                questions.moveToNext();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("examId", String.valueOf(args.getInt("examId")));
                bundle.putIntegerArrayList("answers", answers);
                bundle.putIntegerArrayList("correct answers", correctAnswers);
                Fragment fragment = new ResultFragment();
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.pass_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
//        Button previous = view.findViewById(R.id.previous);
//        previous.setOnClickListener(btn -> {
//            questions.moveToPrevious();
//            setQuestionText();
//        });
        return view;
    }

    private void getQuestions(String examId) {
        String condition = ExamDbSchema.QuestionTable.Cols.EXAM_ID + " = " + examId;
        questions = this.store.find(ExamDbSchema.QuestionTable.NAME, condition);
        questions.moveToFirst();
    }

    private void setQuestionText() {
        TextView questionText = view.findViewById(R.id.question);
        RadioGroup variants = view.findViewById(R.id.variants);
        variants.clearCheck();

        if (!questions.isAfterLast()) {
            questionText.setText(questions.getString(questions.getColumnIndex(ExamDbSchema.QuestionTable.Cols.TEXT)));
            correctAnswers.add(questions.getInt(questions.getColumnIndex(ExamDbSchema.QuestionTable.Cols.ANSWER)));

            String condition = ExamDbSchema.OptionTable.Cols.QUESTION_ID + " = " + questions.getString(questions.getColumnIndex("id"));
            Cursor options = this.store.find(ExamDbSchema.OptionTable.NAME, condition);
            options.moveToFirst();

            for (int index = 0; index != variants.getChildCount(); index++) {
                RadioButton button = (RadioButton) variants.getChildAt(index);
                button.setText(options.getString(options.getColumnIndex(ExamDbSchema.OptionTable.Cols.TEXT)));
                button.setId(index + 1);
                options.moveToNext();
            }
        }
    }
}
