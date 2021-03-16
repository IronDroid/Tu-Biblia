package org.ajcm.tubiblia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import org.ajcm.tubiblia.dataset.AppDatabase;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.Verse;

import java.util.List;

public class DataRepository {

    private static DataRepository instance;

    private final AppDatabase database;

    private MediatorLiveData<List<Book>> observableBook;

    private DataRepository(final AppDatabase database) {
        this.database = database;
        observableBook = new MediatorLiveData<>();

        observableBook.addSource(database.bookDao().allBooks(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if (database.getDatabaseCreated().getValue() != null) {
                    observableBook.postValue(books);
                }
            }
        });
    }

    public static DataRepository getInstance(AppDatabase database) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(database);
                }
            }
        }
        return instance;
    }

    public LiveData<List<Book>> allBooks() {
        return observableBook;
    }

    public LiveData<List<Verse>> getChapter(int idBook, int chapter) {
        return database.verseDao().getCapitulo(idBook, chapter);
    }
}
