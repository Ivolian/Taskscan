package com.unicorn.taskscan.record.data;

import android.widget.TextView;

import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.record.RecordDao;
import com.unicorn.taskscan.record.RecordHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class DataStatAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_data_stat;
    }

    @BindView(R.id.tvRecordNum)
    TextView tvRecordNum;

    @BindView(R.id.tvDepartNum)
    TextView tvDepartNum;

    @BindView(R.id.tvArriveNum)
    TextView tvArriveNum;

    @Override
    protected void init() {
        long recordNum = RecordHelper.getRecordDao().count();
        tvRecordNum.setText("已下载" + recordNum + "条记录");
        int departNum = RecordHelper.getRecordDao().queryBuilder()
                .where(RecordDao.Properties.DepartTime.isNotNull())
                .list()
                .size();
        tvDepartNum.setText("已出发" + departNum + "条记录");
        int arriveNum = RecordHelper.getRecordDao().queryBuilder()
                .where(RecordDao.Properties.ArriveTime.isNotNull())
                .list()
                .size();
        tvArriveNum.setText("已到达" + arriveNum + "条记录");

    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

}
