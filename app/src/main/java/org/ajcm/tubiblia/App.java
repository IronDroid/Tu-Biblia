package org.ajcm.tubiblia;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.ajcm.tubiblia.dataset.AppDatabase;
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

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5411285117883478~1488886946");

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));

        DBHelper databaseHelper = new DBHelper(getApplicationContext());
        databaseHelper.loadDB();

        appExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, appExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
