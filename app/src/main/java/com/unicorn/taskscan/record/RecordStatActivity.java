package com.unicorn.taskscan.record;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.ButterKnifeActivity;
import com.unicorn.taskscan.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordStatActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_record_stat;
    }

    @Override
    protected void init() {
        initQueryButton();
    }

    @OnClick(R.id.btnQuery)
    public void btnQueryOnClick() {
        Intent intent = new Intent(this, RecordStatDisplayActivity.class);
        intent.putExtra(Constant.K_LINE_NO, getLineNo());
        startActivity(intent);
    }


    // ==================== 基本无视 ====================

    @BindView(R.id.metLineNo)
    MaterialEditText metLineNo;

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

    private String getLineNo() {
        return metLineNo.getText().toString().trim();
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }


}
