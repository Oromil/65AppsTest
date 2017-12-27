package com.oromil.a65appstest.ui.base;

/**
 * Created by Oromil on 19.12.2017.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V view);
    void detachView();
}
