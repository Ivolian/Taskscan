package com.unicorn.taskscan;

import android.content.Intent;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.taskscan.base.PreparePrint;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.ToastUtils;
import com.unicorn.taskscan.volley.SimpleVolley;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import butterknife.OnClick;

public class MainActivity extends PreparePrint {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.queryRecord)
    public void queryRecordOnClick() {
        startActivity(RecordQueryActivity.class);
    }

    boolean isDepartScan;

    @OnClick(R.id.departScan)
    public void departScanOnClick() {
//        Intent intent = new Intent();
//        intent.setAction("com.woyou.aidlservice.IWoyouService");
//        intent.setPackage("com.woyou.aidlservice");
//        boolean result = bindService(intent, connection, Context.BIND_AUTO_CREATE);

//        scan();

//        downloadRecord();

        uploadRecord();
        isDepartScan = true;
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


    @OnClick(R.id.arriveScan)
    public void arriveScanOnClick() {
        downloadRecord();

//        scan();
        isDepartScan = false;
    }

    private void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String teamNo = result.getContents();
            if (isDepartScan) {
                onDepartScanFinish(teamNo);
            } else {
                onArriveScanFinish(teamNo);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onDepartScanFinish(final String teamNo) {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        Record record = recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
        if (record == null) {
            record = new Record();
            record.setAccount(ConfigUtils.getAccount());
            record.setTeamNo(teamNo);
            record.setLineNo(teamNo.substring(0, 2));
            record.setDepartTime(new Date().getTime());
            recordDao.insert(record);
            ToastUtils.show("出发扫码成功");
        } else {
            record.setDepartTime(new Date().getTime());
            recordDao.update(record);
            ToastUtils.show("出发扫码已更新");
        }
    }

    private void onArriveScanFinish(final String teamNo) {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        Record record = recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
        if (record == null) {
            ToastUtils.show("尚未出发扫码");
            return;
        }

        long arriveTime = new Date().getTime();
        record.setArriveTime(arriveTime);
        record.setDiff(arriveTime - record.getDepartTime());
        recordDao.update(record);

        ToastUtils.show("到达扫码成功");
        RecordHelper.print(record);
    }

    //

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
