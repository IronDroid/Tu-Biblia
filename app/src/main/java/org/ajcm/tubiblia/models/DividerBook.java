package org.ajcm.tubiblia.models;

import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by jhonlimaster on 08-07-16.
 */
@Entity(tableName = "divider_table")
public class DividerBook {
    @PrimaryKey
    @ColumnInfo
    private int _id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String color;

    public enum Columns {
        _id, name, color
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static DividerBook fromCursor(Cursor cursor){
        DividerBook dividerBook = new DividerBook();
        dividerBook.set_id(cursor.getInt(Columns._id.ordinal()));
        dividerBook.setName(cursor.getString(Columns.name.ordinal()));
        dividerBook.setColor(cursor.getString(Columns.color.ordinal()));
        return dividerBook;
    }
}
