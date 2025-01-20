package com.example.mpark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Make sure this matches your layout file name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignUp = findViewById(R.id.buttonSignUp);

        // Set up the sign up button click listener
        buttonSignUp.setOnClickListener(view -> {
            // Get the input from EditTexts
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // You can add more validation logic here
            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            } else {
                // Proceed with signing up the user
                signUpUser(email, password, name);
            }
        });
    }

    private void signUpUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        Toast.makeText(SignupActivity.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                        createUserInDatabase(name, email);
                        startActivity(new Intent(SignupActivity.this, MapsActivity.class));
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(SignupActivity.this, "Sign up failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUserInDatabase(String name, String email)
    {
        String UID = "";
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
            Log.d(Constants.TAG, UID);
            Log.d(Constants.TAG, "Hello");
            // Rest of your code to use UID
        } else {
            Log.d(Constants.TAG, "currentUser is null");
        }


         //Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Email", email);
        user.put("Name", name);
        Log.d(Constants.TAG, "Hello");

//        Add a new document with a generated ID
        db.collection("users")
                .document(UID) // Use .document() with your custom ID
                .set(user) // Use .set() to add the document with your data
                .addOnSuccessListener(aVoid -> Log.d(Constants.TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(Constants.TAG, "Error writing document", e));
    }
}
