package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    userAdapter userAdap;
    List<UserInfo> userInfos;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
        userInfos=new ArrayList<>();
        database= FirebaseDatabase.getInstance().getReference("User");


        getUsers();
    }

    private void getUsers() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                    userInfos.add(userInfo);
                }
                userAdap=new userAdapter(UserActivity.this,userInfos,database);
                recyclerView.setAdapter(userAdap);
                userAdap.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}