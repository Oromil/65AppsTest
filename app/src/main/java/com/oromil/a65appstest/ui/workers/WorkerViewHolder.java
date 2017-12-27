package com.oromil.a65appstest.ui.workers;

import android.view.View;

import static android.view.View.*;

/**
 * Created by Oromil on 23.12.2017.
 */

public interface WorkerViewHolder {

    void bindWorkerData(String username, String surname, String age, String avatarLink, OnClickListener onClickListener);
}
