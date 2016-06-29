package org.ajcm.tubiblia.dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.ajcm.tubiblia.App;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;
import org.ajcm.tubiblia.utils.UserPreferences;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_TABLE_BOOK = "libro";
    private static final String DATABASE_TABLE_VERSICULE = "versiculo";

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void loadDB() throws Exception {
        dbHelper.copydatabase(context);
        Log.e(TAG, "copyDB: ");
        UserPreferences.putBoolean(context, App.COPY_DB, true);
    }

    public Cursor getAllBooks() {
        open();
        return db.query(DATABASE_TABLE_BOOK, null, null, null, null, null, null);
    }

    public Cursor getCapitulo(int idBook, int idCap) {
        return db.query(true, DATABASE_TABLE_VERSICULE, null,
                Verse.Columns.id_libro.name() + " = " + idBook + " AND " + Verse.Columns.capitulo.name() + " = " + idCap, null, null, null, null, null);
    }

    public long addFav(int libro, int capitulo, int versiculo, String letra) {
        ContentValues values = new ContentValues();
//        values.put(ColussmnVersicule.favorito.name(), num);
        return db.update(DATABASE_TABLE_VERSICULE, values, "", null);
    }

    public Cursor getAllFav(){
        open();
        return db.query(DATABASE_TABLE_VERSICULE, null, Verse.Columns.favorito + " = 1", null, null, null, null);
    }

    public Cursor getBook(long idBook) {
        open();
        Cursor res = db.query(DATABASE_TABLE_BOOK, null, Book.Columns.id_libro.name() + " = " + idBook, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}
