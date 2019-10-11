package com.example.messenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class userListActivity extends AppCompatActivity {

    private List<String> users;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private ListView lvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        users=new ArrayList<>();
        progressDialog=new ProgressDialog(userListActivity.this);
        progressDialog.setMessage("Wczytywanie");
        progressDialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext()) {
                    DataSnapshot item = items.next();
                    String currLogin = item.child("nick").getValue().toString().trim();
                    if(!currLogin.equals(LoginActivity.nick))
                    {
                        users.add(currLogin);
                    }
                }
                progressDialog.dismiss();
                showList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showList() {
        lvUserList =findViewById(R.id.lvUsers);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,users);
        lvUserList.setAdapter(adapter);

        lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(userListActivity.this,chatActivity.class);
                String user = adapter.getItem(position);
                i.putExtra("User",user);
                startActivity(i);
            }
        });
    }
}
