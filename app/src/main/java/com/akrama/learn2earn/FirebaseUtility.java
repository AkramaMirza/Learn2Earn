package com.akrama.learn2earn;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by akrama on 23/01/18.
 */

public class FirebaseUtility {

    public static DocumentReference getUserDocumentReference(String userUuid) {
        return FirebaseFirestore.getInstance().collection(Constants.COLLECTION_USERS).document(userUuid);
    }
}
