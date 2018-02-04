package com.akrama.learn2earn.parenthome;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.FirebaseUtils;
import com.akrama.learn2earn.model.CompressedBet;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
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

    public void updateBet(CompressedBet bet, Consumer<Boolean> listener) {
        updateBetsCollection(bet);
        FirebaseUtils.getCurrentUserDocumentReference().get().addOnSuccessListener(parentDocument -> {
            String parentUid = parentDocument.getId();
            String childUid = parentDocument.getString(Constants.FIELD_CHILD_UID);
            updateUserToBetsCollection(parentUid, bet, listener);
            updateUserToBetsCollection(childUid, bet, aBoolean -> {});
        });
    }

    private void updateBetsCollection(CompressedBet bet) {
        Map<String, Boolean> data = new HashMap<>();
        data.put(Constants.FIELD_BET_CONFIRMED, bet.isConfirmed());
        FirebaseUtils.getBetsCollection().document(bet.getBetUid()).set(data, SetOptions.merge());
    }

    private void updateUserToBetsCollection(String userUid, CompressedBet bet, Consumer<Boolean> listener) {
        FirebaseUtils.getUsersActiveBets(userUid).get().addOnSuccessListener(documentSnapshot -> {
            List<Map> mapList = (List<Map>) documentSnapshot.get(Constants.FIELD_ACTIVE_BETS);
            List<CompressedBet> compressedBets = CompressedBet.fromMapListToCompressedBetList(mapList);
            for (CompressedBet compressedBet : compressedBets) {
                if (compressedBet.getBetUid().equals(bet.getBetUid())) {
                    compressedBet.setConfirmed(bet.isConfirmed());
                }
            }
            Map<String, List<CompressedBet>> data = new HashMap<>();
            data.put(Constants.FIELD_ACTIVE_BETS, compressedBets);
            FirebaseUtils.getUsersActiveBets(userUid).set(data, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> listener.accept(true));
        });
    }
}
