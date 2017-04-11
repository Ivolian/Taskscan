package com.unicorn.taskscan.record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.f2prateek.dart.InjectExtra;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordStatDisplayAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_stat_display;
    }

    @InjectExtra(Constant.K_LINE_NO)
    String lineNo;

    @Override
    protected void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StatAdapter statAdapter = new StatAdapter();
        recyclerView.setAdapter(statAdapter);
        statAdapter.setNewData(RecordHelper.top10ByLineNo(lineNo));
    }


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }


    @OnClick(R.id.print)
    public void printOnClick(){
        WoyouPrinter.printTop10(RecordHelper.top10ByLineNo(lineNo));
    }

}
