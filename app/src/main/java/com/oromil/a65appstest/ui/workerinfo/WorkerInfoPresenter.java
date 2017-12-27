package com.oromil.a65appstest.ui.workerinfo;

import com.oromil.a65appstest.di.ConfigPersistent;
import com.oromil.a65appstest.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Oromil on 24.12.2017.
 */

@ConfigPersistent
public class WorkerInfoPresenter extends BasePresenter<WorkerInfoMvpView> {

    @Inject
    public WorkerInfoPresenter() {
    }

}
