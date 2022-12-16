package com.example.manageabill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LogIn extends AppCompatActivity {
    private Spinner spinnerUser;
    private Button loginBtn,registerBtn;
    private TextInputEditText username, password;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        DB = new DBHelper(this);
        username = findViewById(R.id.idEdtUsernameLogin);
        password = findViewById(R.id.idEdtPasswordLogin);
        loginBtn = findViewById(R.id.idBtnLogin);
        registerBtn = findViewById(R.id.idBtnSignup);
        spinnerUser = findViewById(R.id.spinner_user);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);

        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Register")){

                    Intent intent = new Intent(LogIn.this, Register.class);
                    startActivity(intent);
                }
                    else {
                        if (parent.getItemAtPosition(position).equals("Split Bill")) {

                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(LogIn.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserPass = DB.checkUsernamePassword(user,pass);
                    if (checkUserPass == true) {
                        Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn.this, ViewBill.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}

