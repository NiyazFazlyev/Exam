package ru.job4j.exam.store;

public class ExamDbSchema {
    public static final class ExamTable {
        public static final String NAME = "exams";

        public static final class Cols {
            public static final String TITLE = "title";
            public static final String DESC = "description";
            public static final String RESULT = "result";
            public static final String DATE = "date";
        }
    }

    public static final class QuestionTable {
        public static final String NAME = "questions";

        public static final class Cols {
            public static final String TEXT = "text";
            public static final String EXAM_ID = "exam_id";
            public static final String POSITION = "position";
            public static final String ANSWER = "answer";

        }
    }

    public static final class OptionTable {
        public static final String NAME = "options";

        public static final class Cols {
            public static final String TEXT = "text";
            public static final String QUESTION_ID = "question_id";
        }
    }

    public static final class RightAnswerTable {
        public static final String NAME = "right_answers";

        public static final class Cols {
            public static final String QUESTION_ID = "question_id";
            public static final String OPTION_ID = "option_id";
        }
    }

    public static final class AnswerTable {
        public static final String NAME = "answers";

        public static final class Cols {
            public static final String OPTION_ID = "option_id";
            public static final String QUESTION_ID = "question_id";
            public static final String EXAM_ID = "exam_id";
        }
    }
}
