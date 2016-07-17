package org.ajcm.tubiblia.dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.ajcm.tubiblia.App;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.DividerBook;
import org.ajcm.tubiblia.models.Verse;
import org.ajcm.tubiblia.utils.UserPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_TABLE_BOOK = "book_table";
    private static final String DATABASE_TABLE_VERSE = "verse_table";
    private static final String DATABASE_TABLE_DIVIDER = "divider_table";

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

    public ArrayList<Book> getAllBooks() {
        open();
        Cursor res = db.query(DATABASE_TABLE_BOOK, null, null, null, null, null, null);
        ArrayList<Book> books = new ArrayList<>();
        while (res.moveToNext()) {
            books.add(Book.fromCursor(res));
        }
        res.close();
        return books;
    }

    public Cursor getCapitulo(int idBook, int idCap) {
        return db.query(true, DATABASE_TABLE_VERSE, null,
                Verse.Columns.id_book.name() + " = " + idBook + " AND " + Verse.Columns.chapter.name() + " = " + idCap, null, null, null, null, null);
    }

    public long addFav(int idBook, int chapter, int verse, boolean favState) {
        ContentValues values = new ContentValues();
        values.put(Verse.Columns.favorite.name(), favState);
        open();
        return db.update(DATABASE_TABLE_VERSE, values,
                Verse.Columns.id_book.name() + " = " + idBook + " AND " +
                        Verse.Columns.chapter.name() + " = " + chapter + " AND " +
                        Verse.Columns.verse.name() + " = " + verse, null);
    }

    public long addNote(int idVerse, String note) {
        ContentValues values = new ContentValues();
        values.put(Verse.Columns.text_note.name(), note);
        open();
        return db.update(DATABASE_TABLE_VERSE, values, Verse.Columns._id.name() + " = " + idVerse, null);
    }

    public long addNote(int idBook, int chapter, int verse, String note) {
        ContentValues values = new ContentValues();
        values.put(Verse.Columns.text_note.name(), note);
        open();
        return db.update(DATABASE_TABLE_VERSE, values,
                Verse.Columns.id_book.name() + " = " + idBook + " AND " +
                        Verse.Columns.chapter.name() + " = " + chapter + " AND " +
                        Verse.Columns.verse.name() + " = " + verse, null);
    }

    public long deleteNote(int idVerse) {
        ContentValues values = new ContentValues();
        values.put(Verse.Columns.text_note.name(), "");
        open();
        return db.update(DATABASE_TABLE_VERSE, values, Verse.Columns._id.name() + " = " + idVerse, null);
    }

    public ArrayList<Verse> getAllFav() {
        open();
        Cursor res = db.query(DATABASE_TABLE_VERSE, null, Verse.Columns.favorite + " = 1", null, null, null, null);
        ArrayList<Verse> verses = new ArrayList<>();
        while (res.moveToNext()) {
            verses.add(Verse.fromCursor(res));
        }
        res.close();
        return verses;
    }

    public ArrayList<Verse> getAllNotes() {
        open();
        Cursor res = db.query(DATABASE_TABLE_VERSE, null, Verse.Columns.text_note + " != ''", null, null, null, null);
        ArrayList<Verse> verses = new ArrayList<>();
        while (res.moveToNext()) {
            verses.add(Verse.fromCursor(res));
        }
        res.close();
        return verses;
    }

    public Book getBook(long idBook) {
        open();
        Cursor res = db.query(DATABASE_TABLE_BOOK, null, Book.Columns._id.name() + " = " + idBook, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
            return Book.fromCursor(res);
        }
        return null;
    }

    public DividerBook getDivider(long idBook) {
        open();
        Cursor res = db.query(DATABASE_TABLE_DIVIDER, null, DividerBook.Columns._id.name() + " = " + idBook, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
            res.close();
        }
        return DividerBook.fromCursor(res);
    }

    public ArrayList<DividerBook> getAllDivider() {
        open();
        Cursor res = db.query(DATABASE_TABLE_DIVIDER, null, null, null, null, null, null);
        ArrayList<DividerBook> dividerBooks = new ArrayList<>();
        while (res.moveToNext()) {
            dividerBooks.add(DividerBook.fromCursor(res));
        }
        res.close();
        return dividerBooks;
    }

    public ArrayList<Verse> getSearch(String text, boolean swFilter) {
        open();
        Cursor res = db.query(DATABASE_TABLE_VERSE, null, Verse.Columns.text_verse + " like '%" + text + "%'", null, null, null, null);
        ArrayList<Verse> verses = new ArrayList<>();
        while (res.moveToNext()) {
            Verse verse = Verse.fromCursor(res);
            if (swFilter){
                String[] split = verse.getText().split(" ");
                for (String sp : split) {
                    if (sp.length() == text.length() && sp.contains(text)) {
                        verses.add(verse);
                    }
                }
            } else {
                verses.add(verse);
            }
        }
        res.close();
        return verses;
    }

    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }
}
