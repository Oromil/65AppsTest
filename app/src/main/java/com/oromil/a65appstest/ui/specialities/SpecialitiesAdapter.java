package com.oromil.a65appstest.ui.specialities;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oromil on 22.12.2017.
 */

@PerActivity
public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.SpecialitiesViewHolder> {

    private SpecialitiesPresenter mPresenter;

    @Inject
    public SpecialitiesAdapter(SpecialitiesPresenter presenter){
        mPresenter = presenter;
    }

    @Override
    public SpecialitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_speciality, parent, false);
        return new SpecialitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialitiesViewHolder holder, int position) {
        mPresenter.bindSpecialityItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getSpecialitiesListSize();
    }

    public void updateData(){
        notifyDataSetChanged();
    }

    static class SpecialitiesViewHolder extends RecyclerView.ViewHolder implements SpecialityViewHolder{

        @BindView(R.id.tvName)
        TextView tvSpecialityName;
        @BindView(R.id.cardView)
        CardView cardView;

        public SpecialitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindSpecialiryViw(String title, View.OnClickListener onClickListener) {
            tvSpecialityName.setText(title);
            cardView.setOnClickListener(onClickListener);
        }
    }
}
