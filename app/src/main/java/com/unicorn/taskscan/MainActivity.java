package com.unicorn.taskscan;

import android.content.Intent;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.record.Record;
import com.unicorn.taskscan.record.RecordDao;
import com.unicorn.taskscan.record.RecordHelper;
import com.unicorn.taskscan.record.RecordQueryActivity;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.ToastUtils;

import java.util.Date;

import butterknife.OnClick;

public class MainActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.uploadRecord)
    public void uploadRecordOnClick() {
        RecordHelper.uploadRecord();
    }

    @OnClick(R.id.queryRecord)
    public void queryRecordOnClick() {
        startActivity(RecordQueryActivity.class);
    }

    boolean isDepartScan;

    @OnClick(R.id.departScan)
    public void departScanOnClick() {
        isDepartScan = true;
        scan();
    }

    @OnClick(R.id.arriveScan)
    public void arriveScanOnClick() {
        isDepartScan = false;
        scan();
    }

    private void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            onScanFinish(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onScanFinish(final String teamNo) {
        if (isDepartScan) {
            onDepartScanFinish(teamNo);
        } else {
            onArriveScanFinish(teamNo);
        }
    }

    private void onDepartScanFinish(final String teamNo) {
        Record record = new Record();
        record.setAccount(ConfigUtils.getAccount());
        record.setTeamNo(teamNo);
        record.setLineNo(getLineNo(teamNo));
        record.setDepartTime(new Date().getTime());
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        recordDao.insertOrReplace(record);
        ToastUtils.show("出发扫码完成");
    }

    private String getLineNo(final String teamNo) {
        return teamNo.substring(0, 2);
    }

    private void onArriveScanFinish(final String teamNo) {
        Record record = RecordHelper.getRecord();
        if (record == null) {
            ToastUtils.show("尚未出发扫码");
            return;
        }
        if (!record.getTeamNo().equals(teamNo)) {
            ToastUtils.show("与出发扫码不一致");
            return;
        }
        record.setArriveTime(new Date().getTime());
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        recordDao.update(record);
        ToastUtils.show("到达扫码成功");
        WoyouPrinter.printRecord(record);
    }

}
