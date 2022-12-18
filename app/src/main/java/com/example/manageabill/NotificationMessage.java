package com.example.manageabill;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationMessage extends AppCompatActivity {
    TextView textView;
    Button viewBillsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        viewBillsBtn=findViewById(R.id.returnButton);
        viewBillsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewBill.class);
                startActivity(intent);
            }
        });
        textView = findViewById(R.id.message);
    }

}
