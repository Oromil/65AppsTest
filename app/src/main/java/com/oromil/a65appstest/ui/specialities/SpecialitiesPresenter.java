package com.oromil.a65appstest.ui.specialities;

import com.oromil.a65appstest.data.DataManager;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.di.ConfigPersistent;
import com.oromil.a65appstest.ui.base.BasePresenter;
import com.oromil.a65appstest.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oromil on 19.12.2017.
 */

@ConfigPersistent
public class SpecialitiesPresenter extends BasePresenter<SpecialitiesMvpView> {

    private List<Speciality> mSpecialities;
    private DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public SpecialitiesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mSpecialities = new ArrayList<>();
    }

    public int getSpecialitiesListSize() {
        return mSpecialities.size();
    }

    public void updateSpecialitiesList(List<Speciality> data) {
        updateDataList(data);
        getView().updateListLata();
    }

    public void loadSpecialities() {
        RxUtil.dispose(mDisposable);
        mDisposable = mDataManager.getSpecialitiesFromDB()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(specialities -> {
                    if (specialities.isEmpty())
                        getView().showEmptyDataError(true);
                    else {
                        getView().showEmptyDataError(false);
                        updateDataList(specialities);
                        getView().updateListLata();
                    }
                }, Throwable::getMessage);
    }

    public void updateData() {
        getView().showProgress(true);
        RxUtil.dispose(mDisposable);
        mDataManager.loadDataAndGetSpecialities().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Speciality>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<Speciality> specialities) {
                        if (specialities.isEmpty())
                            SpecialitiesPresenter.this.getView().showEmptyDataError(true);
                        else {
                            SpecialitiesPresenter.this.getView().showEmptyDataError(false);
                            SpecialitiesPresenter.this.updateDataList(specialities);
                            SpecialitiesPresenter.this.getView().updateListLata();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSpecialities.isEmpty())
                            SpecialitiesPresenter.this.getView().showEmptyDataError(true);
                        getView().showToast(e.getMessage());
                        getView().showProgress(false);
                    }

                    @Override
                    public void onComplete() {
                        getView().showProgress(false);
                    }
                });
    }

    public void bindSpecialityItem(SpecialityViewHolder holder, int position) {
        holder.bindSpecialiryViw(mSpecialities.get(position).getName(), view ->
                getView().navigateToWorkersActivity(mSpecialities.get(position))
        );
    }

    private void updateDataList(List<Speciality> data) {
        mSpecialities.clear();
        mSpecialities.addAll(data);
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.dispose(mDisposable);
    }
}
