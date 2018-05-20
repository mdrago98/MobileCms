package com.example.drago.testp.config;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.drago.testp.model.Listing;

public class Application extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("MyDb.db")
                .addModelClass(Listing.class)
                .create();

        ActiveAndroid.initialize(dbConfiguration);
    }
}
