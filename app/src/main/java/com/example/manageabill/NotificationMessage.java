package com.example.manageabill;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class NotificationMessage extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        textView = findViewById(R.id.message);
        //call the data which is passed by another intent
        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));


    }

}
