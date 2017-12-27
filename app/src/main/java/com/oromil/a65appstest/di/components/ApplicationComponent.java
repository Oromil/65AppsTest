package com.oromil.a65appstest.di.components;

import android.app.Application;
import android.content.Context;

import com.oromil.a65appstest.data.DataManager;
import com.oromil.a65appstest.di.ApplicationContext;
import com.oromil.a65appstest.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oromil on 19.12.2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

//    DatabaseHelper databaseHelper();

    DataManager dataManager();

}


