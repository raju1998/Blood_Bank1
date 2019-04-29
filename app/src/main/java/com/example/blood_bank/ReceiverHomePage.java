package com.example.blood_bank;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceiverHomePage extends AppCompatActivity {
    RecyclerView recycler;
    DrawerLayout drawerLayout;
    private Context context;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase database;
    private DatabaseReference myref;
    FirebaseAuth.AuthStateListener mAuthListener;
    RecyclerView recyclerView;
    TextView blood,name,number,location;
    List<List_data> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home_page);
        recycler=findViewById(R.id.recyler_view2);
        toolbar=findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);
        


        blood=findViewById(R.id.blood_group);
        name=findViewById(R.id.text_name);
        number=findViewById(R.id.mobile_number);
        location=findViewById(R.id.take_city);
        final ProgressDialog progressDialog=new ProgressDialog(ReceiverHomePage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        database= FirebaseDatabase.getInstance();
        myref=database.getReference("message");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot==null)
                {
                    Toast.makeText(ReceiverHomePage.this,"nahi Hua",Toast.LENGTH_SHORT).show();
                }
                list=new ArrayList<>();
                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    User_details user_details=dataSnapshot1.getValue(User_details.class);
                    List_data list_data=new List_data();
                    assert user_details != null;
                    String blood=user_details.get_BloodGroup();
                    String name=user_details.get_name();
                    String number=user_details.get_number();
                    String location=user_details.get_location();

                    list_data.set_BloodGroup(blood);
                    list_data.set_name(name);
                    list_data.set_number(number);
                    list_data.set_location(location);

                    list.add(list_data);
                    progressDialog.dismiss();
                }
        RecyclerviewAdapter recyclerviewAdapter=new RecyclerviewAdapter(context,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ReceiverHomePage.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(recyclerviewAdapter);
    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReceiverHomePage.this,"nahi Hua",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
