package com.unicorn.taskscan;

import android.content.Intent;
import android.widget.ArrayAdapter;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.utils.Constant;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;

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
        Intent intent = new Intent(this, RecordDisplayActivity.class);
        intent.putExtra(Constant.K_TEAM_NO, getTeamNo());
        intent.putExtra(Constant.K_LINE_NO, getLineNo());
        intent.putExtra(Constant.K_IS_ARRIVAL, isArrival());
        startActivity(intent);
    }

    @BindView(R.id.metTeamNo)
    MaterialEditText metTeamNo;

    @BindView(R.id.metLineNo)
    MaterialEditText metLineNo;

    private String getTeamNo() {
        return metTeamNo.getText().toString().trim();
    }

    private String getLineNo() {
        return metLineNo.getText().toString().trim();
    }

    @BindView(R.id.mbsIsArrival)
    MaterialBetterSpinner mbsIsArrival;

    private void initMbsIsArrival() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Arrays.asList("是", "否"));
        mbsIsArrival.setAdapter(adapter);
        mbsIsArrival.setText("是");
    }

    private boolean isArrival() {
        return mbsIsArrival.getText().toString().equals("是");
    }

}
