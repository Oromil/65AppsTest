package com.oromil.a65appstest.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oromil.a65appstest.data.models.WorkersResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Oromil on 19.12.2017.
 */

public interface WorkersApi {

    String BASE_URL = "http://gitlab.65apps.com/";

    @GET("65gb/static/raw/master/testTask.json")
    Observable<WorkersResponse> loadWorkers();

    class Creator {
        public static WorkersApi createWorkersApi() {
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(WorkersApi.class);
        }
    }
}
