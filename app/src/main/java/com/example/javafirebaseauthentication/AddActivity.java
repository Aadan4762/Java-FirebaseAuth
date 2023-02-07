package com.example.javafirebaseauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class AddActivity extends AppCompatActivity {

    //Register edit text and Buttons by Id
    EditText name, course, email, surl;
    Button btnAdd, btnBack, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Register buttons and editText
        name = (EditText) findViewById(R.id.txtName);
        course = (EditText) findViewById(R.id.txtCourse);
        email = (EditText) findViewById(R.id.txtEmail);
        surl = (EditText) findViewById(R.id.txtImageUrl);

        //Creating connection between code and xml files

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnLogout = (Button) findViewById(R.id.btnlogout);

        //creating onClick Listener for both add and delete buttons
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to call the method insertData() created at the bottom of the code
                insertData();
                //to call ClearAll method after insertion. the methode is at the end of the code
                clearAll();


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }

    //method for fetching all the data and setting the data into a map
    //using map to access the values from editText
    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("course", course.getText().toString());
        map.put("email", email.getText().toString());
        map.put("surl", surl.getText().toString());
//pushing the data to the database and showing the user
        FirebaseDatabase.getInstance().getReference().child("students").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddActivity.this, "Error while adding", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    //method for clearing text field after adding
    private void clearAll() {
        name.setText("");
        course.setText("");
        email.setText("");
        surl.setText("");

    }

}
