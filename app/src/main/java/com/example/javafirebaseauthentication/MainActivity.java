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

    @Override
    public void onClick(View v) {
        /**
         * Switch for all buttons
         * using switch to move from one page to another
         */
        //navigating to register page from the main page
        switch (v.getId()) {
            //case for our register button after we've set onClick listener for it
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            //case for our signIn Button after setting onClick Listener for it. for navigation purpose
            case R.id.signIn:
                userLogin();
                break;

            //Redirecting the user to reset password by redirecting user to ForgotPassword activity
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class ));
                break;
        }

    }

    //method for the signIn
    private void userLogin() {
        //for validation convert user credentials to String
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


/**
 * Validation of user credentials after converting them to String
 */
        //Email validation
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;

        }


        //Checking if the Email matches with the one already registered
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;

        }

        //Password validation
        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        //checking the length of the password
        if (password.length() < 6){
            editTextPassword.setError("Minimum length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        //Keep the progressBar spinning until the User Logs in
        progressBar.setVisibility(View.VISIBLE);


        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if user has Logged in Successfully then redirect user to Profile activity page
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, StudentActivity.class));


                    //user verification or email address verification
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    if (user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        // Toast.makeText(MainActivity.this, "Log in Successful", Toast.LENGTH_LONG).show();


                    }

                    else{
                        //if user not verified need to send verification link
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();


                    }





                    //else if user login is not successful
                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();

                }


            }
        });




    }
}
