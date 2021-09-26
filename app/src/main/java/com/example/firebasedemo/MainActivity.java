package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText edtname,edtmail,edtphone;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname=findViewById(R.id.getname);
        edtmail=findViewById(R.id.getemail);
        edtphone=findViewById(R.id.getphone);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
    }

    public void adddata(View view) {
        addToFirebase(edtname.getText().toString(),edtmail.getText().toString(),edtphone.getText().toString());
    }

    private void addToFirebase(String name, String mail, String phone) {
        UserInfo userInfo=new UserInfo(name,mail,phone);
        databaseReference.push().setValue(userInfo);
        Toast.makeText(MainActivity.this,"Record Inserted Successfully",Toast.LENGTH_LONG).show();
    }

    public void ViewUser(View view) {
        startActivity(new Intent(MainActivity.this,UserActivity.class));
    }
}