package com.example.skisan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
   
    EditText name,village,city,district,type,number;
    Button submit;
    RadioButton farmer,trader;
    String username;
    String address_user;
    String occupation_user;
    String number_user;
    String type_user;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       

         
        name=findViewById(R.id.username);
        village=findViewById(R.id.uservillage);
        city=findViewById(R.id.usercity);
        district=findViewById(R.id.userdistrict);
        submit=findViewById(R.id.buttonsubmit);
        farmer=findViewById(R.id.radioButtonfarmer);
        trader=findViewById(R.id.radioButtontrader);
        type=findViewById(R.id.usertradertype);
        number=findViewById(R.id.editTextPhone);
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
//        getSupportActionBar().hide();
        address_user=village.getText().toString() + ","+city.getText().toString()+ ","+district.getText().toString();
        username=name.getText().toString();
        number_user=number.getText().toString();
        type_user=type.getText().toString();

        auth=FirebaseAuth.getInstance();
        FirebaseUser authCurrentUser=auth.getCurrentUser();
        if(authCurrentUser!=null)
        {
            Intent i=new Intent(this,MainActivity4.class);
            startActivity(i);
        }


        

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Signing in with GMail");
                progressDialog.show();

                if(username.length()==0||number_user.length()==0||address_user.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Enter all Details", Toast.LENGTH_SHORT).show();
                }

                else {
                    signingoogle();
                }
               

            }
        });

    }
     int RC_SIGN_IN=9001;
    private void signingoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton)view).isChecked();

        switch(view.getId())
        {
            case R.id.radioButtonfarmer:
                if(checked)
                {
                    occupation_user="farmer";
                }
                break;
            case R.id.radioButtontrader:
                if(checked)
                {
                    occupation_user="trader";
                }
                break;
        }
    }
    public void getadddata(String id) {
        progressDialog.setTitle("Verifying Details");


        if(type_user.length()==0)
        {
            type_user="";
        }
        userdata newuser=new userdata();
        newuser.setName(username);
        newuser.setAddress(address_user);
        newuser.setOccupation(occupation_user);
        newuser.setNumber(number_user);
        newuser.setTradertype(type_user);
        newuser.setId(id);
        progressDialog.setTitle("On the Way");



        if(occupation_user=="farmer")
        {
            progressDialog.setTitle("Almost Done");
            database.getReference().child("ALLUSERS").child("FARMERS").child(id).setValue(newuser);
            Toast.makeText(MainActivity.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
        }
        else{

            database.getReference().child("ALLUSERS").child("TRADERS").child(id).setValue(newuser);
            Toast.makeText(MainActivity.this, "Sign in Successfull", Toast.LENGTH_SHORT).show(); }
        progressDialog.hide();

       Intent intent=new Intent(MainActivity.this,MainActivity4.class);
        intent.putExtra("nameofuser",newuser.getName() );
        startActivity(intent);



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user=auth.getCurrentUser();
                            String id=user.getUid();

                            getadddata(id);

                            Toast.makeText(MainActivity.this,"Account Created Successfully",Toast.LENGTH_LONG).show();



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());


                        }
                    }
                });
    }




}