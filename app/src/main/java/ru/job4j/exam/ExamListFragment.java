package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.add.AddActivity;
import ru.job4j.exam.add.ExamUpdateFragment;
import ru.job4j.exam.store.ExamDbSchema;
import ru.job4j.exam.store.Store;

public class ExamListFragment extends Fragment {
    private RecyclerView recycler;
    private Store store;
    private onTitleClickListener callback;

    public interface onTitleClickListener {
        void onTitleClicked(Exam exam);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        this.recycler = view.findViewById(R.id.list);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.store = Store.getStore(getContext());
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Exam> exams = new ArrayList<Exam>();
        Cursor cursor = this.store.find(
                ExamDbSchema.ExamTable.NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exams.add(new Exam(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex(ExamDbSchema.ExamTable.Cols.TITLE)),
                    "description",
                    cursor.getString(cursor.getColumnIndex(ExamDbSchema.ExamTable.Cols.DATE)),
                    cursor.getInt(cursor.getColumnIndex(ExamDbSchema.ExamTable.Cols.RESULT))
            ));
            //TODO добавить description
            cursor.moveToNext();
        }
        cursor.close();
        this.recycler.setAdapter(new ExamAdapter(exams));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.exams, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_exam:
                startActivity(new Intent(getActivity(), AddActivity.class));
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.beginTransaction()
//                        .replace(R.id.exams, new ExamAddFragment())
//                        .addToBackStack(null)
//                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ExamHolder extends RecyclerView.ViewHolder {

        private View view;

        public ExamHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamHolder> {

        private final List<Exam> exams;

        public ExamAdapter(List<Exam> exams) {
            this.exams = exams;
        }

        @NonNull
        @Override
        public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.info_exam, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int i) {
            Exam exam = this.exams.get(i);
            TextView text = holder.view.findViewById(R.id.q_text);
            TextView res = holder.view.findViewById(R.id.res);
            TextView date = holder.view.findViewById(R.id.date);
            if ((i % 2) == 0) {
                holder.view.setBackgroundColor(Color.parseColor("#d8d8d8"));
            }

            text.setText(exam.getName());
            res.setText(String.valueOf(exam.getResult()) + "%");
            date.setText(exam.getTime());

            text.setOnClickListener(
                    btn -> {
                        callback.onTitleClicked(exam);
                    }
            );
            holder.view.findViewById(R.id.edit)
                    .setOnClickListener(
                            btn -> {
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", exam.getId());
                                bundle.putString("name", exam.getName());
                                bundle.putString("desc", exam.getDesc());
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Fragment fragment = new ExamUpdateFragment();
                                fragment.setArguments(bundle);
                                fm.beginTransaction()
                                        .replace(R.id.exams, fragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                    );

            holder.view.findViewById(R.id.delete)
                    .setOnClickListener(
                            btn -> {
                                store.delete(ExamDbSchema.ExamTable.NAME, "id = " + String.valueOf(exam.getId()));
                                exams.remove(exam);
                                notifyItemRemoved(i);
                            }
                    );
        }

        @Override
        public int getItemCount() {
            return this.exams.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (onTitleClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }
}