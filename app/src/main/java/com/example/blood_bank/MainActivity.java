package com.example.blood_bank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button signin;
    //private final static int RC_SIGN_IN=123;
    private  FirebaseAuth mAuth;
    String TAG= "";
    FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mProgress;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
         mAuth=FirebaseAuth.getInstance();
        mProgress =new ProgressDialog(this);


         email =findViewById(R.id.login_email);
         password=findViewById(R.id.login_password);
         signin=findViewById(R.id.login_button);



        TextView signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
         signin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String email1= email.getText().toString();
                 String pass= password.getText().toString();
                 if(TextUtils.isEmpty(email1)){
                     Toast.makeText(getApplicationContext(),"Please enter email id",Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if(TextUtils.isEmpty(pass)){
                     Toast.makeText(getApplicationContext(),"Enter Password",
                             Toast.LENGTH_SHORT).show();
                     return;
                 }
                 mProgress.setMessage("Please wait...");
                 mProgress.show();

                 mAuth.signInWithEmailAndPassword(email1,pass).addOnCompleteListener(MainActivity.this,
                         new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if(task.isSuccessful()){
                                     Log.d(TAG,"signIn Success");
                                     mProgress.dismiss();
                                     Intent intent =new Intent(MainActivity.this,HomePage.class);
                                     startActivity(intent);
                                     finish();

                                 }
                                 else {
                                     Log.d(TAG,"Signin: Fail");
                                     Toast.makeText(MainActivity.this," Failed",Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
             }
         });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                   // Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i= new Intent(MainActivity.this,SignUpActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            }
        };



    }


}
