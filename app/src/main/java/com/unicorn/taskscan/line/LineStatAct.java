package com.unicorn.taskscan.line;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;

import butterknife.BindView;
import butterknife.OnClick;

public class LineStatAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.act_line_stat;
    }

    @Override
    protected void init() {
        initRecyclerView();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    LineStatAdapter lineStatAdapter;

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lineStatAdapter = new LineStatAdapter());
        lineStatAdapter.setNewData(new LineStatProvider().provide());
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

}
