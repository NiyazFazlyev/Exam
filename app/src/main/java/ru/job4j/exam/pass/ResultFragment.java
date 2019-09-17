package ru.job4j.exam.pass;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ru.job4j.exam.ExamListActivity;
import ru.job4j.exam.R;
import ru.job4j.exam.store.ExamBaseHelper;
import ru.job4j.exam.store.ExamDbSchema;

public class ResultFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment, container, false);
        String exam_id = getArguments().getString("examId");
        ArrayList<Integer> correctAnswers = getArguments().getIntegerArrayList("correct answers");
        ArrayList<Integer> answers = getArguments().getIntegerArrayList("answers");
        int correct = 0;
        for (int index = 0; index != answers.size(); index++) {
            if (answers.get(index) == correctAnswers.get(index)) {
                correct++;
            }
        }
        TextView result = view.findViewById(R.id.result);

        result.setText(String.valueOf(100 * correct / answers.size()) + "%");
        SQLiteDatabase store = new ExamBaseHelper(getActivity()).getWritableDatabase();
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(ExamDbSchema.ExamTable.Cols.RESULT, 100 * correct / answers.size());
        String where = "id" + "=" + exam_id;
        store.update(ExamDbSchema.ExamTable.NAME, updatedValues, where, null);
        Button ok =  view.findViewById(R.id.Ok);
        ok.setOnClickListener(btn -> {
            startActivity(new Intent(getActivity(), ExamListActivity.class));

        });
        return view;
    }
}
