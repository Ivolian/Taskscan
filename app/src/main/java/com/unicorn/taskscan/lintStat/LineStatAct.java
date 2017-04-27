package com.unicorn.taskscan.lintStat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recyclerView.setAdapter(lineStatAdapter = new LineStatAdapter());
        lineStatAdapter.setNewData(new LineStatProvider().provide());
        setOnItemClickListener();
    }

    private void setOnItemClickListener() {
        lineStatAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LineStat lineStat = (LineStat) adapter.getItem(position);
                // TODO: 2017/4/27
            }
        });
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

}
