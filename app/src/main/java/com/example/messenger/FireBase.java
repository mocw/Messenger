package com.example.messenger;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {
    private static FireBase fireBase=null;
    //private static DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;
    private FireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    public static FireBase getInstance(){
        if(fireBase==null){
            fireBase=new FireBase();
        }
        return fireBase;
    }

    public static DatabaseReference dbReference(){
        FireBase.getInstance();
        return firebaseDatabase.getReference();
    }
}
