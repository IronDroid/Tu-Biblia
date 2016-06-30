package org.ajcm.tubiblia.models;

import android.database.Cursor;

/**
 * Created by jhonlimaster on 29-06-16.
 */
public class Note {
    private int _id;
    private int idVerse;
    private String textNote;

    public enum Columns {
        _id, id_verse, text_note
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIdVerse() {
        return idVerse;
    }

    public void setIdVerse(int idVerse) {
        this.idVerse = idVerse;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public static Note fromCursor(Cursor cursor){
        Note note = new Note();
        note.set_id(cursor.getInt(Columns._id.ordinal()));
        note.setIdVerse(cursor.getInt(Columns.id_verse.ordinal()));
        note.setTextNote(cursor.getString(Columns.text_note.ordinal()));
        return note;
    }
}
