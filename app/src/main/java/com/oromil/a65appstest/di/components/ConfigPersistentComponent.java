package com.oromil.a65appstest.di.components;

import com.oromil.a65appstest.di.ConfigPersistent;
import com.oromil.a65appstest.di.modules.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}