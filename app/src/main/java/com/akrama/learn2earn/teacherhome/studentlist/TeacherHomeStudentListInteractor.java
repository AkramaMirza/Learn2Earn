package com.akrama.learn2earn.teacherhome.studentlist;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.FirebaseUtils;
import com.akrama.learn2earn.model.Assignment;
import com.akrama.learn2earn.model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by akrama on 02/02/18.
 */

public class TeacherHomeStudentListInteractor {

    public void requestStudents(Consumer<List<Map>> listener) {
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(teacherDocument -> {
            if (!teacherDocument.contains(Constants.FIELD_STUDENTS)) {
                listener.accept(null);
            } else {
                List<Map> mapList = (List<Map>) teacherDocument.get(Constants.FIELD_STUDENTS);
                listener.accept(mapList);
            }
        });
    }

    public void deleteAssignment(String assignmentUid, List<Student> studentList, Consumer<Boolean> listener) {
        FirebaseUtils.getAssignmentWithUid(assignmentUid).delete();
        String teacherUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        removeFromUserToAssignment(teacherUid, assignmentUid, listener);
        for (Student student : studentList) {
            removeFromUserToAssignment(student.getStudentUid(), assignmentUid, aVoid->{});
        }
    }

    private void removeFromUserToAssignment(String userUid, String assignmentUid, Consumer<Boolean> listener) {
        FirebaseUtils.getUserToAssignments(userUid).get().addOnSuccessListener(userToAssignmentDoc -> {
            List<Map> mapList = (List<Map>) userToAssignmentDoc.get(Constants.FIELD_ASSIGNMENTS);
            List<Assignment> assignmentList = Assignment.fromMapListToAssignmentList(mapList);
            for (Assignment assignment : assignmentList) {
                if (assignment.getUid().equals(assignmentUid)) {
                    assignmentList.remove(assignment);
                    break;
                }
            }
            Map<String, List<Assignment>> data = new HashMap<>();
            data.put(Constants.FIELD_ASSIGNMENTS, assignmentList);
            FirebaseUtils.getUserToAssignments(userUid).set(data, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> listener.accept(true))
                    .addOnFailureListener(e -> listener.accept(false));
        });
    }
}
