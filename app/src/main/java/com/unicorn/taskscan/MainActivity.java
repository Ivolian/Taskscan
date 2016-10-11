package com.unicorn.taskscan;

import android.content.Intent;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.ToastUtils;

import java.util.Date;

import butterknife.OnClick;

public class MainActivity extends ButterKnifeActivity {

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
        scan();
        isDepartScan = true;
    }

    @OnClick(R.id.arriveScan)
    public void arriveScanOnClick() {
        scan();
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


}
