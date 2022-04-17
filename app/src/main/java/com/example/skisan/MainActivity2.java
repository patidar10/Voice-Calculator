package com.example.skisan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    EditText name,number,type;
    TextView signout,tnc,pp,about;
    Button edit;
    String username,usernumber,usertype;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        name=findViewById(R.id.username1);
        number=findViewById(R.id.usernumber2);
        type=findViewById(R.id.typeuser1);
        edit=findViewById(R.id.button);
        signout=findViewById(R.id.textView13);

        FirebaseUser curruser=mAuth.getCurrentUser();
        String id=curruser.getUid();
        database.getReference().child("ALLUSERS").child("TRADERS").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               userdata curr =dataSnapshot.getValue(userdata.class);

               if(curr!=null) {
                   username = curr.getName();
                   usernumber = curr.getNumber();
                   usertype = curr.getTradertype();

                   name.setText(username);
                   number.setText(usernumber);
                   type.setText(usertype);
               }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userdata newdetails=new userdata();
                newdetails.setName(name.getText().toString());
                newdetails.setNumber(number.getText().toString());
                newdetails.setTradertype(type.getText().toString());

                if(newdetails.getOccupation()=="trader") {
                    database.getReference().child("ALLUSERS").child("TRADER").child(id).setValue(newdetails);
                }
                else
                {
                    database.getReference().child("ALLUSERS").child("FARMER").child(id).setValue(newdetails);
                }
                Toast.makeText(MainActivity2.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });




    }



}