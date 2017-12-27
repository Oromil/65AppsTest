package com.oromil.a65appstest.ui.workerinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.data.models.Worker;
import com.oromil.a65appstest.di.components.ActivityComponent;
import com.oromil.a65appstest.ui.base.BaseActivity;
import com.oromil.a65appstest.util.DateUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Oromil on 24.12.2017.
 */

public class WorkerInfoActivity extends BaseActivity<WorkerInfoPresenter, WorkerInfoMvpView> implements WorkerInfoMvpView {

    public static final String CURRENT_WORKER = "worker";

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSurname)
    TextView tvSurname;
    @BindView(R.id.tvBirthday)
    TextView tvBirthDay;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.rvSpecialities)
    RecyclerView mRecyclerView;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;

    WorkerInfoSpecialitiesAdapter mAdapter;


    @Override
    protected void onComponentCreated(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_info;
    }

    @Override
    protected void setupViews() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent startIntent = getIntent();
        if (startIntent.getSerializableExtra(CURRENT_WORKER) != null) {
            Worker currentWorker = (Worker) startIntent.getSerializableExtra(CURRENT_WORKER);
            tvName.setText(currentWorker.getName());
            tvSurname.setText(currentWorker.getSurname());
            tvBirthDay.setText(currentWorker.getBirthday());
            tvAge.setText(DateUtils.calulateAge(currentWorker.getBirthday()));
            if (currentWorker.getAvatarLink() != null && !currentWorker.getAvatarLink().equals(""))
                Picasso.with(this).load(currentWorker.getAvatarLink())
                        .error(R.drawable.ic_default_user).into(imgAvatar);

            setTitle(currentWorker.getName());
            mAdapter = new WorkerInfoSpecialitiesAdapter(currentWorker.getSpecialities());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.updateData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, Worker worker) {
        Intent starter = new Intent(context, WorkerInfoActivity.class);
        starter.putExtra(CURRENT_WORKER, worker);
        context.startActivity(starter);
    }
}
