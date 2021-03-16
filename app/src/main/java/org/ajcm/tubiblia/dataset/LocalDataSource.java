package org.ajcm.tubiblia.dataset;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import org.ajcm.tubiblia.models.Book;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface LocalDataSource {

    Flowable<List<Book>> getBooks();

    Flowable<Book> getBook(int idBook);

    Completable insertOrUpdateBook(Book book);
}
