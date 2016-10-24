package com.unicorn.taskscan.record;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.DateUtils;
import com.unicorn.taskscan.utils.ResponseHelper;
import com.unicorn.taskscan.utils.ToastUtils;
import com.unicorn.taskscan.volley.SimpleVolley;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecordSyncer {

    /*
        数据同步流程：
        1、循环上传isupt=1的数据信息，上传一条之后，该条数据isupt设为0；
        2、上传数据完成之后，下载数据，update出发时间、到达时间、时间间距三个字段信息；
     */

    public void sync() {
        uploadRecordIfNeed();
    }

    private void uploadRecordIfNeed() {
        Record recordNeedUpload = getRecordNeedUpload();
        if (recordNeedUpload != null) {
            uploadRecord(recordNeedUpload);
        } else {
            downloadRecord();
        }
    }

    private Record getRecordNeedUpload() {
        List<Record> records = RecordHelper.getRecordDao().queryBuilder()
                .where(RecordDao.Properties.Isupt.eq(1))
                .list();
        if (records == null || records.size() == 0) {
            return null;
        }
        return records.get(0);
    }

    private void uploadRecord(final Record record) {
        String url = getUploadRecordUrl(record);
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (ResponseHelper.isRight(response)) {
                                // 标识为已上传
                                record.setIsupt(0);
                                RecordHelper.getRecordDao().update(record);
                                // 是否有仍需要上传的数据
                                uploadRecordIfNeed();
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
        builder.appendQueryParameter(Constant.K_TEAM_ID, record.getTeamId());
        builder.appendQueryParameter(Constant.K_TEAM_NO, record.getTeamNo());
        builder.appendQueryParameter(Constant.K_MATCH_ID, record.getMatchId());
        builder.appendQueryParameter(Constant.K_USER_NO, record.getAccount());
        builder.appendQueryParameter(Constant.K_START_TIME, DateUtils.getDateString(record.getDepartTime()));
        if (record.getArriveTime() != null) {
            builder.appendQueryParameter(Constant.K_SET_TIME, DateUtils.getDateString(record.getArriveTime()));
            builder.appendQueryParameter(Constant.K_TIMELINE, getTimeline(record));
        }
        return builder.toString();
    }

    private static String getTimeline(Record record) {
        Double result = (record.getArriveTime() - record.getDepartTime()) * 1.d / 3600 / 1000;
        return result.toString().substring(0, 6);
    }

    private static void downloadRecord() {
        String url = getDownloadRecordsUrl(ConfigUtils.getAccount());
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

    private static String getDownloadRecordsUrl(final String userNo) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/downloadresult?").buildUpon();
        builder.appendQueryParameter(Constant.K_USER_NO, userNo);
        return builder.toString();
    }

    private static void copeDownloadRecordResponse(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        JSONArray data = response.getJSONArray(Constant.K_DATA);
        List<Record> records = new ArrayList<>();
        for (int i = 0; i != data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            String teamNo = item.getString(Constant.K_TEAM_NO);
            String userNo = item.getString(Constant.K_USER_NO);
            String startTime = item.getString(Constant.K_START_TIME);
            String setTime = item.getString(Constant.K_SET_TIME);
            Record record = RecordHelper.getRecordByTeamNo(teamNo);
            if (record != null) {
                record.setAccount(userNo);
                record.setDepartTime(dateStringToTime(startTime));
                record.setArriveTime(dateStringToTime(setTime));
                records.add(record);
            }
        }
        RecordHelper.getRecordDao().updateInTx(records);
        ToastUtils.show("同步完成");
    }

    private static Long dateStringToTime(String dateString) {
        if (dateString == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateString);
        return dateTime.getMillis();
    }


}
