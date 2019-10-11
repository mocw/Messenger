package com.example.messenger;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNick;
    private Button btnUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNick = findViewById(R.id.tvNick);
        tvNick.setGravity(Gravity.CENTER_HORIZONTAL);
        btnUsers = findViewById(R.id.btnUsers);
        btnUsers.setGravity(Gravity.CENTER_HORIZONTAL);

        tvNick.setText(LoginActivity.nick);

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,userListActivity.class);
                startActivity(intent);
            }
        });


    }
}
