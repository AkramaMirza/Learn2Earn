package com.akrama.learn2earn.parenthome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akrama.learn2earn.R;
import com.akrama.learn2earn.model.CompressedBet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by akrama on 27/01/18.
 */

public class ParentHomeActiveBetAdapter extends RecyclerView.Adapter<ParentHomeActiveBetAdapter.ViewHolder> {

    private static final Consumer<CompressedBet> EMPTY_LISTENER = aVoid -> {};

    private List<CompressedBet> mBets = new ArrayList<>();
    private Context mContext;
    private Consumer<CompressedBet> mOnConfirmClickListener = EMPTY_LISTENER;

    public ParentHomeActiveBetAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.parent_home_active_bets_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompressedBet bet = mBets.get(position);
        holder.mAssignmentNameTextView.setText(bet.getAssignmentName());
        holder.mBetValueTextView.setText(String.format("%s ETH", bet.getBetValue()));
        holder.mGradeTextView.setText(String.format(mContext.getString(R.string.home_minimum_grade), bet.getBetGrade()));
        if (bet.isConfirmed()) {
            holder.mConfirmButton.setVisibility(View.GONE);
        } else {
            holder.mConfirmButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mBets.size();
    }

    public void setBets(List<CompressedBet> bets) {
        mBets = bets;
        notifyDataSetChanged();
    }

    public void setOnConfirmClickListener(Consumer<CompressedBet> listener) {
        mOnConfirmClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAssignmentNameTextView;
        TextView mBetValueTextView;
        TextView mGradeTextView;
        Button mConfirmButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mAssignmentNameTextView = itemView.findViewById(R.id.active_bets_list_item_assignment_name_text_view);
            mBetValueTextView = itemView.findViewById(R.id.active_bets_list_item_bet_value_text_view);
            mGradeTextView = itemView.findViewById(R.id.active_bets_list_item_bet_grade_text_view);
            mConfirmButton = itemView.findViewById(R.id.active_bets_list_item_confirm_btn);
            mConfirmButton.setOnClickListener(v -> mOnConfirmClickListener.accept(mBets.get(getAdapterPosition())));
        }
    }
}
