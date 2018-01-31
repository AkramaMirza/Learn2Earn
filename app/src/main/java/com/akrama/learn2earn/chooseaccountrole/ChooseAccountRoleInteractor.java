package com.akrama.learn2earn.chooseaccountrole;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.FirebaseUtils;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akrama on 24/01/18.
 */

public class ChooseAccountRoleInteractor {

    private ChooseAccountRolePresenter mPresenter;

    public ChooseAccountRoleInteractor(ChooseAccountRolePresenter presenter) {
        mPresenter = presenter;
    }

    public void setRole(String role) {
        Map<String, String> data = new HashMap<>();
        data.put(Constants.FIELD_ROLE, role);
        FirebaseUtils.getCurrentUserDocumentReference().set(data, SetOptions.merge());
    }
}
