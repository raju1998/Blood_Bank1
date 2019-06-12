package com.example.blood_bank;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG= "";
    EditText signup_email,signup_pass,signup_name;
    Button register, login;

    private ProgressDialog mProgress;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mProgress =new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference();

        signup_name=findViewById(R.id.signup_name);
        signup_email=findViewById(R.id.signup_email);
        signup_pass=findViewById(R.id.signup_password);
        register=findViewById(R.id.signup_button);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setMessage("Signing up...");
                mProgress.setCancelable(false);
                mProgress.show();

                final String name= signup_name.getText().toString();
                String email=signup_email.getText().toString();
                String password=signup_pass.getText().toString();



                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email id",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter Password",
                            Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    String user_id= mAuth.getCurrentUser().getUid();
                                    Log.d(TAG,"CreateUserWithEmail: success");
                                    FirebaseUser user =mAuth.getCurrentUser();
                                    DatabaseReference current_user=mDatabase.child(user_id);
                                    mProgress.dismiss();
                                    current_user.child("name").setValue(name);
                                    Intent i= new Intent(SignUpActivity.this,FormFill.class);
                                    startActivity(i);


                                }else{
                                    mProgress.dismiss();
                                    Log.w(TAG,"CreateUser: Failure",task.getException());
                                    Toast.makeText(SignUpActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

}
