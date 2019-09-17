package ru.job4j.exam.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.Option;
import ru.job4j.exam.Question;
import ru.job4j.exam.R;

public class QuestionAddFragment extends Fragment {

    private OnAddQuestionButtonClickListener callback;
    private View mainView;

    public interface OnAddQuestionButtonClickListener {
        void onAddQuestionButtonClicked(Question question);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.question_add, container, false);
        Button add = mainView.findViewById(R.id.add_question);
        add.setOnClickListener(this::addQuestion);
        return mainView;
    }

    private void addQuestion(View view){
        final TextView questionText = mainView.findViewById(R.id.question);
        final TextView option1 = mainView.findViewById(R.id.option1);
        final TextView option2 = mainView.findViewById(R.id.option2);
        final TextView option3 = mainView.findViewById(R.id.option3);
        final TextView option4 = mainView.findViewById(R.id.option4);
        final TextView answer = mainView.findViewById(R.id.answer);
        List<Option> options = new ArrayList<>();
        String option1Text = option1.getText().toString();
        options.add(new Option(1, option1Text));
        options.add(new Option(2, option2.getText().toString()));
        options.add(new Option(3, option3.getText().toString()));
        options.add(new Option(4, option4.getText().toString()));
        Question question = new Question(1, questionText.getText().toString(), options, Integer.valueOf(answer.getText().toString()));

        callback.onAddQuestionButtonClicked(question);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnAddQuestionButtonClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }
}
