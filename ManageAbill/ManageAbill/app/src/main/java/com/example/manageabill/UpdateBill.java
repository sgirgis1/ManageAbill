package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class UpdateBill extends AppCompatActivity {
    private Spinner spinnerBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bill);

        spinnerBill = findViewById(R.id.spinner_bill);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bill, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBill.setAdapter(adapter);

        spinnerBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*String item = parent.getItemAtPosition(position).toString();*/
                if (parent.getItemAtPosition(position).equals("")) {

                }

                else{
                    /* String item = parent.getItemAtPosition(position).toString();*/

                    /* Toast.makeText(parent.getContext() + item)*/

                    if (parent.getItemAtPosition(position).equals("Add Bill")){

                        Intent intent = new Intent(UpdateBill.this, AddBill.class);
                        startActivity(intent);
                    }
                    else {
                        if (parent.getItemAtPosition(position).equals("View Bills")){
                            Intent intent = new Intent (UpdateBill.this,ViewBill.class);
                            startActivity(intent);
                        }
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}