package com.akrama.learn2earn.studenthome;

import android.text.TextUtils;

import com.akrama.learn2earn.model.Bet;
import com.akrama.learn2earn.model.CompressedBet;
import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.FirebaseUtils;
import com.akrama.learn2earn.model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by akrama on 25/01/18.
 */

public class StudentHomeInteractor {

    public void addParentWithEmail(String email, Consumer<Boolean> listener) {
        FirebaseUtils.getUserDocumentReferenceWithEmail(email).get().addOnSuccessListener(documentSnapshots -> {
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

    public void addTeacherWithEmail(String email, Consumer<Boolean> listener) {
        FirebaseUtils.getUserDocumentReferenceWithEmail(email).get().addOnSuccessListener(documentSnapshots -> {
            if (documentSnapshots.isEmpty()) {
                listener.accept(false);
            } else {
                String teacherUid = documentSnapshots.getDocuments().get(0).getId();
                setTeacherUid(teacherUid, listener);
                setTeachersStudentUid(teacherUid);
            }
        });
    }

    private void setTeacherUid(String teacherUid, Consumer<Boolean> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(Constants.FIELD_TEACHER_UID, teacherUid);
        FirebaseUtils.getCurrentUserDocumentReference()
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> listener.accept(true));
    }

    // TODO: Move to cloud function
    private void setTeachersStudentUid(String teacherUid) {
        FirebaseUtils.getUserDocumentReference(teacherUid).get().addOnSuccessListener(teacherDocument -> {
            List students;
            if (!teacherDocument.contains(Constants.FIELD_STUDENTS)) {
               students = new ArrayList<>();
            } else {
                students = (List) teacherDocument.get(Constants.FIELD_STUDENTS);
            }
            String studentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String studentName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            students.add(new Student(studentName, studentUid)); // TODO: Only add to the list if the student is not already added (don't allow duplicates)
            Map<String, List<String>> data = new HashMap<>();
            data.put(Constants.FIELD_STUDENTS, students);
            FirebaseUtils.getUserDocumentReference(teacherUid).set(data, SetOptions.merge());
        });
    }

    // TODO: Move to cloud function
    public void createBet(String assignmentUid, String value, String grade, Consumer<Boolean> listener) {
        final String studentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(currentUserDocument -> {
            final String parentUid = currentUserDocument.getString(Constants.FIELD_PARENT_UID);

            FirebaseUtils.getAssignmentWithUid(assignmentUid).get().addOnSuccessListener(assignmentDocument -> {
                final String teacherUid = assignmentDocument.getString(Constants.FIELD_TEACHER_UID);
                final String assignmentName = assignmentDocument.getString(Constants.FIELD_ASSIGNMENT_NAME);
                Bet bet = new Bet(studentUid, parentUid, teacherUid, assignmentUid, value, grade);

                FirebaseUtils.getBetsCollection().add(bet).addOnSuccessListener(betDocument -> {
                    String betUid = betDocument.getId();
                    CompressedBet compressedBet = new CompressedBet(assignmentName, betUid, value, grade);
                    addBetToUser(studentUid, compressedBet, listener);
                    addBetToUser(parentUid, compressedBet, aVoid -> {});
                });
            });
        });
    }

    private void addBetToUser(String userUid, CompressedBet compressedBet, Consumer<Boolean> listener) {
        FirebaseUtils.getUsersActiveBets(userUid).get().addOnSuccessListener(documentSnapshot -> {
            List activeBets;
            if (!documentSnapshot.exists() || !documentSnapshot.contains(Constants.FIELD_ACTIVE_BETS)) {
                activeBets = new ArrayList<>();
            } else {
                activeBets = (List) documentSnapshot.get(Constants.FIELD_ACTIVE_BETS);
            }
            activeBets.add(compressedBet);
            Map<String, List<Map>> data = new HashMap<>();
            data.put(Constants.FIELD_ACTIVE_BETS, activeBets);
            FirebaseUtils.getUsersActiveBets(userUid)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> listener.accept(true));
        });
    }

    public void requestActiveBets(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUsersActiveBets().addSnapshotListener((documentSnapshot, e) -> {
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

    public void requestAssignments(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUsersAssignments().get().addOnSuccessListener(documentSnapshot -> {
           if (!documentSnapshot.exists() || !documentSnapshot.contains(Constants.FIELD_ASSIGNMENTS)) {
               listener.accept(null);
           } else {
               List<Map> assignments = (List<Map>) documentSnapshot.get(Constants.FIELD_ASSIGNMENTS);
               listener.accept(assignments);
           }
        });
    }

    public void requestTeacherStatus(Consumer<Boolean> listener) {
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.contains(Constants.FIELD_TEACHER_UID)
                    || TextUtils.isEmpty(documentSnapshot.getString(Constants.FIELD_TEACHER_UID))) {
                listener.accept(false);
            } else {
                listener.accept(true);
            }
        });
    }
}
