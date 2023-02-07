package com.example.javafirebaseauthentication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    //Variables
    private TextView banner;
    private TextView registerUser;
    private EditText editTextFullName,editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    //to check authentication copy data from firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

//firebase authentication
        mAuth = FirebaseAuth.getInstance();

        /**
         * Initialize all the variables found in register.xml
         * the id that we use here is already set id in register.xml
         *
         */
        banner = (TextView) findViewById(R.id.banner);
       // banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);



    }

    @Override
    public void onClick(View v) {
        //using the switch to get the Id of the clicked button and navigating to different screen
        switch (v.getId()){

            case R.id.registerUser:
                registerUser();
                //startActivity(new Intent(this,MainActivity.class));

                //TODO: For progress bar. here is the extra added code where possible
                progressBar.setVisibility(View.GONE);


                break;
        }

    }

    private void registerUser() {

        //validation to avoid user to submit an empty form
        //converting any input to string
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String fullName = editTextFullName.getText().toString();
        String age = editTextAge.getText().toString();


        //in case user submit empty field

        //if user forget full name
        if (fullName.isEmpty()){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }
        //if user forget age
        if (age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }
        //if user forget email
        if (email.isEmpty()){
            editTextEmail.setError("Enter your email");
            editTextEmail.requestFocus();
            return;
        }
        //to check if the email is valid. example abd123@gmail.com
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }
        //check if password is less than six characters
        //firebase does not take password < 6 characters
        if(password.length() < 6){
            editTextPassword.setError("Minimum password should be six characters");
            editTextPassword.requestFocus();
            return;
        }


        //setting the visibility of the progress bar
        progressBar.setVisibility(View.VISIBLE);

        //pass the password and email of the user to be registered
        //object for the firebase auth
        mAuth.createUserWithEmailAndPassword(email, password)
//check if user has been registered

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check if user has been registered successfully
                        if(task.isSuccessful()){
                            //if registration is successfully creating user object
                            User user = new User(fullName, age, email);

                            //send the user object to realtime database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            
                                            //prompt toast if user is registered successfully
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);



                                                //after registering user successfully, user has to login now



                                        }else {
                                                Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });








    }
}