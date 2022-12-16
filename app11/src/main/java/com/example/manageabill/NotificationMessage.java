package com.example.manageabill;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//this class creates the Reminder Notification Message
public class NotificationMessage extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
       // AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Intent intent1 = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        //intent1.addCategory("android.intent.category.DEFAULT");

        //PendingIntent broadcast = PendingIntent.getBroadcast(this,0,intent1,PendingIntent.FLAG_IMMUTABLE);

        textView = findViewById(R.id.message);
        Bundle bundle = getIntent().getExtras();                                                    //call the data which is passed by another intent
        textView.setText(bundle.getString("message"));

    }
}
