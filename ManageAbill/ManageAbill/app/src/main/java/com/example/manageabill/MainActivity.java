package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {
    private Spinner spinnerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerUser = findViewById(R.id.spinner_user);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);

        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String item = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).equals("")) {

                }

                else{
                   /* String item = parent.getItemAtPosition(position).toString();*/

                   /* Toast.makeText(parent.getContext() + item)*/

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
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}