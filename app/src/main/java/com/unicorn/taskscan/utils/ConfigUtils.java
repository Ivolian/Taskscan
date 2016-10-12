package com.unicorn.taskscan.utils;

public class ConfigUtils {

    public static String getAccount() {
        TinyDB tinyDB = TinyDB.getNewInstance();
        return tinyDB.getString(Constant.K_ACCOUNT);
    }

    final static private String TEST_IP = "139.224.69.49";

    final static private String TEST_PORT = "9002";

    public static String getBaseUrl() {
        return "http://" + TEST_IP + ":" + TEST_PORT;
    }

}
