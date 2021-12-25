package com.kvknamakkal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilis {
    @SuppressLint("StaticFieldLeak")
    static Context con;
    // Custom Progress Dialog
    public static ProgressDialog mProgressDialog;

    public static String Api = "https://kvknamakkal.com/";

    public static String appversion = "version.php";

    public static String shareUrl = "https://kvknamakkal.com/app/update.php";

    public Utilis(Context con) {
        Utilis.con = con;
    }

    public static void showProgress(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.progresstitle));
        mProgressDialog.show();
    }

    public static void dismissProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInternetOn() {
        ConnectivityManager conMgr = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isGpsOn() {
        LocationManager manager = (LocationManager) con
                .getSystemService(Context.LOCATION_SERVICE);
        return !(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }
}
