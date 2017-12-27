package com.oromil.a65appstest.ui.specialities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.di.components.ActivityComponent;
import com.oromil.a65appstest.ui.base.BaseActivity;
import com.oromil.a65appstest.ui.workers.WorkersActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Oromil on 19.12.2017.
 */

public class SpecialitiesActivity extends BaseActivity<SpecialitiesPresenter, SpecialitiesMvpView> implements SpecialitiesMvpView {

    public static final String SPECIALITIES = "specialities";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.errorLayout)
    ViewGroup errorLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeLayout;

    @Inject
    SpecialitiesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getSerializableExtra(SPECIALITIES) != null) {
            ArrayList<Speciality> specialities = (ArrayList<Speciality>) getIntent().getSerializableExtra(SPECIALITIES);
            getPresenter().updateSpecialitiesList(specialities);
        } else {
            getPresenter().loadSpecialities();
        }
    }

    @Override
    protected void onComponentCreated(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data;
    }

    @Override
    protected void setupViews() {
        setTitle(R.string.title_specialities_activity);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeLayout.setOnRefreshListener(() -> getPresenter().updateData());
    }

    @Override
    public void updateListLata() {
        mAdapter.updateData();
    }

    @Override
    public void showEmptyDataError(boolean show) {
        if (show)
            errorLayout.setVisibility(View.VISIBLE);
        else errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void navigateToWorkersActivity(Speciality speciality) {
        WorkersActivity.start(this, speciality);
    }

    @Override
    public void showProgress(boolean show) {
        mSwipeLayout.setRefreshing(show);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context, ArrayList<Speciality> data) {
        Intent starter = new Intent(context, SpecialitiesActivity.class);
        starter.putExtra(SPECIALITIES, data);
        context.startActivity(starter);
    }
}
