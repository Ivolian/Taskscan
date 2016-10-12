package com.unicorn.taskscan.record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.f2prateek.dart.InjectExtra;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.utils.Constant;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordDisplayActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_display;
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

    @Override
    protected void init() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<Record> recordList = queryRecords();
        RecordAdapter recordAdapter = new RecordAdapter();
        recordAdapter.setRecordList(recordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recordAdapter);
    }

    private List<Record> queryRecords() {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        QueryBuilder<Record> queryBuilder = recordDao.queryBuilder();
        if (!teamNo.equals("")) {
            queryBuilder.where(RecordDao.Properties.TeamNo.eq(teamNo));
        }
        if (!lineNo.equals("")) {
            queryBuilder.where(RecordDao.Properties.LineNo.like(lineNo));
        }
        if (isArrival) {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNotNull());
        } else {
            queryBuilder.where(RecordDao.Properties.ArriveTime.isNull());
        }
        return queryBuilder.list();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectExtra(Constant.K_TEAM_NO)
    String teamNo;

    @InjectExtra(Constant.K_LINE_NO)
    String lineNo;

    @InjectExtra(Constant.K_IS_ARRIVAL)
    Boolean isArrival;

}


