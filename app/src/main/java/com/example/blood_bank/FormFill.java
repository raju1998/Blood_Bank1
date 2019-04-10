package com.example.blood_bank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormFill extends AppCompatActivity {
    EditText name,blood,number;
    Button submit;

    FirebaseDatabase database;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fill);

        name=findViewById(R.id.take_name);
        blood=findViewById(R.id.take_blood);
        number=findViewById(R.id.take_number);

        database=FirebaseDatabase.getInstance();
        myref=database.getReference("message");
        submit=findViewById(R.id.submitDetails);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname= name.getText().toString();
                String ublood=blood.getText().toString();
                String unumber=number.getText().toString();

                String key=myref.push().getKey();
                User_details user_details=new User_details();

                user_details.set_name(uname);
                user_details.set_BloodGroup(ublood);
                user_details.set_number(unumber);

                myref.child(key).setValue(user_details);
                name.setText("");
                number.setText("");
                blood.setText("");

                startActivity(new Intent(FormFill.this,HomePage.class));
                finish();
            }

        });

    }
}
