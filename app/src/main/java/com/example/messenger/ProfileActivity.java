package com.example.messenger;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNick;
    private Button btnUsers;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNick = findViewById(R.id.tvNick);
        tvNick.setGravity(Gravity.CENTER_HORIZONTAL);
        btnUsers = findViewById(R.id.btnUsers);
        btnUsers.setGravity(Gravity.CENTER_HORIZONTAL);

        tvNick.setText(LoginActivity.getNick());



        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,userListActivity.class);
                startActivity(intent);
            }
        });


    }
}
