package com.unicorn.taskscan.record;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.utils.ToastUtils;

public class RecordCleaner {

    /*
        数据清除流程：
        1、对话框是否需要清除本地数据？；
        2、如果选择是，判断是否本地有isupt=1的数据，如果有提示“仍有未上传数据，清上传后再进行清除”；
        3、如无未上传数据，进行本地数据清空，显示“本地数据已清除”；
     */

    // =================== constructor ===================

    private Activity mActivity;

    public RecordCleaner(Activity mActivity) {
        this.mActivity = mActivity;
    }


    // =================== showCleanConfirm ===================

    public void showCleanConfirm() {
        new MaterialDialog.Builder(mActivity)
                .title("确认清除本地数据？")
                .positiveText("确认")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        checkIsuptRecord();
                    }
                })
                .show();
    }

    private void checkIsuptRecord() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        long count = recordDao.queryBuilder().where(RecordDao.Properties.Isupt.eq(1)).count();
        if (count != 0) {
            ToastUtils.show("仍有未上传数据，清上传后再进行清除");
        } else {
            clearRecord();
        }
    }

    private void clearRecord() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        recordDao.deleteAll();
        ToastUtils.show("本地数据已清除");
    }

}
