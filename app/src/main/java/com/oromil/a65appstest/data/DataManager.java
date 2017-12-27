package com.oromil.a65appstest.data;

import com.oromil.a65appstest.data.local.DatabaseHelper;
import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.data.models.Worker;
import com.oromil.a65appstest.data.network.WorkersApi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Oromil on 19.12.2017.
 */
@Singleton
public class DataManager {

    private WorkersApi mWorkersApi;
    private DatabaseHelper mDbHelper;

    @Inject
    public DataManager(DatabaseHelper dbHelper, WorkersApi workersApi) {
        mDbHelper = dbHelper;
        mWorkersApi = workersApi;
    }

    public Observable<List<Worker>> getWorkersBySpecialityId(int specialityId) {
        return mDbHelper.getAllWorkers().distinct()
                .zipWith(mDbHelper.getSpecialityWithId(specialityId), (workers, speciality) -> {
                    List<Worker> worcersToRemove = new ArrayList<>();
                    for (Worker worker : workers) {
                        if (!worker.getSpecialities().contains(speciality))
                            worcersToRemove.add(worker);
                    }
                    workers.removeAll(worcersToRemove);
                    return workers;
                });
    }

    public Observable<List<Speciality>> loadDataAndGetSpecialities() {
        return mWorkersApi.loadWorkers().concatMap(workersResponse ->
                mDbHelper.saveWorkers(workersResponse.getWorkers()))
                .concatMap(workers -> {
                    Set<Speciality> specialities = new LinkedHashSet<>();
                    for (Worker worker : workers) {
                        specialities.addAll(worker.getSpecialities());
                    }
                    return mDbHelper.saveSpecialities(specialities);
                }).distinct();
    }

    public Observable<List<Speciality>> getSpecialitiesFromDB() {
        return mDbHelper.getSpecialities().distinct();
    }
}
