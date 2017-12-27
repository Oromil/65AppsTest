package com.oromil.a65appstest.ui.workers;

import com.oromil.a65appstest.data.models.Worker;
import com.oromil.a65appstest.ui.base.MvpView;

/**
 * Created by Oromil on 23.12.2017.
 */

public interface WorkersMvpView extends MvpView {
    void updateDataList();

    void showDataError(boolean show);

    void showProgress(boolean show);

    void showToast(String text);

    void navigateToWorkerInfoActivity(Worker worker);
}
