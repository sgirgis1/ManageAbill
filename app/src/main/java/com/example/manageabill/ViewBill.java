package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class ViewBill extends AppCompatActivity {
    private Spinner spinnerBill;
    DBHelper DB;
    RecyclerView recyclerView;
    ArrayList<String> name,amount,due,reminder,reminderTime,notes;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bill);

        DB = new DBHelper(this);
        name = new ArrayList<>();
        amount = new ArrayList<>();
        due = new ArrayList<>();
        reminder = new ArrayList<>();
        reminderTime = new ArrayList<>();
        notes = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, name, amount, due, reminder,reminderTime, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
        spinnerBill = findViewById(R.id.spinner_bill);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bill, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBill.setAdapter(adapter);

        spinnerBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Add Bill")){

                        Intent intent = new Intent(ViewBill.this, AddBill.class);
                        startActivity(intent);
                    }
                    else {
                        if (parent.getItemAtPosition(position).equals("Update Bill")){
                            Intent intent = new Intent (ViewBill.this,UpdateBill.class);
                            startActivity(intent);
                        }
                        else {
                            if (parent.getItemAtPosition(position).equals("Split Bill")) {

                                Intent intent = new Intent(ViewBill.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                if (parent.getItemAtPosition(position).equals("Log Out")) {

                                    finishAffinity();
                                    startActivity(new Intent(ViewBill.this,LogIn.class));
                                }
                            }
                        }
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void displayData() {
        Cursor cursor = DB.getData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(ViewBill.this, "No Bills Saved", Toast.LENGTH_SHORT ).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                amount.add(cursor.getString(2));
                due.add(cursor.getString(3));
                reminder.add(cursor.getString(4));
                reminderTime.add(cursor.getString(5));
                notes.add(cursor.getString(6));
            }
        }
    }
}