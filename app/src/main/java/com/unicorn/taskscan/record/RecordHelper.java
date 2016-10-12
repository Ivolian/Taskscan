package com.unicorn.taskscan.record;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.ResponseHelper;
import com.unicorn.taskscan.utils.ToastUtils;
import com.unicorn.taskscan.volley.SimpleVolley;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecordHelper {

    public static Record getRecord() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        return recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
    }

    public static void print(Record record) {
        if (record.getArriveTime() == null){
            ToastUtils.show("尚未到达扫码");
            return;
        }

        // TODO
        //        Intent intent = new Intent();
//        intent.setAction("com.woyou.aidlservice.IWoyouService");
//        intent.setPackage("com.woyou.aidlservice");
//        boolean result = bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }


    private void uploadRecord(Record record) {
        String url = getUploadRecordUrl(record);
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (ResponseHelper.isRight(response)) {
                                //
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                SimpleVolley.getDefaultErrorListener()
        );
        SimpleVolley.addRequest(request);
    }

    private String getUploadRecordUrl(final Record record) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/uploadresult?").buildUpon();
        builder.appendQueryParameter(Constant.K_USER_NO, record.getAccount());
        builder.appendQueryParameter(Constant.K_TEAM_NO, record.getTeamNo());
        builder.appendQueryParameter("starttime", getDateString(record.getDepartTime()));
        if (record.getArriveTime() != null) {
            builder.appendQueryParameter("settime", getDateString(record.getArriveTime()));
            builder.appendQueryParameter("timeline", getTimeline(record));
        }
        return builder.toString();
    }


    private String getDateString(long time) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        return new DateTime(time).toString(format);
    }

    private String getTimeline(Record record) {
        Double result = record.getArriveTime() - record.getDepartTime() * 1.d / 3600 / 1000;
        return result.toString().substring(0, 6);
    }


    private void downloadRecord() {
        String url = getDownloadRecordUrl(ConfigUtils.getAccount());
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (ResponseHelper.isRight(response)) {
                                copeDownloadRecordResponse(response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                SimpleVolley.getDefaultErrorListener()
        );
        SimpleVolley.addRequest(request);
    }

    private void uploadRecord() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        Record record = recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
        if (record != null) {
            uploadRecord(record);
        }
    }


    private void copeDownloadRecordResponse(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        JSONArray data = response.getJSONArray(Constant.K_DATA);
        // TODO
//        "userno":"zuobiao01",
//        "teamno":"017001",
//                "starttime":"2016-10-11 15:44:47",
//                "gettime":"2016-10-11 15:45:04",
    }

    private String getDownloadRecordUrl(final String userNo) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/downloadresult?").buildUpon();
        builder.appendQueryParameter(Constant.K_USER_NO, userNo);
        return builder.toString();
    }


}
