package com.akrama.learn2earn;

import android.content.Context;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * Created by akrama on 03/02/18.
 */

public class EthereumInteractor {

    private static final String ETHEREUM_NODE_URL = "https://rinkeby.infura.io/99wXx429tmh3S0dsxwwb";
    private static final String CONTRACT_ADDRESS = "0x113913679A7acC9981F7D1313CbEb9e43dB22A7F";

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
                mContract = Learn2EarnContract.load(CONTRACT_ADDRESS, mWeb3j, mCredentials, Learn2EarnContract.GAS_PRICE, Learn2EarnContract.GAS_LIMIT);
                listener.accept(true);
            } catch (Exception e) {
                e.printStackTrace();
                listener.accept(false);
            }
        }).start();
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
