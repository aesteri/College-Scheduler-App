package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final View parentView;
    private final ArrayList<LocalDate> days;
    private final CalenderAdapter.OnItemListener onItemListener;
    public final TextView dayOfMonth;
    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
