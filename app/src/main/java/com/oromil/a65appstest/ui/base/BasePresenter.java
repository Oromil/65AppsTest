package com.oromil.a65appstest.ui.base;

/**
 * Created by Oromil on 19.12.2017.
 */

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mView;

    @Override
    public void attachView(T view) {
        mView = view;
        onViewAttached();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    protected void onViewAttached(){}

    protected T getView(){
        return mView;
    }
}
