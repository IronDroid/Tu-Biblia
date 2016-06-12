package org.ajcm.tubiblia.models;

import android.database.Cursor;

/**
 * Created by jhonlimaster on 09-06-16.
 */
public class Book {
    private int idBook;
    private String nameBook;
    private int numCap;

    public enum Columns {
        id_libro, nombre, num_cap
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

    public int getNumCap() {
        return numCap;
    }

    public void setNumCap(int numCap) {
        this.numCap = numCap;
    }

    public static Book fromCursor(Cursor cursor){
        Book book = new Book();
        book.setIdBook(cursor.getInt(Columns.id_libro.ordinal()));
        book.setNameBook(cursor.getString(Columns.nombre.ordinal()));
        book.setNumCap(cursor.getInt(Columns.num_cap.ordinal()));
        return book;
    }
}
