package org.ajcm.tubiblia.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("select * from book_table")
    LiveData<List<Book>> allBooks();
}
