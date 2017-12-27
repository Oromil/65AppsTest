package com.oromil.a65appstest.util;

import io.reactivex.disposables.Disposable;

/**
 * Created by Oromil on 23.12.2017.
 */

public class RxUtil {

    public static void dispose(Disposable d) {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }
}
