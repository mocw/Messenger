package com.example.messenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import android.os.Handler;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText etLogin;
    private EditText etPassword;
    private Button userLogin;
    private Button userSignUp;
    private String textLogin;
    private String textPassword;
    private static String UID;
    private static String nick;
    private  AlertDialog.Builder builder;
    private  DatabaseReference myRef = FireBase.dbReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        progressBar = findViewById(R.id.progressBar2);
        etLogin = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etUserPass);
        userLogin = findViewById(R.id.btnUserLogin);
        userSignUp=findViewById(R.id.buttonUserSignIp);


        etLogin.setText("radek"); // PAMIETAC O USUNIECIU!
        etPassword.setText("radzio");

        builder=new AlertDialog.Builder(LoginActivity.this);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                if(checkLoginData())
                {
                    logging();
                }
            }
        });

        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                if (checkLoginData()) {
                    myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean exists=false;
                            Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                            while(items.hasNext()) {
                                DataSnapshot item = items.next();
                                String currLogin = item.child("nick").getValue().toString().trim();
                                if(textLogin.equals(currLogin)) {
                                    Toast.makeText(LoginActivity.this, "Użytkownik o " +
                                                    "podanym loginie już istnieje!",
                                            Toast.LENGTH_SHORT).show();
                                    exists=true;
                                    break;
                                }
                            }
                            if(exists) return;
                            else  addUser();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void addUser() {

            User u=new User(textLogin,textPassword,"ONLINE");
            myRef.child("Users").push().setValue(u);
            Toast.makeText(LoginActivity.this, "Zarejestrowano!",
                    Toast.LENGTH_SHORT).show();
            logging();

    }

    private void logging()
    {
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.setMessage("Czas minął!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        },10000);

        progressBar.setVisibility(View.VISIBLE);
        myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext()) {
                    DataSnapshot item = items.next();
                    String currLogin = item.child("nick").getValue().toString().trim();
                    String currPaswd = item.child("password").getValue().toString().trim();
                    if(textLogin.equals(currLogin) && textPassword.equals(currPaswd)) {
                        progressBar.setVisibility(View.GONE);
                        UID=item.getKey(); //<--Klucz zalogowanego uzytkownika
                        nick=currLogin;
                        h.removeCallbacksAndMessages(null);
                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        return;
                    }
                }
                h.removeCallbacksAndMessages(null);
                Toast.makeText(LoginActivity.this, "Nieprawidłowy login/hasło!",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setData()
    {

        textLogin = etLogin.getText().toString();
        textPassword = etPassword.getText().toString();
    }

    private boolean checkLoginData()
    {
        if (textLogin.matches("") || textPassword.matches("")) {
            Toast.makeText(LoginActivity.this, "Brak loginu/hasła!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    public static String getUID() {
        return UID;
    }

    public static String getNick() {
        return nick;
    }
}
