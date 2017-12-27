package com.oromil.a65appstest.ui.specialities;

import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.ui.base.MvpView;

/**
 * Created by Oromil on 19.12.2017.
 */

public interface SpecialitiesMvpView extends MvpView {
    void updateListLata();

    void showEmptyDataError(boolean show);

    void navigateToWorkersActivity(Speciality speciality);

    void showProgress(boolean show);

    void showToast(String text);
}
