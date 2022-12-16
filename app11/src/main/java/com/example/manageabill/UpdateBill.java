package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class UpdateBill extends AppCompatActivity {
    private Spinner spinnerBill;
    private TextInputEditText expenseName, expenseAmount, etDate, reminder, reminderTime, expenseNote;
    private TextInputLayout dueDate;
    private Button updateBtn, deleteBtn;
    private DatePickerDialog.OnDateSetListener setListener;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bill);

        DB = new DBHelper(this);
        updateBtn = findViewById(R.id.idBtnUpdate);
        deleteBtn = findViewById(R.id.idBtnDelete);
        expenseName = findViewById(R.id.idEdtChooseBill);
        expenseAmount = findViewById(R.id.idEdtBillAmount);
        etDate = findViewById(R.id.idEdtDueDate);
        reminder = findViewById(R.id.idEdtReminder);
        expenseNote = findViewById(R.id.idEdtNotes);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        spinnerBill = findViewById(R.id.spinner_bill);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bill, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBill.setAdapter(adapter);

        spinnerBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Add Bill")){

                    Intent intent = new Intent(UpdateBill.this, AddBill.class);
                    startActivity(intent);

                }
                else {
                    if (parent.getItemAtPosition(position).equals("View Bills")){
                        Intent intent = new Intent (UpdateBill.this,ViewBill.class);
                        startActivity(intent);
                    }
                    else {
                        if (parent.getItemAtPosition(position).equals("Split Bill")) {

                            Intent intent = new Intent(UpdateBill.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            if (parent.getItemAtPosition(position).equals("Log Out")) {

                                finishAffinity();
                                startActivity(new Intent(UpdateBill.this,LogIn.class));
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateBill.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = month + "/" + day + "/" + year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }

        });


        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = expenseName.getText().toString();
                String amount = expenseAmount.getText().toString();
                String date = etDate.getText().toString();
                String rem = reminder.getText().toString();
                String remTime = reminderTime.getText().toString();
                String note = expenseNote.getText().toString();

                Boolean checkUpdateData = DB.updateData(name,amount,date,rem, remTime, note);
                if(checkUpdateData == true) {
                    Toast.makeText(UpdateBill.this, "Bill Successfully Updated!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateBill.this, UpdateBill.class));
                }
                else
                    Toast.makeText(UpdateBill.this, "Unable to Update Bill", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = expenseName.getText().toString();

                Boolean checkDeleteData = DB.deleteData(name);
                if(checkDeleteData == true) {
                    Toast.makeText(UpdateBill.this, "Bill Deleted!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateBill.this, UpdateBill.class));
                }

                else
                    Toast.makeText(UpdateBill.this, "Unable to Find Bill", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

