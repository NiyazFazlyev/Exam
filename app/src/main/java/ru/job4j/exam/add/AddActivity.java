package ru.job4j.exam.add;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.job4j.exam.ExamListActivity;
import ru.job4j.exam.Option;
import ru.job4j.exam.Question;
import ru.job4j.exam.R;
import ru.job4j.exam.store.ExamBaseHelper;
import ru.job4j.exam.store.ExamDbSchema;

public class AddActivity extends AppCompatActivity implements QuestionAddFragment.OnAddQuestionButtonClickListener,
        ExamAddFragment.OnAddExamButtonClickListener {
    private final List<Question> newQuestions = new ArrayList();
    private SQLiteDatabase store;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.store = new ExamBaseHelper(this).getWritableDatabase();
        setContentView(R.layout.add_activity);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.add_container);
        if (fragment == null) {
            fragment = new ExamAddFragment();
            fm.beginTransaction()
                    .add(R.id.add_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onAddQuestionButtonClicked(Question question) {
        newQuestions.add(question);

    }

    @Override
    public void onAddExamButtonClicked(String title) {
        ContentValues value = new ContentValues();
        value.put(ExamDbSchema.ExamTable.Cols.TITLE, title);
        value.put(ExamDbSchema.ExamTable.Cols.RESULT, 0);
        Date date = new Date();
        value.put(ExamDbSchema.ExamTable.Cols.DATE, date.getDate());
        long examId = this.store.insert(ExamDbSchema.ExamTable.NAME, null, value);
        value.clear();
        int position = 0;
        for (Question question : newQuestions) {
            ContentValues newQuestion = new ContentValues();
            newQuestion.put(ExamDbSchema.QuestionTable.Cols.EXAM_ID, examId);
            newQuestion.put(ExamDbSchema.QuestionTable.Cols.TEXT, question.getText());
            newQuestion.put(ExamDbSchema.QuestionTable.Cols.POSITION, position++);
            newQuestion.put(ExamDbSchema.QuestionTable.Cols.ANSWER, question.getAnswer());
            long questionId = this.store.insert(ExamDbSchema.QuestionTable.NAME, null, newQuestion);
            newQuestion.clear();
            for (Option option : question.getOptions()) {
                ContentValues newOption = new ContentValues();
                newOption.put(ExamDbSchema.OptionTable.Cols.QUESTION_ID, questionId);
                newOption.put(ExamDbSchema.OptionTable.Cols.TEXT, option.getText());
                this.store.insert(ExamDbSchema.OptionTable.NAME, null, newOption);
                newOption.clear();
            }
        }
        startActivity(new Intent(this, ExamListActivity.class));
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.beginTransaction()
//                .replace(R.id.exams, new ExamListFragment())
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onNewQuestionButtonClicked() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.add_container, new QuestionAddFragment())
                .addToBackStack(null)
                .commit();
    }
}
