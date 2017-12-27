package com.oromil.a65appstest.ui.workers;

import com.oromil.a65appstest.data.DataManager;
import com.oromil.a65appstest.data.models.Worker;
import com.oromil.a65appstest.di.ConfigPersistent;
import com.oromil.a65appstest.ui.base.BasePresenter;
import com.oromil.a65appstest.util.DateUtils;
import com.oromil.a65appstest.util.RxUtil;
import com.oromil.a65appstest.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oromil on 23.12.2017.
 */

@ConfigPersistent
public class WorkersPresenter extends BasePresenter<WorkersMvpView> {

    private DataManager mDataManager;
    private List<Worker> mWorkersList;
    private Disposable mDisposable;

    @Inject
    public WorkersPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mWorkersList = new ArrayList<>();
    }

    public void loadWorkersList(int workersSpecialityId) {

        RxUtil.dispose(mDisposable);
        mDisposable = mDataManager.getWorkersBySpecialityId(workersSpecialityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workers -> {
                    if (workers.isEmpty())
                        getView().showDataError(true);
                    else {
                        getView().showDataError(false);
                        mWorkersList.clear();
                        for (Worker worker : workers) {
                            mWorkersList.add(createFormattedWorker(worker));
                        }
                        getView().updateDataList();
                    }
                });
    }

    public void updateData(int workersSpecialityId) {
        getView().showProgress(true);
        RxUtil.dispose(mDisposable);
        mDataManager.loadDataAndGetSpecialities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(specialities -> mDataManager
                        .getWorkersBySpecialityId(workersSpecialityId))
                .subscribe(new Observer<List<Worker>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<Worker> workers) {
                        if (workers.isEmpty())
                            getView().showDataError(true);
                        else {
                            getView().showDataError(false);
                            mWorkersList.clear();
                            for (Worker worker : workers) {
                                mWorkersList.add(createFormattedWorker(worker));
                            }
                            getView().showProgress(false);
                            getView().updateDataList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mWorkersList.isEmpty())
                            getView().showDataError(true);
                        getView().showProgress(false);
                        getView().showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public int getWorkersListSize() {
        return mWorkersList.size();
    }

    public void bindWorkerItem(WorkerViewHolder holder, int itemPosition) {
        Worker worker = mWorkersList.get(itemPosition);
        holder.bindWorkerData(worker.getName(), worker.getSurname(), DateUtils
                        .calulateAge(worker.getBirthday()),
                worker.getAvatarLink(), view -> getView().navigateToWorkerInfoActivity(worker));
    }

    private Worker createFormattedWorker(Worker worker) {
        return Worker.newBuilder()
                .setName(StringUtils.format(worker.getName()))
                .setSurname(StringUtils.format(worker.getSurname()))
                .setBirthday(DateUtils.formatDate(worker.getBirthday()))
                .setAvatarLink(worker.getAvatarLink())
                .addSpecialityId(worker.getSpecialityIdSet())
                .addSpecialities(worker.getSpecialities())
                .build();
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.dispose(mDisposable);
    }
}
