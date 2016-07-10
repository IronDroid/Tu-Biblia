package org.ajcm.tubiblia.models;

import android.database.Cursor;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class Book {
    private int idBook;
    private String nameBook;
    private int numChapter;
    private int idDivider;

    public enum Columns {
        _id, name, num_chapter, id_divider
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getNumChapter() {
        return numChapter;
    }

    public void setNumChapter(int numChapter) {
        this.numChapter = numChapter;
    }

    public int getIdDivider() {
        return idDivider;
    }

    public void setIdDivider(int idDivider) {
        this.idDivider = idDivider;
    }

    public static Book fromCursor(Cursor cursor){
        Book book = new Book();
        book.setIdBook(cursor.getInt(Columns._id.ordinal()));
        book.setNameBook(cursor.getString(Columns.name.ordinal()));
        book.setNumChapter(cursor.getInt(Columns.num_chapter.ordinal()));
        book.setIdDivider(cursor.getInt(Columns.id_divider.ordinal()));
        return book;
    }
}
