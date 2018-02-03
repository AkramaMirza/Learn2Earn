package com.akrama.learn2earn.teacherhome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by akrama on 31/01/18.
 */

public class TeacherHomeAssignmentAdapter extends RecyclerView.Adapter<TeacherHomeAssignmentAdapter.ViewHolder> {

    private static final Consumer<Assignment> EMPTY_CONSUMER = s -> {};

    private List<Assignment> mAssignments = new ArrayList<>();
    private Context mContext;
    private Consumer<Assignment> mOnClickListener = EMPTY_CONSUMER;

    public TeacherHomeAssignmentAdapter(Context context) {
        mContext = context;
    }

    public void setOnClickListener(Consumer<Assignment> onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setAssignments(List<Assignment> assignments) {
        mAssignments = assignments;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.teacher_home_assignments_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Assignment assignment = mAssignments.get(position);
        holder.mAssignmentNameTextView.setText(assignment.getName());
    }

    @Override
    public int getItemCount() {
        return mAssignments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mAssignmentNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mAssignmentNameTextView = itemView.findViewById(R.id.assignments_list_item_name_text_view);
            itemView.setOnClickListener(v -> {
                mOnClickListener.accept(mAssignments.get(getAdapterPosition()));
            });
        }
    }
}
