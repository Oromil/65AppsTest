package com.oromil.a65appstest.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Oromil on 19.12.2017.
 */

public class WorkersResponse {
    @SerializedName("response")
    private List<Worker> workers;

    public List<Worker> getWorkers() {
        return workers;
    }
}
