package com.example.skisan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    FirebaseDatabase database;
    TextView currusername;
    FirebaseAuth auth;
    TextView bhav;
    ImageView lout,sett;
    RecyclerView tradervieww;
    ArrayList<userdata> alltraders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        database=FirebaseDatabase.getInstance();
        currusername=findViewById(R.id.textView2);
        bhav=findViewById(R.id.textView4);
        auth=FirebaseAuth.getInstance();
        lout=findViewById(R.id.signout);
        sett=findViewById(R.id.settings);
        tradervieww=findViewById(R.id.traderview);


        tradervieww.setLayoutManager(new LinearLayoutManager(this));
        alltraders=new ArrayList<>();


        Intent intent=getIntent();
        String name=intent.getStringExtra("nameofuser");
        currusername.setText(name);

        database.getReference().child("ALLUSERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot i:dataSnapshot.getChildren())
                {
                    userdata trader = i.getValue(userdata.class);

                    if(trader.getOccupation()=="trader"){
                    alltraders.add(trader);}
                }
                tradervieww.setAdapter(new traderadapter(alltraders,MainActivity4.this));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        bhav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity4.this,MainActivity5.class);
                startActivity(intent1);
                
            }
        });

       sett.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent1=new Intent(MainActivity4.this,MainActivity2.class);
               startActivity(intent1);

           }
       });
       lout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog();

           }
       });
    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity4.this);
        dialog.setMessage("Do you want to Signout");
        dialog.setTitle("Confirm");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        auth.signOut();
                        Intent intent=new Intent(MainActivity4.this,MainActivity.class);
                        Toast.makeText(getApplicationContext(),"Signed Out Successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
        dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}