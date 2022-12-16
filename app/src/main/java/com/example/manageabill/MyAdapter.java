package com.example.manageabill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList expenseName,expenseAmount,etDate, reminder,reminderTime, notes;

    public MyAdapter(Context context, ArrayList expenseName, ArrayList expenseAmount, ArrayList etDate, ArrayList reminder, ArrayList reminderTime, ArrayList notes) {
        this.context = context;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.etDate = etDate;
        this.reminder = reminder;
        this.reminderTime = reminderTime;
        this.notes = notes;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_entry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.expenseName.setText(String.valueOf(expenseName.get(position)));
    holder.expenseAmount.setText(String.valueOf(expenseAmount.get(position)));
    holder.etDate.setText(String.valueOf(etDate.get(position)));
    holder.reminder.setText(String.valueOf(reminder.get(position)));
    holder.reminderTime.setText(String.valueOf(reminderTime.get(position)));
    holder.notes.setText(String.valueOf(notes.get(position)));

    }

    @Override
    public int getItemCount() {
        return expenseName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expenseName, expenseAmount, etDate, reminder,reminderTime, notes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.textName);
            expenseAmount = itemView.findViewById(R.id.textAmount);
            etDate = itemView.findViewById(R.id.textDue);
            reminder = itemView.findViewById(R.id.textReminder);
            reminderTime = itemView.findViewById((R.id.textReminderTime));
            notes = itemView.findViewById(R.id.textNotes);
        }
    }
}
