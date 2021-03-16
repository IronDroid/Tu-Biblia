package org.ajcm.tubiblia.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VerseDao {

    @Query("select * from verse_table where id_book = :idBook and chapter = :idCap")
    LiveData<List<Verse>> getCapitulo(int idBook, int idCap);

//    @Update(entity = Verse.class,)
//    long addFav(int idBook, int chapter, int verse, boolean favState) {
//        values.put(Verse.Columns.favorite.name(), favState);
//        return db.update(DATABASE_TABLE_VERSE, values,
//                Verse.Columns.id_book.name() + " = " + idBook + " AND " +
//                        Verse.Columns.chapter.name() + " = " + chapter + " AND " +
//                        Verse.Columns.verse.name() + " = " + verse, null);
//    }

//    public long addNote(int idVerse, String note) {
//        ContentValues values = new ContentValues();
//        values.put(Verse.Columns.text_note.name(), note);
//        open();
//        return db.update(DATABASE_TABLE_VERSE, values, Verse.Columns._id.name() + " = " + idVerse, null);
//    }

//    public long addNote(int idBook, int chapter, int verse, String note) {
//        ContentValues values = new ContentValues();
//        values.put(Verse.Columns.text_note.name(), note);
//        open();
//        return db.update(DATABASE_TABLE_VERSE, values,
//                Verse.Columns.id_book.name() + " = " + idBook + " AND " +
//                        Verse.Columns.chapter.name() + " = " + chapter + " AND " +
//                        Verse.Columns.verse.name() + " = " + verse, null);
//    }

//    public long deleteNote(int idVerse) {
//        ContentValues values = new ContentValues();
//        values.put(Verse.Columns.text_note.name(), "");
//        open();
//        return db.update(DATABASE_TABLE_VERSE, values, Verse.Columns._id.name() + " = " + idVerse, null);
//    }

    @Query("select * from verse_table where favorite = :1")
    LiveData<List<Verse>> getAllFav();

    @Query("select * from verse_table where text_note != :null")
    LiveData<List<Verse>> getAllNotes();
}
