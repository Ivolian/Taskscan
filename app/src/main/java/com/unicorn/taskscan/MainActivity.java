package com.unicorn.taskscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.record.RecordArriver;
import com.unicorn.taskscan.record.RecordCleaner;
import com.unicorn.taskscan.record.RecordDeparter;
import com.unicorn.taskscan.record.RecordDownloader;
import com.unicorn.taskscan.record.RecordQueryActivity;

import butterknife.OnClick;

public class MainActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }


    // =================== onClick ===================

    @OnClick(R.id.downloadRecord)
    public void downloadRecordOnClick() {
        RecordDownloader recordDownloader = new RecordDownloader(this);
        recordDownloader.downloadRecord();
    }

    @OnClick(R.id.clearRecord)
    public void clearRecordOnClick() {
        RecordCleaner recordCleaner = new RecordCleaner(this);
        recordCleaner.showCleanConfirm();
    }

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

    @OnClick(R.id.queryRecord)
    public void queryRecordOnClick() {
        startActivity(RecordQueryActivity.class);
    }

    @OnClick(R.id.uploadRecord)
    public void uploadRecordOnClick() {
        // TODO
    }


    // =================== scan ===================

    private boolean isDepartScan;

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
        RecordDeparter recordDeparter = new RecordDeparter();
        recordDeparter.depart(teamNo);
    }

    private void onArriveScanFinish(final String teamNo) {
        RecordArriver recordArriver = new RecordArriver();
        recordArriver.arrive(teamNo);
    }


    // =================== onCreate & onDestroy ===================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WoyouPrinter.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WoyouPrinter.destroy(this);
    }


}
