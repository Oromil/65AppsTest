package com.oromil.a65appstest;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.oromil.a65appstest.di.components.ApplicationComponent;
import com.oromil.a65appstest.di.components.DaggerApplicationComponent;
import com.oromil.a65appstest.di.modules.ApplicationModule;

/**
 * Created by Oromil on 19.12.2017.
 */

public class App65Application extends Application {
    private static ApplicationComponent mComponent;

    public static App65Application get(Context context){
        return (App65Application) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initStetho();
    }

    public ApplicationComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mComponent;
    }

    private void initStetho() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
