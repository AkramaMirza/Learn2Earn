package com.akrama.learn2earn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is logged in, check if they are student, parent, or teacher
            FirebaseUtility.getUserDocumentReference(user.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String role = task.getResult().getString("role");
                            launchHomeScreen(role);
                        }
                    });

        } else {
            // Show Sign-in/Sign-up screen
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Created account successfully, now show a screen to ask which role they want

            }
        }
    }

    private void launchHomeScreen(String role) {
        switch (role) {
            case Constants.ROLE_TEACHER:
                Toast.makeText(this, "You are a teacher!", Toast.LENGTH_LONG).show();
                break;
            case Constants.ROLE_PARENT:
                Toast.makeText(this, "You are a parent!", Toast.LENGTH_LONG).show();
                break;
            case Constants.ROLE_STUDENT:
            default:
                Toast.makeText(this, "You are a student!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
