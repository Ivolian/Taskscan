package com.unicorn.taskscan;

import com.unicorn.taskscan.base.ButterKnifeActivity;

import butterknife.OnClick;

public class RecordQueryActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_query;
    }

    @OnClick(R.id.back)
    public void backOnClick(){
        finish();
    }

        //
//    三个条件：
//
//    队伍编号，匹配teamno
//    线路，匹配编号前面的两个数字，比如输入01就left(编号,2)匹配
//    是否到达，判断结束时候是否有时间就行


}
