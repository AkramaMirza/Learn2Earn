package com.akrama.learn2earn.studenthome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.CompressedBet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akrama on 27/01/18.
 */

public class StudentHomeActiveBetAdapter extends RecyclerView.Adapter<StudentHomeActiveBetAdapter.ViewHolder> {

    private List<CompressedBet> mBets = new ArrayList<>();
    private Context mContext;

    public StudentHomeActiveBetAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.active_bets_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompressedBet bet = mBets.get(position);
        holder.mAssignmentNameTextView.setText(bet.getAssignmentName());
        holder.mBetValueTextView.setText(String.format("%s ETH", bet.getBetValue()));
        holder.mGradeTextView.setText(String.format(mContext.getString(R.string.home_minimum_grade), bet.getBetGrade()));
    }

    @Override
    public int getItemCount() {
        return mBets.size();
    }

    public void setBets(List<CompressedBet> bets) {
        mBets = bets;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAssignmentNameTextView;
        TextView mBetValueTextView;
        TextView mGradeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mAssignmentNameTextView = itemView.findViewById(R.id.active_bets_list_item_assignment_name_text_view);
            mBetValueTextView = itemView.findViewById(R.id.active_bets_list_item_bet_value_text_view);
            mGradeTextView = itemView.findViewById(R.id.active_bets_list_item_bet_grade_text_view);
        }
    }
}
