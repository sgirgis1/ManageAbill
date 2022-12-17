package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Date;


public class AddBill extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerBill;
    private TextInputEditText expenseName, expenseAmount, etDate, reminderDate, reminderTime, expenseNote;
    private TextInputLayout dueDate;
    private Button submitBtn;
    private String timeToNotify;
    private DatePickerDialog.OnDateSetListener setListener;
    DBHelper DB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_bill);

            DB = new DBHelper(this);
            expenseName = findViewById(R.id.idEdtBillName);
            expenseAmount = findViewById(R.id.idEdtBillAmount);
            reminderDate = findViewById(R.id.idEdtReminder);
            reminderTime = findViewById(R.id.idEdtReminderTime);
            dueDate = findViewById(R.id.idDueDate);
            etDate = findViewById(R.id.idEdtDueDate);
            expenseNote = findViewById(R.id.idEdtNotes);
            submitBtn = findViewById(R.id.idBtnSubmit);

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
                    if (parent.getItemAtPosition(position).equals("View Bills")) {

                        Intent intent = new Intent(AddBill.this, ViewBill.class);
                        startActivity(intent);

                    } else {
                        if (parent.getItemAtPosition(position).equals("Update Bill")) {
                            Intent intent = new Intent(AddBill.this, UpdateBill.class);
                            startActivity(intent);

                        } else {
                            if (parent.getItemAtPosition(position).equals("Split Bill")) {
                                Intent intent = new Intent(AddBill.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                if (parent.getItemAtPosition(position).equals("Log Out")) {
                                    finishAffinity();
                                    startActivity(new Intent(AddBill.this, LogIn.class));

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
                            AddBill.this, new DatePickerDialog.OnDateSetListener() {
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

            reminderDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            AddBill.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            month = month + 1;
                            String date = month + "/" + day + "/" + year;
                            reminderDate.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            });

            reminderTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            AddBill.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            timeToNotify=hour + ":" + minute;
                            reminderTime.setText(FormatTime(hour,minute));
                        }
                    }, hour, minute,false);
                    timePickerDialog.show();
                }

            });



            submitBtn.setOnClickListener(v -> {
                String name = expenseName.getText().toString();
                String amount = expenseAmount.getText().toString();
                String date = etDate.getText().toString();
                String rem = reminderDate.getText().toString();
                String remTime = reminderTime.getText().toString();
                String note = expenseNote.getText().toString();

                Boolean checkInsertData = DB.insertData(name, amount, date, rem, remTime, note);
                if (checkInsertData == true) {
                    processInsert(name, rem, remTime);
                    Toast.makeText(AddBill.this, "Bill Successfully Saved!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddBill.this, AddBill.class));
                } else
                    Toast.makeText(AddBill.this, "Fill Required Fields", Toast.LENGTH_SHORT).show();
            });
        }

    private void processInsert(String name, String rem, String remTime) {
        //calls the set alarm method to set alarm
        setAlarm(name, rem, remTime);

    }

    private void setAlarm(String name, String rem, String remTime) {
        //assigning alarm manager object to set alarm
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        //sending data to alarm class to create channel and notification
        intent.putExtra("event", name);
        intent.putExtra("date", rem);
        intent.putExtra("time", remTime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 5, intent, PendingIntent.FLAG_IMMUTABLE);//error here??
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent.isImmutable();
        }
        String dateandtime = rem + " " + timeToNotify;
System.out.println(dateandtime);
        DateFormat formatter = new SimpleDateFormat("M/d/yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //this intent will be called once the setting alarm is complete
        Intent intentBack = new Intent(getApplicationContext(), ViewBill.class);
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //navigates from adding reminder activity to ViewBill activity
        startActivity(intentBack);
    }


    @Override
    public void onClick(View view) {

    }
        public String FormatTime(int hr, int min) {
        String time;
        time = "";
        String formattedMinute;

        if(min /10 ==0) {
            formattedMinute = "0" + min;
        }else {
            formattedMinute = "" +min;
        }
        if (hr == 0) {
            time = "12" + ":" + formattedMinute+ "AM";
        } else if (hr < 12) {
            time = hr+ ":" + formattedMinute +"AM";
        } else if (hr ==12) {
            time = "12"+ ":" + formattedMinute +"PM";
        } else {
            int temp = hr -12;
            time = temp + ":" + formattedMinute +"PM";
        }
        return time;
    }

    }

