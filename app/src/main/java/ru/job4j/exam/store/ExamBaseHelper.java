package ru.job4j.exam.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExamBaseHelper extends SQLiteOpenHelper {
    public static final String DB = "exams.db";
    public static final int VERSION = 1;

    public ExamBaseHelper(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + ExamDbSchema.ExamTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        ExamDbSchema.ExamTable.Cols.TITLE + " text, " +
                        ExamDbSchema.ExamTable.Cols.DESC + " text, " +
                        ExamDbSchema.ExamTable.Cols.RESULT + " integer, " +
                        ExamDbSchema.ExamTable.Cols.DATE + " text" +
                        ")"
        );
        db.execSQL(
                "create table " + ExamDbSchema.QuestionTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        ExamDbSchema.QuestionTable.Cols.TEXT + " text, " +
                        ExamDbSchema.QuestionTable.Cols.EXAM_ID + " integer, " +
                        ExamDbSchema.QuestionTable.Cols.POSITION + " text, " +
                        ExamDbSchema.QuestionTable.Cols.ANSWER + " text" +
                        ")"
        );
        db.execSQL(
                "create table " + ExamDbSchema.OptionTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        ExamDbSchema.OptionTable.Cols.TEXT + " text, " +
                        ExamDbSchema.OptionTable.Cols.QUESTION_ID + " integer" +
                        ")"
        );
        db.execSQL(
                "create table " + ExamDbSchema.RightAnswerTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        ExamDbSchema.RightAnswerTable.Cols.QUESTION_ID + " integer, " +
                        ExamDbSchema.RightAnswerTable.Cols.OPTION_ID + " integer " +
                        ")"
        );
        db.execSQL(
                "create table " + ExamDbSchema.AnswerTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        ExamDbSchema.AnswerTable.Cols.OPTION_ID + " integer, " +
                        ExamDbSchema.AnswerTable.Cols.EXAM_ID + " integer" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}