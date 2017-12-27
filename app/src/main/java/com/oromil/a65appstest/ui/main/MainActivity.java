package com.oromil.a65appstest.ui.main;

import android.widget.Toast;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.di.components.ActivityComponent;
import com.oromil.a65appstest.ui.base.BaseActivity;
import com.oromil.a65appstest.ui.specialities.SpecialitiesActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter, MainMvpView> implements MainMvpView {

    @Override
    protected void onComponentCreated(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupViews() {
    }

    @Override
    public void navigateToSpecialitiesActivity(List<Speciality> specialities) {
        SpecialitiesActivity.start(this, (ArrayList<Speciality>) specialities);
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
