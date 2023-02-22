package com.example.javafirebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //for register button
    private TextView register, forgotPassword;

    //for the user to login after registering an account
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private ProgressBar progressBar;

    //creating firebase variable
    private FirebaseAuth myAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize register button
        register = (TextView) findViewById(R.id.register);
        //set onClick Listener on the Register Button
        register.setOnClickListener(this);


        //initialize signIn button after user has already created/registered an account
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        //initialize edit text email and password
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        //initialize progressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //initialize myAuth
        myAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);


    }

    
