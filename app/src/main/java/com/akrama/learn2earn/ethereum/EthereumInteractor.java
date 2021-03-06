package com.akrama.learn2earn.ethereum;

import android.content.Context;
import android.content.res.Resources;

import com.akrama.learn2earn.Constants;
import com.akrama.learn2earn.model.Addresses;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.function.Consumer;

import rx.Observable;

/**
 * Created by akrama on 03/02/18.
 */

public class EthereumInteractor {

    private static final String ETHEREUM_NODE_URL = "https://rinkeby.infura.io/99wXx429tmh3S0dsxwwb";
    private static final String CONTRACT_ADDRESS = "0x3Bc82aa87dE23483B092211C68028AaD4008EE2B";

    private static final EthereumInteractor ourInstance = new EthereumInteractor();

    private Web3j mWeb3j;
    private Context mContext;
    private Credentials mCredentials;
    private Learn2EarnContract mContract;

    public static EthereumInteractor getInstance() {
        return ourInstance;
    }

    private EthereumInteractor() {
    }

    public void init(Context context, String role, Consumer<Boolean> listener) {
        mContext = context;
        mWeb3j = Web3jFactory.build(new HttpService(ETHEREUM_NODE_URL));
        initInBackground(role, listener);
    }

    private void initInBackground(String role, Consumer<Boolean> listener) {
        String walletPath = "";
        switch (role) {
            case Constants.ROLE_STUDENT:
                walletPath = WalletConstants.STUDENT_WALLET;
                break;
            case Constants.ROLE_PARENT:
                walletPath = WalletConstants.PARENT_WALLET;
                break;
            case Constants.ROLE_TEACHER:
                walletPath = WalletConstants.TEACHER_WALLET;
                break;
            default:
                break;
        }
        File credsFile = new File(mContext.getCacheDir(), "temp_wallet_file");
        copyInputStreamToFile(EthereumInteractor.class.getResourceAsStream(walletPath), credsFile);
        new Thread(() -> {
            try {
                mCredentials = WalletUtils.loadCredentials(WalletConstants.PASSWORD, credsFile);
                mContract = Learn2EarnContract.load(CONTRACT_ADDRESS, mWeb3j, mCredentials,
                        Learn2EarnContract.GAS_PRICE, Learn2EarnContract.GAS_LIMIT);
                listener.accept(true);
            } catch (Exception e) {
                e.printStackTrace();
                listener.accept(false);
            }
        }).start();
    }

    public void requestCurrentBalance(Consumer<BigInteger> listener) {
        new Thread(() -> {
            BigInteger balance = null;
            try {
                balance = mWeb3j.ethGetBalance(mCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listener.accept(balance);
        }).start();
    }

    public void createBet(Addresses addresses, String betUid, BigInteger betValue, BigInteger betGrade, Consumer<Boolean> listener) {
        new Thread(() -> {
            try {
                mContract.createBet(betUid, betGrade, addresses.getStudent(), addresses.getParent(),
                        addresses.getTeacher(), betValue).send();
                listener.accept(true);
            } catch (Exception e) {
                e.printStackTrace();
                listener.accept(false);
            }
        }).start();
    }

    public void confirmBet(String betUid, BigInteger betValue, Consumer<Boolean> listener) {
        new Thread(() -> {
            try {
                mContract.confirmBet(betUid, betValue).send();
                listener.accept(true);
            } catch (Exception e) {
                e.printStackTrace();
                listener.accept(false);
            }
        }).start();
    }

    public void endBet(String betUid, BigInteger grade) {
        mContract.endBet(betUid, grade).sendAsync();
    }

    // Copied from https://stackoverflow.com/questions/10854211/android-store-inputstream-in-file
    private void copyInputStreamToFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if ( out != null ) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
