package com.greenapex.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rultech on 15/2/16.
 */
public class Utils {

    private Activity activity = null;
    private Context mContext = null;

    public Utils(Activity activity) {
        this.activity = activity;
        mContext = null;
    }

    public Utils(Context mContext) {
        this.mContext = mContext;
        activity = null;
    }
//    public static boolean isConnectingToInternet(Context _context){
//        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null)
//        {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null)
//                for (int i = 0; i < info.length; i++)
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
//                    {
//                        return true;
//                    }
//
//        }
//        return false;
//    }

    public static boolean isNetworkConnected(Context _context) {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static String stringToMd5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void showToast(String msg) {
        if (msg != null && msg.equalsIgnoreCase(""))
            msg = "Oops Some Error has occured";
        if (this.mContext != null)
            Toast.makeText(this.mContext, msg, Toast.LENGTH_SHORT).show();
        if (this.activity != null)
            Toast.makeText(this.activity, msg, Toast.LENGTH_SHORT).show();
    }

}
