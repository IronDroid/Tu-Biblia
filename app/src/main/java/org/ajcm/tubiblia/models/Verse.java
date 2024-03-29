package org.ajcm.tubiblia.models;

import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by jhonlimaster on 09-06-16.
 */
@Entity(tableName = "verse_table")
public class Verse {

    @PrimaryKey
    @ColumnInfo
    private int _id;
    @ColumnInfo(name = "id_book")
    private int idBook;
    private int chapter;
    private int verse;
    @ColumnInfo(name = "text_verse")
    private String text;
    @ColumnInfo(name = "favorite")
    private boolean fav;
    @ColumnInfo(name = "text_note")
    private String textNote;

    public enum Columns {
        _id,
        id_book,
        chapter,
        verse,
        text_verse,
        favorite,
        text_note
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public static Verse fromCursor(Cursor cursor) {
        Verse verse = new Verse();
        verse.set_id(cursor.getInt(Columns._id.ordinal()));
        verse.setIdBook(cursor.getInt(Columns.id_book.ordinal()));
        verse.setChapter(cursor.getInt(Columns.chapter.ordinal()));
        verse.setVerse(cursor.getInt(Columns.verse.ordinal()));
        verse.setText(cursor.getString(Columns.text_verse.ordinal()));
        verse.setFav(cursor.getInt(Columns.favorite.ordinal()) == 1);
        verse.setTextNote(cursor.getString(Columns.text_note.ordinal()));
        return verse;
    }
}
