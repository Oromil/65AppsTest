package com.oromil.a65appstest.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.oromil.a65appstest.data.models.Speciality;
import com.oromil.a65appstest.data.models.Worker;

/**
 * Created by Oromil on 19.12.2017.
 */

public class Database {


    public static final String SPECIALITIES_TABLE_NAME = "specialities";
    public static final String TABLE_NAME = "workers";

    public Database() {
    }

    public abstract static class SpecialtiesTable {

        public static final String COLUMN_SPECIALITY_ID = "id";
        public static final String COLUMN_SPECIALITY_NAME = "speciality";

        public static final String CREATE =
                "CREATE TABLE " + SPECIALITIES_TABLE_NAME + " ( " +
                        COLUMN_SPECIALITY_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_SPECIALITY_NAME + " TEXT NOT NULL" + " ); ";

        public static ContentValues toContentValues(Speciality speciality) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SPECIALITY_ID, speciality.getId());
            values.put(COLUMN_SPECIALITY_NAME, speciality.getName());
            return values;
        }

        public static Speciality parseCursor(Cursor cursor) {
            return new Speciality(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SPECIALITY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIALITY_NAME)));
        }
    }

    public abstract static class WorkersTable {
        public static final String WORKER_ID = "worker_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String BIRTHDAY = "birthday";
        public static final String AVATAR_LINK = "avatar";
        public static final String WORKER_SPECIALLITY_ID = "speciality_id";


        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" + WORKER_ID + " INTEGER PRIMARY KEY, "
                        + LAST_NAME + " TEXT NOT NULL, " + FIRST_NAME + " TEXT NOT NULL, "
                        + BIRTHDAY + " TEXT, " + AVATAR_LINK + " TEXT, " + WORKER_SPECIALLITY_ID +
                        " INTEGER" + " ); ";

        public static ContentValues toContentValues(Worker worker, int id) {
            ContentValues values = new ContentValues();
            values.put(FIRST_NAME, worker.getName());
            values.put(LAST_NAME, worker.getSurname());
            values.put(BIRTHDAY, worker.getBirthday());
            values.put(AVATAR_LINK, worker.getAvatarLink());
            values.put(WORKER_SPECIALLITY_ID, id);
            return values;
        }

        public static Worker parseCursor(Cursor cursor) {
            return Worker.newBuilder()
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME)))
                    .setSurname(cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME)))
                    .setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(BIRTHDAY)))
                    .setAvatarLink(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_LINK)))
                    .addSpecialityId(cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_SPECIALLITY_ID)))
                    .build();
        }
    }
}
