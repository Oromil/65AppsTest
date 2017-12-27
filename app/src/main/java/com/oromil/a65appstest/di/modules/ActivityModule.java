package com.oromil.a65appstest.di.modules;

import android.app.Activity;
import android.content.Context;

import com.oromil.a65appstest.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oromil on 19.12.2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
