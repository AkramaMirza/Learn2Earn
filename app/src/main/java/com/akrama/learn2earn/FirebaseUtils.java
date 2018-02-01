package com.akrama.learn2earn;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    // TODO: Consider creating a seperate collection mapping email addresses to users if this is
    // called very frequently
    public static Query getUserDocumentReferenceWithEmail(String email) {
        return FirebaseFirestore.getInstance().collection(Constants.COLLECTION_USERS)
                .whereEqualTo(Constants.FIELD_EMAIL, email);
    }

    public static DocumentReference getCurrentUsersActiveBets() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return getUsersActiveBets(uid);
    }

    public static DocumentReference getUsersActiveBets(String userUid) {
        return FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_USERS_TO_BETS)
                .document(userUid);
    }

    public static DocumentReference getCurrentUsersAssignments() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return getUserToAssignments(uid);
    }

    public static DocumentReference getUserToAssignments(String userUid) {
        return FirebaseFirestore.getInstance()
                .collection(Constants.COLLECTION_USERS_TO_ASSIGNMENTS)
                .document(userUid);
    }

    public static DocumentReference getAssignmentWithUid(String uid) {
        return getAssignmentsCollection().document(uid);
    }

    public static CollectionReference getAssignmentsCollection() {
        return FirebaseFirestore.getInstance().collection(Constants.COLLECTION_ASSIGNMENTS);
    }

    public static CollectionReference getBetsCollection() {
        return FirebaseFirestore.getInstance().collection(Constants.COLLECTION_BETS);
    }
}
