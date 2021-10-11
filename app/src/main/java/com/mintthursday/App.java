package com.mintthursday;

import android.app.Application;

import androidx.room.Room;

import com.github.terrakok.cicerone.Cicerone;
import com.github.terrakok.cicerone.NavigatorHolder;
import com.github.terrakok.cicerone.Router;
import com.mintthursday.database.AppDatabase;

public class App extends Application {

    private static App instance;
    private Cicerone<Router> cicerone;
    private AppDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries() //todo
                .build();
        cicerone = Cicerone.create();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }
}
