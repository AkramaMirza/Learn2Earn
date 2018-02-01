package com.akrama.learn2earn.studenthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.Assignment;

/**
 * Created by akrama on 26/01/18.
 */

public class CreateBetAssignmentAdapter extends ArrayAdapter<Assignment> {

    private static class ViewHolder {
        public TextView assignmentNameTextView;
        public ViewHolder(View rootView) {
            assignmentNameTextView = rootView.findViewById(R.id.assignments_list_item_name_text_view);
        }
    }

    public CreateBetAssignmentAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.create_bet_assignments_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Assignment assignment = getItem(position);
        viewHolder.assignmentNameTextView.setText(assignment.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.create_bet_assignments_drop_down_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Assignment assignment = getItem(position);
        viewHolder.assignmentNameTextView.setText(assignment.getName());
        return convertView;
    }
}
