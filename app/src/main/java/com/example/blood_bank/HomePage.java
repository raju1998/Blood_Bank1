package com.example.blood_bank;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    //FirebaseAuth mAuth;
    FirebaseDatabase database;
    private DatabaseReference myref;
    FirebaseAuth.AuthStateListener mAuthListener;
    RecyclerView recyclerView;
    TextView blood,name,number;
    List<List_data> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView=findViewById(R.id.recyler_view);


        blood=findViewById(R.id.blood_group);
        name=findViewById(R.id.text_name);
        number=findViewById(R.id.mobile_number);
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

                    list_data.set_BloodGroup(blood);
                    list_data.set_name(name);
                    list_data.set_number(number);

                    list.add(list_data);
                }

                RecyclerviewAdapter recyclerviewAdapter=new RecyclerviewAdapter(list);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting){

        }
        if(item.getItemId()==R.id.logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout(){
        mAuth.signOut();
    }*/
}
