package org.ajcm.tubiblia.models;

import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by jhonlimaster on 09-06-16.
 */
@Entity(tableName = "book_table")
public class Book {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int idBook;
    @ColumnInfo(name = "name")
    private String nameBook;
    @ColumnInfo(name = "num_chapter")
    private int numChapter;
    @ColumnInfo(name = "id_divider")
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
