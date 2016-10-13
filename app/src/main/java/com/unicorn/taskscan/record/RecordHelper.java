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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecordHelper {

    public static Record getRecord() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        return recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
    }


    // ======================== 上传记录 ========================

    public static void uploadRecord() {
        Record record = getRecord();
        if (record == null) {
            ToastUtils.show("尚未出发扫码");
            return;
        }
        if (record.getArriveTime() == null) {
            ToastUtils.show("尚未到达扫码");
            return;
        }
        uploadRecord(record);
    }

    private static void uploadRecord(Record record) {
        String url = getUploadRecordUrl(record);
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (ResponseHelper.isRight(response)) {
                                downloadRecords();
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

    private static String getUploadRecordUrl(final Record record) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/uploadresult?").buildUpon();
        builder.appendQueryParameter(Constant.K_USER_NO, record.getAccount());
        builder.appendQueryParameter(Constant.K_TEAM_NO, record.getTeamNo());
        builder.appendQueryParameter(Constant.K_START_TIME, getDateString(record.getDepartTime()));
        builder.appendQueryParameter(Constant.K_SET_TIME, getDateString(record.getArriveTime()));
        builder.appendQueryParameter(Constant.K_TIMELINE, getTimeline(record));
        return builder.toString();
    }

    private static String getDateString(long time) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        return new DateTime(time).toString(format);
    }

    private static String getTimeline(Record record) {
        Double result = (record.getArriveTime() - record.getDepartTime()) * 1.d / 3600 / 1000;
        return result.toString().substring(0, 6);
    }

    private static void downloadRecords() {
        String url = getDownloadRecordsUrl(ConfigUtils.getAccount());
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (ResponseHelper.isRight(response)) {
                                copeDownloadRecordsResponse(response);
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

    private static String getDownloadRecordsUrl(final String userNo) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/downloadresult?").buildUpon();
        builder.appendQueryParameter(Constant.K_USER_NO, userNo);
        return builder.toString();
    }

    private static void copeDownloadRecordsResponse(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        JSONArray data = response.getJSONArray(Constant.K_DATA);
        for (int i = 0; i != data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            String account = item.getString(Constant.K_USER_NO);
            String teamNo = item.getString(Constant.K_TEAM_NO);
            String startTime = item.getString(Constant.K_START_TIME);
            String setTime = item.getString(Constant.K_SET_TIME);

            Record record = new Record();
            record.setAccount(account);
            record.setTeamNo(teamNo);
            record.setLineNo(getLineNo(teamNo));
            record.setDepartTime(dateStringToTime(startTime));
            record.setArriveTime(dateStringToTime(setTime));

            RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
            recordDao.insertOrReplace(record);
        }
    }

    private static String getLineNo(final String teamNo) {
        return teamNo.substring(0, 2);
    }

    private static long dateStringToTime(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return dateTime.getMillis();
    }

}
