package org.ajcm.tubiblia.dataset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tu_biblia.db";
    private static final int DATABASE_VERSION = 2;
    private String DBPath;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DBPath = "/data/data/" + context.getPackageName() + "/databases/"+DATABASE_NAME;
    }

    public void copydatabase(Context context) throws IOException {
        this.getReadableDatabase();
        InputStream input = context.getAssets().open(DATABASE_NAME);
        OutputStream output = new FileOutputStream(DBPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB;
        try {
            checkDB = SQLiteDatabase.openDatabase(DBPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            return false;
        }
        if (checkDB != null) {
            checkDB.close();
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
