package com.unicorn.taskscan.lintStat;

import android.support.v4.content.ContextCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unicorn.taskscan.R;

public class LineStatAdapter extends BaseQuickAdapter<LineStat, BaseViewHolder> {

    public LineStatAdapter() {
        super(R.layout.item_line_stat);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineStat lineStat) {
        helper.setImageDrawable(R.id.ivLineNo, TextDrawable.builder()
                .buildRoundRect(lineStat.getLineNo(), ContextCompat.getColor(mContext, R.color.colorPrimary), 15));
        String text = "队伍总数:" + lineStat.getCount() + "  已出发:" + lineStat.getDepartCount()
                + "  已到达:" + lineStat.getArriveCount();
        helper.setText(R.id.tvText, text);
    }

}
