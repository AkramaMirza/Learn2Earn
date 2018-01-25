package com.akrama.learn2earn;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by akrama on 23/01/18.
 */

public class FirebaseUtils {

    public static DocumentReference getUserDocumentReference(String userUid) {
        return FirebaseFirestore.getInstance().collection(Constants.COLLECTION_USERS).document(userUid);
    }

    public static DocumentReference getCurrentUserDocumentReference() {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return getUserDocumentReference(userUid);
    }
}
