package com.oromil.a65appstest.ui.main;

import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.ui.base.MvpView;

import java.util.List;

/**
 * Created by Oromil on 19.12.2017.
 */

public interface MainMvpView extends MvpView {

    void navigateToSpecialitiesActivity(List<Speciality> specialities);

    void showToast(String text);
}
