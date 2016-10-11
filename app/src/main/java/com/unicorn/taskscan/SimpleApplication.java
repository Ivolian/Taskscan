package com.unicorn.taskscan;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.unicorn.taskscan.user.UserHelper;


public class SimpleApplication extends Application {


    // ======================== instance =========================

    private static SimpleApplication instance;

    public static SimpleApplication getInstance() {
        return instance;
    }


    // ======================== onCreate =========================

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        initGreenDao();
        UserHelper.initIfNeed();
    }


    // ======================== GreenDao =========================

    private DaoSession daoSession;

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "taskscan-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return instance.daoSession;
    }


}
