package com.unicorn.taskscan;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.f2prateek.dart.InjectExtra;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.record.RecordArriver;
import com.unicorn.taskscan.record.RecordDeparter;
import com.unicorn.taskscan.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamNoInputAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_team_no_input;
    }

    @Override
    protected void init() {
        tvTitle.setText(isDepart ? "出发输入" : "到达输入");
        initButton();
    }

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @InjectExtra("isDepart")
    boolean isDepart;

    @OnClick(R.id.btnConfirm)
    public void confirmOnClick() {
        if (getTeamNo().equals("")) {
            ToastUtils.show("队伍编号不能为空");
            return;
        }
        if (isDepart) {
            new RecordDeparter().depart(getTeamNo());
        } else {
            new RecordArriver().arrive(getTeamNo());
        }
    }


    // ==================== 基本无视 ====================

    @BindView(R.id.etTeamNo)
    MaterialEditText etTeamNo;

    @BindView(R.id.btnConfirm)
    TextView btnConfirm;

    private void initButton() {
        btnConfirm.setBackground(getButtonBackground());
    }

    private Drawable getButtonBackground() {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bg.setCornerRadius(10);
        return bg;
    }

    private String getTeamNo() {
        return etTeamNo.getText().toString().trim();
    }

    @OnClick(R.id.back)
    public void backOnClick() {
        finish();
    }

}
