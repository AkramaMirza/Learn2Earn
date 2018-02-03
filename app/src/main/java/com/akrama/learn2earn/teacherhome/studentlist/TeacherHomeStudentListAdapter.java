package com.akrama.learn2earn.teacherhome.studentlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by akrama on 02/02/18.
 */

public class TeacherHomeStudentListAdapter extends RecyclerView.Adapter<TeacherHomeStudentListAdapter.ViewHolder> {

    private static final Consumer<Integer> EMPTY_LISTENER = integer -> {};

    private List<Student> mStudents = new ArrayList<>();
    private Integer[] mGrades;
    private Context mContext;
    private Consumer<Integer> mOnClickListener = EMPTY_LISTENER;

    public TeacherHomeStudentListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.teacher_home_students_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = mStudents.get(position);
        holder.mStudentNameTextView.setText(student.getStudentName());
        if (mGrades[position] != null) {
            holder.mStudentGradeTextView.setText(String.format("%d%%", mGrades[position]));
        } else {
            holder.mStudentGradeTextView.setText("");
        }
    }

    public void setStudents(List<Student> studentList) {
        mStudents = studentList;
        mGrades = new Integer[mStudents.size()];
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(Consumer<Integer> listener) {
        mOnClickListener = listener;
    }

    public String getStudentName(int index) {
        return mStudents.get(index).getStudentName();
    }

    public List<Student> getStudents() {
        return mStudents;
    }

    public Integer[] getGrades() {
        return mGrades;
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public void setGrade(Integer index, Integer grade) {
        mGrades[index] = grade;
        notifyItemChanged(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mStudentNameTextView;
        private TextView mStudentGradeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mStudentNameTextView = itemView.findViewById(R.id.students_list_item_name_text_view);
            mStudentGradeTextView = itemView.findViewById(R.id.students_list_item_grade_text_view);
            itemView.setOnClickListener(v -> {
                mOnClickListener.accept(getAdapterPosition());
            });
        }
    }
}
