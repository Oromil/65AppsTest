package com.oromil.a65appstest.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oromil.a65appstest.di.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by Oromil on 19.12.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Apps65.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foregin_keys = 1");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(Database.SpecialtiesTable.CREATE);
            sqLiteDatabase.execSQL(Database.WorkersTable.CREATE);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
