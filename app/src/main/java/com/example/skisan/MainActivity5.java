package com.example.skisan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    RecyclerView mandiview;
    FirebaseStorage storage;
    ArrayList<bhavdata> allprices;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mandiview=findViewById(R.id.bhavview);
        getSupportActionBar().hide();
        storage=FirebaseStorage.getInstance();
        allprices=new ArrayList<>();


            StorageReference ref=storage.getReference().child("Khargone");


            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(@NonNull Uri uri) {
                    String url=uri.toString();
                    bhavdata data=new bhavdata();
                    data.setBhavphotourl(url);
                    String cityname=url.replaceAll(".jpg","");

                    data.setMandiplace(cityname);
                    allprices.add(data);

                }
            });


        mandiview.setAdapter(new bhavadapter(allprices,MainActivity5.this));


    }
}