package com.example.blood_bank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.*;
import static com.example.blood_bank.R.id.nav_logout;

public class HomePage extends AppCompatActivity {
    //FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    private Context context;
    Toolbar toolbar;
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase database;
    private DatabaseReference myref;
    FirebaseAuth mAuth;


    FirebaseAuth.AuthStateListener mAuthListener;
    RecyclerView recyclerView;

    TextView blood,name,number,location;
    ImageView phone,message;
    List<List_data> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView=findViewById(R.id.recyler_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAuth=FirebaseAuth.getInstance();


        toolbar=findViewById(R.id.toolbar_nav);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle=new ActionBarDrawerToggle(HomePage.this,drawerLayout,R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);



        blood=findViewById(R.id.blood_group);
        name=findViewById(R.id.text_name);
        number=findViewById(R.id.mobile_number);
        location=findViewById(R.id.take_city);


        database=FirebaseDatabase.getInstance();
        myref=database.getReference("message");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

                }

                RecyclerviewAdapter recyclerviewAdapter=new RecyclerviewAdapter(context,list);
                LinearLayoutManager layoutManager=new LinearLayoutManager(HomePage.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerviewAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                                Intent i= new Intent(HomePage.this,MainActivity.class);
                                startActivity(i);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                break;
                    case R.id.nav_aboutus:
                                Intent i1=new Intent(HomePage.this,AboutUs.class);
                                startActivity(i1);
                                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                break;


                }


                return true;
            }
        });
    }

  //  @Override
  /*  protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*private void logout(){
        mAuth.signOut();
    }*/
}
