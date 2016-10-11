package com.unicorn.taskscan.utils;

public class ConfigUtils {

    public static String getAccount() {
        TinyDB tinyDB = TinyDB.getNewInstance();
        return tinyDB.getString(Constant.K_ACCOUNT);
    }


}
