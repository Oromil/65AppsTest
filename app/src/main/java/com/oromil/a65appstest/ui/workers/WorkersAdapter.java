package com.oromil.a65appstest.ui.workers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.di.PerActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Oromil on 21.12.2017.
 */

@PerActivity
public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.WorkersViewHolder> {

    private WorkersPresenter mPresenter;

    @Inject
    public WorkersAdapter(WorkersPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public WorkersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false);
        return new WorkersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WorkersViewHolder holder, int position) {

        mPresenter.bindWorkerItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getWorkersListSize();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    static class WorkersViewHolder extends RecyclerView.ViewHolder implements WorkerViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.imgUserAvatar)
        CircleImageView userAvatar;
        @BindView(R.id.tvUserName)
        TextView userName;
        @BindView(R.id.tvUserSurname)
        TextView userSurname;
        @BindView(R.id.tvAge)
        TextView userAge;

        public WorkersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindWorkerData(String username, String surname, String age, String avatarLink, OnClickListener onClickListener) {
            cardView.setOnClickListener(onClickListener);
            userName.setText(username);
            userSurname.setText(surname);
            userAge.setText(age);

            if (avatarLink != null && !avatarLink.equals(""))
                Picasso.with(itemView.getContext()).load(avatarLink).error(R.drawable.ic_default_user).into(userAvatar);
        }
    }
}
