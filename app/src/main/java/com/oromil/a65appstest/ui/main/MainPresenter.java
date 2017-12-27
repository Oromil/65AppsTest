package com.oromil.a65appstest.ui.main;

import com.oromil.a65appstest.data.DataManager;
import com.oromil.a65appstest.di.ConfigPersistent;
import com.oromil.a65appstest.ui.base.BasePresenter;
import com.oromil.a65appstest.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oromil on 19.12.2017.
 */

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    protected void onViewAttached() {
        loadData();
    }

    public void loadData() {
        RxUtil.dispose(mDisposable);
        mDisposable = mDataManager.loadDataAndGetSpecialities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(specialities ->
                                getView().navigateToSpecialitiesActivity(specialities)
                        , throwable -> {
                            getView().showToast(throwable.getMessage());
                            getView().navigateToSpecialitiesActivity(null);
                        });
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.dispose(mDisposable);
    }
}
