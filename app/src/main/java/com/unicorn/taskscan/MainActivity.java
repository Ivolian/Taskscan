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

    boolean isDepartScan;

    @OnClick(R.id.departScan)
    public void departScanOnClick() {
        new IntentIntegrator(this).initiateScan();
        isDepartScan = true;
    }

    @OnClick(R.id.arriveScan)
    public void arriveScanOnClick() {
        new IntentIntegrator(this).initiateScan();
        isDepartScan = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
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
            // 创建记录
            record = new Record();
            record.setAccount(ConfigUtils.getAccount());
            record.setTeamNo(teamNo);
            record.setDepartTime(new Date().getTime());
            recordDao.insert(record);
        } else {
            // 更新出发时间
            record.setDepartTime(new Date().getTime());
            recordDao.update(record);
        }
    }

    private void onArriveScanFinish(final String teamNo) {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        Record record = recordDao.queryBuilder()
                .where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()))
                .unique();
        if (record == null) {
            ToastUtils.show("请先出发扫码");
            return;
        }

        long arriveTime = new Date().getTime();
        record.setArriveTime(arriveTime);
        record.setDiff(arriveTime - record.getDepartTime());
        recordDao.update(record);

        printRecord(record);
    }

    /*
    打印格式：
    队伍编号：
    出发时间：
    结束时间：
    总计耗时：
     */
    private void printRecord(Record record) {
        // todo
    }

    /*
    hour = time / 3600;
minute = time / 60 % 60;
second = time % 60;
     */

}
