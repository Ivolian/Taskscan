package com.unicorn.taskscan.record;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.utils.Constant;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordQueryAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_query;
    }

    @Override
    protected void init() {
        initMbsIsArrival();
        initQueryButton();
    }

    private void initMbsIsArrival() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Arrays.asList("是", "否"));
        mbsIsArrival.setAdapter(adapter);
        mbsIsArrival.setText("是");
    }

    @OnClick(R.id.btnQuery)
    public void btnQueryOnClick() {
        Intent intent = new Intent(this, RecordDisplayAct.class);
        intent.putExtra(Constant.K_TEAM_NO, getTeamNo());
        intent.putExtra(Constant.K_LINE_NO, getLineNo());
        intent.putExtra(Constant.K_IS_ARRIVAL, getIsArrival());
        startActivity(intent);
    }


    // ==================== 基本无视 ====================

    @BindView(R.id.metTeamNo)
    MaterialEditText metTeamNo;

    @BindView(R.id.metLineNo)
    MaterialEditText metLineNo;

    @BindView(R.id.mbsIsArrival)
    MaterialBetterSpinner mbsIsArrival;

    @BindView(R.id.btnQuery)
    TextView btnQuery;

    private void initQueryButton() {
        btnQuery.setBackground(getQueryButtonBackground());
    }

    private Drawable getQueryButtonBackground() {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bg.setCornerRadius(10);
        return bg;
    }

    private String getTeamNo() {
        return metTeamNo.getText().toString().trim();
    }

    private String getLineNo() {
        return metLineNo.getText().toString().trim();
    }

    private String getIsArrival() {
        return mbsIsArrival.getText().toString();
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }


}
