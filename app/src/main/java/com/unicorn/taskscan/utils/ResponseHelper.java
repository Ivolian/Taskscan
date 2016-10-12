package com.unicorn.taskscan.utils;

import org.json.JSONObject;


public class ResponseHelper {

    public static boolean isRight(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        String code = response.getString(Constant.K_CODE);
        boolean success = (code != null && code.equals(Constant.RESPONSE_SUCCESS_CODE));
        if (!success) {
            showMsg(responseString);
        }
        return success;
    }

    private static void showMsg(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        String msg = response.getString(Constant.K_MSG);
        if (msg != null) {
            ToastUtils.show(msg);
        }
    }

}
