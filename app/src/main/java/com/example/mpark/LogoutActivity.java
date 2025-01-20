package com.example.mpark;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;


public class LogoutActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
//    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//

//        if (user != null) {
//            // User is signed in, retrieve user's name from Firebase Database
//            DatabaseReference userRef = mDatabase.getReference("users").child(user.getUid());
//            userRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get user's name from dataSnapshot
//                    String username = dataSnapshot.child("username").getValue(String.class);
//
//                    // Update UI to display user's name in the side panel
//                    TextView welcomeTextView = findViewById(R.id.welcome_text_view);
//                    welcomeTextView.setText("Welcome, " + username);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Handle errors
//                }
//            });
//        }


        // Log Out Button Click Listener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
                finish();
            }
        });

        Button btnGoBack = findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Delete Account Button Click Listener
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete account functionality here
                // Example: Show confirmation dialog and delete account if confirmed
                FirebaseUser user = mAuth.getCurrentUser();

                // Delete the user's account
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Account deletion successful
                                        // Redirect to the login screen
                                        startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
                                        finish();
                                        Toast.makeText(LogoutActivity.this, "Account successfully deleted", Toast.LENGTH_SHORT).show();

                                    } else {
                                        // Account deletion failed
                                        // Handle the failure (e.g., display an error message)
                                        Toast.makeText(LogoutActivity.this, "Unable to delete account", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }
}