package org.ajcm.tubiblia.dataset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import org.ajcm.tubiblia.utils.UserPreferences;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class DBHelper implements SupportSQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "tu_biblia.db";
    private static final String COPY_DB_PREF = "version_db";
    private static final int DATABASE_VERSION = 2;
    private String DBPath;
    private Context context;

    public DBHelper(Context context) {
        super();
        this.context = context;
        DBPath = "/data/data/" + context.getPackageName() + "/databases/" + DATABASE_NAME;
    }

    private void copydatabase(Context context) throws IOException {
        Log.e(TAG, "copydatabase: init");
        try {
            SupportSQLiteDatabase readableDatabase = getReadableDatabase();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                readableDatabase.close();
            }
        } catch (Exception ignored) {
            Log.e(TAG, "copydatabase: exception", ignored);
        }
        InputStream input = context.getAssets().open(DATABASE_NAME);
        Log.e(TAG, "copydatabase: available " + input.available());
        OutputStream output = new FileOutputStream(DBPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        Log.e(TAG, "copydatabase: success");
        output.flush();
        output.close();
        input.close();
        UserPreferences.putBoolean(context, COPY_DB_PREF, true);
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(DBPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e(TAG, "checkDataBase: false");
            return false;
        }
        if (checkDB != null) {
            checkDB.close();
            Log.e(TAG, "checkDataBase: true");
            return true;
        }
        Log.e(TAG, "checkDataBase: false");
        return false;
    }

    public void loadDB() {
        if (!checkDataBase()) {
            try {
                copydatabase(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {

    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        return getWritableDatabase();
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        return getReadableDatabase();
    }

    @Override
    public void close() {
        close();
    }
}
