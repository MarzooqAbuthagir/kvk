package com.kvknamakkal;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class App extends Application {
    Utilis utilis;
    public static final String TAG = App.class.getSimpleName();
    private static Context context;
    private static App mInstance;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        utilis = new Utilis(this);
        context = getApplicationContext();
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
