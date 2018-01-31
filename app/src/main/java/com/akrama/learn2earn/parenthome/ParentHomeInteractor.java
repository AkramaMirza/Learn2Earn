package com.akrama.learn2earn.parenthome;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.FirebaseUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by akrama on 31/01/18.
 */

public class ParentHomeInteractor {

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

}
