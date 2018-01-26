package com.akrama.learn2earn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1;
    private static final int RC_CHOOSE_ROLE = 2;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is logged in
            FirebaseUtils.getUserDocumentReference(user.getUid()).get()
                    .addOnSuccessListener(this::onUserAuthenticated)
                    .addOnFailureListener(e -> {
                        // TODO: Handle error
                        Log.e(TAG, e.toString());
                        finish();
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
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    // Signed in successfully, now show a screen to ask which role they want
                    // if it is a new account
                    FirebaseUtils.getCurrentUserDocumentReference().get()
                            .addOnSuccessListener(this::onUserAuthenticated);
                } else {
                    // TODO: Restart MainActivity
                    Toast.makeText(this, R.string.auth_fail, Toast.LENGTH_LONG).show();
                }
                break;

            case RC_CHOOSE_ROLE:
                FirebaseUtils.getCurrentUserDocumentReference().get()
                        .addOnSuccessListener(documentSnapshot ->
                                launchHomeScreen(documentSnapshot.getString(Constants.FIELD_ROLE)));
                break;
            default: break;
        }
    }

    private void onUserAuthenticated(DocumentSnapshot documentSnapshot) {
        if (!documentSnapshot.exists() || !documentSnapshot.contains(Constants.FIELD_ROLE)) {
            launchChooseRoleScreen();
        } else {
            launchHomeScreen(documentSnapshot.getString(Constants.FIELD_ROLE));
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
        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchChooseRoleScreen() {
        Intent intent = new Intent(this, ChooseAccountRoleActivity.class);
        startActivityForResult(intent, RC_CHOOSE_ROLE);
    }
}
