package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdateBill extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerBill;
    private TextInputEditText expenseName, expenseAmount, etDate, reminderDate, reminderTime, expenseNote;
    private TextInputLayout dueDate;
    private Button updateBtn, deleteBtn;
    private String timeToNotify;
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
        reminderDate = findViewById(R.id.idEdtReminder);
        reminderTime = findViewById(R.id.idEdtReminderTime);
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
                String rem = reminderDate.getText().toString();
                String remTime = reminderTime.getText().toString();
                String note = expenseNote.getText().toString();

                Boolean checkUpdateData = DB.updateData(name,amount,date,rem, remTime, note);
                if(checkUpdateData == true) {
                    processInsert(name,rem,remTime);
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

        reminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateBill.this, new DatePickerDialog.OnDateSetListener() {
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
                        UpdateBill.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeToNotify = hour + ":" + minute;
                        reminderTime.setText(FormatTime(hour,minute));
                    }
                }, hour, minute,false);
                timePickerDialog.show();
            }

        });



    }

    @Override
    public void onClick(View v) {

    }
    private void processInsert(String name, String rem, String remTime) {
        setAlarm(name, rem, remTime);
    }

    private void setAlarm(String name, String rem, String remTime) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", name);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("date", rem);
        intent.putExtra("time", remTime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);
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
    public void createNotificationChannel() {
        String id = "channelID";
        String name = "Daily Alerts";
        String des = "Channel Description A Brief";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(id, name, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription(des);
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }
    }
    public void scheduleNotification(String date, String time) throws ParseException {
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        intent.putExtra("titleExtra", "Dynamic Title");
        intent.putExtra("textExtra", "Dynamic Text Body");
        String dateTime = date + " "+ timeToNotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        Date date1= formatter.parse(dateTime);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
        }
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

