package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText amountEdt, peopleEdt, tipEdt;
    private TextView amtTV;
    private Button resetBtn, amtBtn;
    private Spinner spinnerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //initializing variables
        amountEdt = findViewById(R.id.idEdtAmount);
        peopleEdt = findViewById(R.id.idEdtPeople);
        tipEdt = findViewById(R.id.idEdtTip);
        amtBtn = findViewById(R.id.idBtnGetAmount);
        resetBtn = findViewById(R.id.idBtnReset);
        amtTV = findViewById(R.id.idTVIndividualAmount);

        spinnerUser = findViewById(R.id.spinner_user);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);

        //adding listener for drop down menu
        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Register")){

                        Intent intent = new Intent(MainActivity.this, Register.class);
                        startActivity(intent);
                    }
                    else {
                        if (parent.getItemAtPosition(position).equals("Log In")){
                            Intent intent = new Intent (MainActivity.this,LogIn.class);
                            startActivity(intent);
                            }
                        }
                    }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // adding click listener for amount button.
        amtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if edit text is not empty
                if (amountEdt.getText().toString() != null && peopleEdt.getText().toString() != null && tipEdt.getText().toString() != null) {
                    Float tipPerc;
                    Float individualAmt;
                    Float totalBill;

                    tipPerc = (Float.parseFloat(tipEdt.getText().toString())/100);
                    totalBill = (Float.parseFloat(amountEdt.getText().toString()) + ((Float.parseFloat(amountEdt.getText().toString()) *tipPerc)));
                    individualAmt = (totalBill + tipPerc) / Float.parseFloat(
                            peopleEdt.getText().toString());
                   Double roundOff = Math.round(individualAmt*100.0)/100.0;
                    // setting amount to text view.
                    amtTV.setText("Individual Amount : \n" + "$"+roundOff);
                }
            }
        });

        // adding click listener for reset button.
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting empty text for edit text.
                amountEdt.setText("");
                peopleEdt.setText("");
                tipEdt.setText("");
                amtTV.setText("");
            }
        });
    }
}
