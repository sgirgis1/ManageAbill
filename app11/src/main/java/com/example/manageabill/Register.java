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

public class Register extends AppCompatActivity {
    private Spinner spinnerUser;
    private Button registerBtn,signinBtn;
    private TextInputEditText username, password, confPassword;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account);

        DB = new DBHelper(this);

        signinBtn = findViewById(R.id.idBtnSignin);
        username = findViewById(R.id.idEdtUsernameSignup);
        password = findViewById(R.id.idEdtPasswordLogin);
        confPassword = findViewById(R.id.idEdtConfirmPassword);
        registerBtn = findViewById(R.id.idBtnRegister);
        spinnerUser = findViewById(R.id.spinner_user);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);

        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Log In")) {

                    Intent intent = new Intent(Register.this, LogIn.class);
                    startActivity(intent);
                } else {
                    if (parent.getItemAtPosition(position).equals("Split Bill")) {

                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confPass = confPassword.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confPass))
                    Toast.makeText(Register.this, "All fields Required", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(confPass)) {
                        Boolean checkUser = DB.checkUsername(user);
                        if (checkUser == false) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert == true) {
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ViewBill.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "User already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });
    }
}

