package com.oromil.a65appstest.di.components;

import com.oromil.a65appstest.di.PerActivity;
import com.oromil.a65appstest.di.modules.ActivityModule;
import com.oromil.a65appstest.ui.main.MainActivity;
import com.oromil.a65appstest.ui.specialities.SpecialitiesActivity;
import com.oromil.a65appstest.ui.workerinfo.WorkerInfoActivity;
import com.oromil.a65appstest.ui.workers.WorkersActivity;

import dagger.Subcomponent;

/**
 * Created by Oromil on 19.12.2017.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SpecialitiesActivity specialitiesActivity);
    void inject(WorkersActivity workersActivity);
    void inject(WorkerInfoActivity workerInfoActivity);
}
