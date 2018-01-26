package com.akrama.learn2earn;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by akrama on 25/01/18.
 */

public class StudentHomeInteractor {

    public void addParentWithEmail(String email, Consumer<Boolean> listener) {
        FirebaseUtils.getUserDocumentReferenceWithEmail(email).addSnapshotListener((documentSnapshots, e) -> {
            if (documentSnapshots.isEmpty()) {
                listener.accept(false);
            } else {
                String parentUid = documentSnapshots.getDocuments().get(0).getId();
                setParentUid(parentUid, listener);
                setParentsChildUid(parentUid);
            }
        });
    }

    private void setParentUid(String uid, Consumer<Boolean> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(Constants.FIELD_PARENT_UID, uid);
        FirebaseUtils.getCurrentUserDocumentReference()
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> listener.accept(true))
                .addOnFailureListener(e -> listener.accept(false));
    }

    // TODO: Move to cloud function
    private void setParentsChildUid(String parentUid) {
        String childUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> data = new HashMap<>();
        data.put(Constants.FIELD_CHILD_UID, childUid);
        FirebaseUtils.getUserDocumentReference(parentUid)
                .set(data, SetOptions.merge());
    }

    public void requestActiveBets(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUsersActiveBets().get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists() || !documentSnapshot.contains(Constants.FIELD_ACTIVE_BETS)) {
                listener.accept(null);
            } else {
                List<Map> activeBets = (List<Map>) documentSnapshot.get(Constants.FIELD_ACTIVE_BETS);
                listener.accept(activeBets);
            }
        });
    }

    public void requestParentStatus(Consumer<Boolean> listener) {
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.contains(Constants.FIELD_PARENT_UID)
                    || TextUtils.isEmpty(documentSnapshot.getString(Constants.FIELD_PARENT_UID))) {
                listener.accept(false);
            } else {
                listener.accept(true);
            }
        });
    }

    public void requestClassroomStatus(Consumer<Boolean> listener) {
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.contains(Constants.FIELD_CLASSROOM_UID)
                    || TextUtils.isEmpty(documentSnapshot.getString(Constants.FIELD_CLASSROOM_UID))) {
                listener.accept(false);
            } else {
                listener.accept(true);
            }
        });
    }
}
