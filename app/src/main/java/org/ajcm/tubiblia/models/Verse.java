package org.ajcm.tubiblia.models;

import android.database.Cursor;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class Verse {

    private int idBook;
    private int chapter;
    private int verse;
    private String text;
    private boolean fav;

    public enum Columns {
        favorito,
        id_libro,
        capitulo,
        versiculo,
        texto
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

    public static Verse fromCursor(Cursor cursor) {
        Verse verse = new Verse();
        verse.setIdBook(cursor.getInt(Columns.id_libro.ordinal()));
        verse.setChapter(cursor.getInt(Columns.capitulo.ordinal()));
        verse.setVerse(cursor.getInt(Columns.versiculo.ordinal()));
        verse.setText(cursor.getString(Columns.texto.ordinal()));
        verse.setFav(Boolean.valueOf(cursor.getString(Columns.favorito.ordinal())));
        return verse;
    }
}
