package org.ajcm.tubiblia.dataset;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.ajcm.tubiblia.AppExecutors;
import org.ajcm.tubiblia.models.Book;
import org.ajcm.tubiblia.models.BookDao;
import org.ajcm.tubiblia.models.DividerBook;
import org.ajcm.tubiblia.models.Verse;
import org.ajcm.tubiblia.models.VerseDao;

@Database(entities = {Book.class, DividerBook.class, Verse.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "tu_biblia.db";
    private static volatile AppDatabase INSTANCE;

    public abstract VerseDao verseDao();
    public abstract BookDao bookDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    static final Migration MIGRATION_3_2 = new Migration(2, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    public static AppDatabase getInstance(final Context context, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "tu_biblia")
                                    .createFromAsset(DATABASE_NAME)
                                    .build();
                            INSTANCE.updateDatabaseCreated(context);
                        }
                    });
                }
            }
        }
        return INSTANCE;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
