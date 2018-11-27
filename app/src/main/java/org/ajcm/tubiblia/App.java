package org.ajcm.tubiblia;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.ajcm.tubiblia.dataset.DBAdapter;
import org.ajcm.tubiblia.dataset.DBHelper;
import org.ajcm.tubiblia.utils.UserPreferences;

import java.util.concurrent.TimeUnit;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class App extends Application {

    private static final String TAG = "App";
    public static final String COPY_DB = "copyDB";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5411285117883478~1488886946");

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));

        DBAdapter dbAdapter = new DBAdapter(this);
        if (!UserPreferences.getBoolean(this, App.COPY_DB)) {
            try {
                dbAdapter.loadDB();
                dbAdapter.close();
            } catch (Exception e) {
                Log.e(TAG, "onCreate: db copy fail", e);
            }
        }
    }
}
