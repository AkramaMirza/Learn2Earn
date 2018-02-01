package com.akrama.learn2earn.teacherhome;

import com.akrama.learn2earn.model.Assignment;
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
 * Created by akrama on 31/01/18.
 */

public class TeacherHomeInteractor {

    public void requestAssignments(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUsersAssignments().addSnapshotListener((documentSnapshot, e) -> {
            if (!documentSnapshot.exists() || !documentSnapshot.contains(Constants.FIELD_ASSIGNMENTS)) {
                listener.accept(null);
            } else {
                List<Map> assignments = (List<Map>) documentSnapshot.get(Constants.FIELD_ASSIGNMENTS);
                listener.accept(assignments);
            }
        });
    }

    public void requestStudents(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.contains(Constants.FIELD_STUDENTS)) {
                listener.accept(null);
            } else {
                List<Map> students = (List<Map>) documentSnapshot.get(Constants.FIELD_STUDENTS);
                listener.accept(students);
            }
        });
    }

    public void createAssignment(String assignmentName, Consumer<Boolean> listener) {
        String teacherUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> data = new HashMap<>();
        data.put(Constants.FIELD_ASSIGNMENT_NAME, assignmentName);
        data.put(Constants.FIELD_TEACHER_UID, teacherUid);
        FirebaseUtils.getAssignmentsCollection().add(data)
                .addOnSuccessListener(assignmentDocument -> {
                    listener.accept(true);
                    Assignment assignment = new Assignment(assignmentName, assignmentDocument.getId());
                    addAssignmentToUser(teacherUid, assignment);
                    requestStudents(students -> {
                        List<Student> studentList = Student.fromMapListToStudentList(students);
                        for (Student student : studentList) {
                            addAssignmentToUser(student.getStudentUid(), assignment);
                        }
                    });
                })
                .addOnFailureListener(e -> listener.accept(false));
    }

    // TODO: Move to cloud functions
    private void addAssignmentToUser(String userUid, Assignment assignment) {
        FirebaseUtils.getUserToAssignments(userUid).get().addOnSuccessListener(userToAssignmentsDoc -> {
            List<Assignment> assignments;
            if (!userToAssignmentsDoc.exists() || !userToAssignmentsDoc.contains(Constants.FIELD_ASSIGNMENTS)) {
                assignments = new ArrayList<>();
            } else {
                List<Map> mapList = (List<Map>) userToAssignmentsDoc.get(Constants.FIELD_ASSIGNMENTS);
                assignments = Assignment.fromMapListToAssignmentList(mapList);
            }
            assignments.add(assignment);
            Map<String, List<Assignment>> data = new HashMap<>();
            data.put(Constants.FIELD_ASSIGNMENTS, assignments);
            FirebaseUtils.getUserToAssignments(userUid).set(data, SetOptions.merge());
        });
    }
}
