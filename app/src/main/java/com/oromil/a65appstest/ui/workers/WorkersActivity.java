package com.oromil.a65appstest.ui.workers;

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
import com.oromil.a65appstest.data.models.Worker;
import com.oromil.a65appstest.di.components.ActivityComponent;
import com.oromil.a65appstest.ui.base.BaseActivity;
import com.oromil.a65appstest.ui.workerinfo.WorkerInfoActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Oromil on 23.12.2017.
 */

public class WorkersActivity extends BaseActivity<WorkersPresenter, WorkersMvpView> implements WorkersMvpView {

    public static final String CURRENT_SPECIALITY = "worker_speciality";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.errorLayout)
    ViewGroup errorLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeLayout;

    @Inject
    WorkersAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Speciality currentSpeciality = (Speciality) getIntent().getExtras()
                .getSerializable(CURRENT_SPECIALITY);
        if (currentSpeciality != null) {
            setTitle(currentSpeciality.getName());
            mSwipeLayout.setOnRefreshListener(() ->
                    getPresenter().updateData(currentSpeciality.getId()));
            getPresenter().loadWorkersList(currentSpeciality.getId());
        } else showDataError(true);
    }

    @Override
    protected void onComponentCreated(ActivityComponent component) {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data;
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateDataList() {
        mAdapter.updateData();
    }

    @Override
    public void showDataError(boolean show) {
        if (show) {
            errorLayout.setVisibility(View.VISIBLE);
        } else errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(boolean show) {
        mSwipeLayout.setRefreshing(show);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToWorkerInfoActivity(Worker worker) {
        WorkerInfoActivity.start(this, worker);
    }

    public static void start(Context context, Speciality specialiry) {
        Intent starter = new Intent(context, WorkersActivity.class);
        starter.putExtra(CURRENT_SPECIALITY, specialiry);
        context.startActivity(starter);
    }
}
