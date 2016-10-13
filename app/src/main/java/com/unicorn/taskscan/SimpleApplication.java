package com.unicorn.taskscan;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.unicorn.taskscan.record.DaoMaster;
import com.unicorn.taskscan.record.DaoSession;
import com.unicorn.taskscan.user.UserHelper;
import com.unicorn.taskscan.volley.SimpleVolley;

import net.danlew.android.joda.JodaTimeAndroid;


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
        init_();
    }

    private void init() {
        JodaTimeAndroid.init(instance);
        SimpleVolley.init(instance);
        initGreenDao();
    }

    private void init_() {
        UserHelper.initUsersIfNeed();
    }


    // ======================== GreenDao =========================

    private DaoSession daoSession;

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "taskscan-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return instance.daoSession;
    }


}
