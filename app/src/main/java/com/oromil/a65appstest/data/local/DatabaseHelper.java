package com.oromil.a65appstest.data.local;

import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.data.models.Worker;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oromil on 19.12.2017.
 */

public class DatabaseHelper {

    public static final String SPECIALITIES_QUERY = "SELECT*FROM " +
            Database.SPECIALITIES_TABLE_NAME;
    public static final String WORKERS_QUERY = "SELECT*FROM " + Database.TABLE_NAME;

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public Observable<List<Speciality>> getSpecialities() {
        return mDb.createQuery(Database.SPECIALITIES_TABLE_NAME, SPECIALITIES_QUERY)
                .mapToList(Database.SpecialtiesTable::parseCursor);
    }

    public Observable<List<Worker>> getAllWorkers() {
        return mDb.createQuery(Database.TABLE_NAME, WORKERS_QUERY)
                .mapToList(Database.WorkersTable::parseCursor)
                .map(workers -> {
                    List<Worker> newWorkersList = new ArrayList<>();
                    for (Worker worker : workers) {
                        if (newWorkersList.contains(worker)) {
                            newWorkersList.get(newWorkersList.indexOf(worker))
                                    .getSpecialityIdSet().addAll(worker.getSpecialityIdSet());
                        } else newWorkersList.add(worker);
                    }
                    return newWorkersList;
                })
                .zipWith(getSpecialities(), (workers, specialities) -> {
                    for (Worker worker : workers) {
                        for (Speciality speciality : specialities) {
                            for (int specialityId : worker.getSpecialityIdSet()) {
                                if (specialityId == speciality.getId())
                                    worker.addSpeciality(speciality);
                            }
                        }
                    }
                    return workers;
                });
    }

    public Observable<Speciality> getSpecialityWithId(int id) {
        return mDb.createQuery(Database.SPECIALITIES_TABLE_NAME, SPECIALITIES_QUERY +
                " WHERE " + Database.SpecialtiesTable.COLUMN_SPECIALITY_ID + " = '" + id + "'")
                .mapToOne(Database.SpecialtiesTable::parseCursor);
    }

    //
    public Observable<List<Speciality>> saveSpecialities(Set<Speciality> specialities) {

        return Observable.create(e -> {
            BriteDatabase.Transaction transaction = mDb.newTransaction();

            try {
                mDb.delete(Database.SPECIALITIES_TABLE_NAME, null);
                ArrayList<Speciality> specialitiesList = new ArrayList<>();
                for (Speciality speciality : specialities) {
                    mDb.insert(Database.SPECIALITIES_TABLE_NAME, Database.SpecialtiesTable.toContentValues(speciality));
                }
                specialitiesList.addAll(specialities);
                e.onNext(specialitiesList);
                transaction.markSuccessful();
                e.onComplete();
            } finally {
                transaction.end();
            }
        });
    }

    public Observable<List<Worker>> saveWorkers(List<Worker> workers) {
        return Observable.create(emitter -> {
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                mDb.delete(Database.TABLE_NAME, null);
                for (Worker worker : workers) {
                    for (Speciality speciality : worker.getSpecialities()) {
                        mDb.insert(Database.TABLE_NAME,
                                Database.WorkersTable.toContentValues(worker, speciality.getId()));
                    }
                }
                emitter.onNext(workers);
                transaction.markSuccessful();
                emitter.onComplete();

            } finally {
                transaction.end();
            }
        });
    }
}
