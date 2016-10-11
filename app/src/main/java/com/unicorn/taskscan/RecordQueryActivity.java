package com.unicorn.taskscan;

import android.widget.ArrayAdapter;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordQueryActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_query;
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

    @Override
    protected void init() {
        initMbsIsArrival();
    }

    @OnClick(R.id.btnQuery)
    public void btnQueryOnClick() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        QueryBuilder<Record> queryBuilder = recordDao.queryBuilder();
        String teamNo = getTeamNo();
        if (!teamNo.equals("")) {
            queryBuilder.where(RecordDao.Properties.TeamNo.eq(teamNo));
        }
        String lineNo = getLineNo();
        if (!lineNo.equals("")) {
            queryBuilder.where(RecordDao.Properties.LineNo.like(lineNo));
        }
        if (isArrival()) {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNotNull());
        } else {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNull());
        }
        List<Record> recordList = queryBuilder.list();
        // TODO
    }



    //
//    三个条件：
//
//    线路，匹配编号前面的两个数字，比如输入01就left(编号,2)匹配
//    是否到达，判断结束时候是否有时间就行


    @BindView(R.id.metTeamNo)
    MaterialEditText metTeamNo;


    @BindView(R.id.metLineNo)
    MaterialEditText metLineNo;

    @BindView(R.id.mbsIsArrival)
    MaterialBetterSpinner mbsIsArrival;

    private void initMbsIsArrival() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Arrays.asList("是", "否"));
        mbsIsArrival.setAdapter(adapter);
        mbsIsArrival.setText("是");
    }

    private String getTeamNo() {
        return metTeamNo.getText().toString().trim();
    }

    private String getLineNo() {
        return metLineNo.getText().toString().trim();
    }

    private boolean isArrival() {
        return mbsIsArrival.getText().toString().equals("是");
    }

}
