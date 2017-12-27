package com.oromil.a65appstest.ui.workerinfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oromil.a65appstest.R;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.di.PerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oromil on 26.12.2017.
 */

@PerActivity
public class WorkerInfoSpecialitiesAdapter extends RecyclerView.Adapter<WorkerInfoSpecialitiesAdapter.WorkerInfoSpecialitiesViewHolder> {

    private List<Speciality> mSpecialities;

    public WorkerInfoSpecialitiesAdapter(List<Speciality> specialities) {
        mSpecialities = specialities;
    }

    @Override
    public WorkerInfoSpecialitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker_info_speciality,
                parent, false);
        return new WorkerInfoSpecialitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkerInfoSpecialitiesViewHolder holder, int position) {
        if (position==mSpecialities.size()-1)
            holder.devider.setVisibility(View.GONE);
        holder.tvName.setText(mSpecialities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    static class WorkerInfoSpecialitiesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSpecialityName)
        TextView tvName;
        @BindView(R.id.devider)
        View devider;

        public WorkerInfoSpecialitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
