package com.example.blood_bank;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormFill extends AppCompatActivity {
    EditText name;
    String blood;
    EditText number;
    EditText location;
    Button submit;
    Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference myref;

    Spinner spinner;
    String [] array={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fill);

        name=findViewById(R.id.take_name);
        spinner=findViewById(R.id.take_blood);
        number=findViewById(R.id.take_number);
        location=findViewById(R.id.take_city);


        database=FirebaseDatabase.getInstance();
        myref=database.getReference("message");
        submit=findViewById(R.id.submitDetails);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(FormFill.this,android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        blood= spinner.getSelectedItem().toString();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname= name.getText().toString();
                String ublood=blood;
                String unumber=number.getText().toString();
                String ulocation=location.getText().toString();
                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ublood)){
                    Toast.makeText(getApplicationContext(),"Please select your blood group",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(unumber)){
                    Toast.makeText(getApplicationContext(),"Enter number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ulocation)){
                    Toast.makeText(getApplicationContext(),"Enter location",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String key=myref.push().getKey();
                User_details user_details=new User_details();

                user_details.set_name(uname);
                user_details.set_BloodGroup(ublood);
                user_details.set_number(unumber);
                user_details.set_location(ulocation);

                myref.child(key).setValue(user_details);
                name.setText("");
                number.setText("");
                blood.isEmpty();
                location.setText("");

                startActivity(new Intent(FormFill.this,HomePage.class));
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(FormFill.this, "hello");
                builder.setContentTitle("Blood Bank");
                builder.setContentText("Thank you for your support !");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setAutoCancel(true);
                builder.setDefaults(Notification.DEFAULT_ALL);

                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("hello", "hey", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("");
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                notificationManager.notify(0, builder.build());
                finish();
            }

        });

    }
}
