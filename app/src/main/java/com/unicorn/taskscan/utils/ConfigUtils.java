package com.unicorn.taskscan.utils;

import com.unicorn.taskscan.user.UserHelper;

public class ConfigUtils {

    final static private String TEST_IP = "139.196.107.169";

    final static private String TEST_PORT = "9003";

    public static String getBaseUrl() {
        return "http://" + TEST_IP + ":" + TEST_PORT;
    }

    public static String getAccount() {
        TinyDB tinyDB = TinyDB.getNewInstance();
        return tinyDB.getString(Constant.K_ACCOUNT);
    }

    public static void saveAccount(final String account) {
        TinyDB tinyDB = TinyDB.getNewInstance();
        tinyDB.putString(Constant.K_ACCOUNT, account);
    }

    public static
    @UserHelper.UserType
    String getUserType() {
        return UserHelper.getUserType(ConfigUtils.getAccount());
    }

}
