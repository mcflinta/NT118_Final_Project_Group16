package com.myapp.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.weather_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText EDname;
    EditText EDEmail;
    EditText EDPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EDEmail = findViewById(R.id.ETEmail);
        EDname = findViewById(R.id.ETName);
        EDPassword = findViewById(R.id.ETPassword);
        btnRegister = findViewById(R.id.BTCreateAccount);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, name;
                email = String.valueOf(EDEmail.getText());
                password = String.valueOf(EDPassword.getText());
                name = String.valueOf(EDname.getText());
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("MyTag", "createUserWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user != null)
                                    {
                                        String userUID = user.getUid();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://weatherapp-c3951-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                        DatabaseReference databaseReference = database.getReference("users");
                                        databaseReference.child("userUID").child(userUID).setValue(true);
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("MyTag", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

    }

}