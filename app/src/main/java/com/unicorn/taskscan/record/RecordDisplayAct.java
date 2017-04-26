package com.unicorn.taskscan.record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.f2prateek.dart.InjectExtra;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.utils.Constant;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordDisplayAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_display;
    }

    @Override
    protected void init() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<Record> records = queryRecords();
        RecordAdapter recordAdapter = new RecordAdapter();
        recordAdapter.setRecords(records);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordAdapter);
    }

    private List<Record> queryRecords() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        QueryBuilder<Record> queryBuilder = recordDao.queryBuilder();
//        queryBuilder.where(RecordDao.Properties.Account.eq(ConfigUtils.getAccount()));
        if (!teamNo.equals(Constant.EMPTY_STR)) {
            queryBuilder.where(RecordDao.Properties.TeamNo.eq(teamNo));
        }
        if (!lineNo.equals(Constant.EMPTY_STR)) {
            queryBuilder.where(RecordDao.Properties.LineNo.eq(lineNo));
        }
        if (isArrival.equals(Constant.ARRIVAL)) {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNotNull());
        } else {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNull());
        }
        queryBuilder.where(RecordDao.Properties.DepartTime.isNotNull());
        queryBuilder.orderAsc(RecordDao.Properties.TeamNo);
        return queryBuilder.list();
    }


    // ==================== 基本无视 ====================

    @InjectExtra(Constant.K_TEAM_NO)
    String teamNo;

    @InjectExtra(Constant.K_LINE_NO)
    String lineNo;

    @InjectExtra(Constant.K_IS_ARRIVAL)
    String isArrival;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }


}


