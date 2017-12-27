package com.oromil.a65appstest.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.oromil.a65appstest.App65Application;
import com.oromil.a65appstest.di.components.ActivityComponent;
import com.oromil.a65appstest.di.components.ConfigPersistentComponent;
import com.oromil.a65appstest.di.components.DaggerConfigPersistentComponent;
import com.oromil.a65appstest.di.modules.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Oromil on 19.12.2017.
 */

public abstract class BaseActivity<P extends Presenter, V extends MvpView> extends AppCompatActivity implements MvpView {
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private long mActivityId;
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponentsMap = new LongSparseArray<>();

    private ActivityComponent mActivityComponent;

    @Inject
    P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId, null);
        if (configPersistentComponent == null) {
            configPersistentComponent = createComponent();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));

        onComponentCreated(mActivityComponent);
        setContentView();
        ButterKnife.bind(this);
        setupViews();
        mPresenter.attachView(this);
    }

    protected ConfigPersistentComponent createComponent(){

         return DaggerConfigPersistentComponent.builder()
                 .applicationComponent(App65Application.get(this).getComponent())
                 .build();
    }

    protected abstract void onComponentCreated(ActivityComponent component);

    private void setContentView(){
        super.setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        Log.d("Test", "onDestroy");
    }

    protected abstract void setupViews();

    protected ActivityComponent getComponent(){
        return mActivityComponent;
    }

    protected P getPresenter(){
        return mPresenter;
    }
}
