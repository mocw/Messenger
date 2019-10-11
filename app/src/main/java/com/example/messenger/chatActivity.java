package com.example.messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class chatActivity extends AppCompatActivity {

    private String user;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i=getIntent();
        user=i.getStringExtra("User");

        progressDialog=new ProgressDialog(chatActivity.this);
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext()) {
                    DataSnapshot item = items.next();
                    String currLogin = item.child("nick").getValue().toString().trim();
                    if(currLogin.equals(user))
                    {
                        UID=item.getKey();
                        break;
                    }
                }
                progressDialog.dismiss();
            showData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void showData() {

    }
}
