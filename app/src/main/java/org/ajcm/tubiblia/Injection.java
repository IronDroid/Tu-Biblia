package org.ajcm.tubiblia;

import android.content.Context;

import org.ajcm.tubiblia.dataset.AppDatabase;
import org.ajcm.tubiblia.dataset.LocalDataSource;

public class Injection {

    public static LocalDataSource provideUserDataSource(Context context, AppExecutors appExecutors) {
        AppDatabase database = AppDatabase.getInstance(context, appExecutors);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
