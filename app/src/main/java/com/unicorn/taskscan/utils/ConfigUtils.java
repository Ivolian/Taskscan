package com.unicorn.taskscan.utils;

public class ConfigUtils {

    final static private String TEST_IP = "139.224.69.49";

    final static private String TEST_PORT = "9002";

    public static String getBaseUrl() {
        return "http://" + TEST_IP + ":" + TEST_PORT;
    }

    public static String getAccount() {
        TinyDB tinyDB = TinyDB.getNewInstance();
        return tinyDB.getString(Constant.K_ACCOUNT);
    }

    public static void saveAccount(final String account){
        TinyDB tinyDB = TinyDB.getNewInstance();
        tinyDB.putString(Constant.K_ACCOUNT, account);
    }

}
