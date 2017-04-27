package com.unicorn.taskscan.line;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unicorn.taskscan.R;

public class LineStatAdapter extends BaseQuickAdapter<LineStat, BaseViewHolder> {

    public LineStatAdapter() {
        super(R.layout.item_line_stat);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineStat lineStat) {
        helper.setText(R.id.tvLineNo, lineStat.getLineNo());
//        if (record.getDepartTime() == null || record.getArriveTime() == null) {
//            return;
//        }
//
//        helper.setText(R.id.tvTeamName, (helper.getAdapterPosition() + 1) + "." + record.getTeamName());
//        Interval interval = new Interval(record.getDepartTime(), record.getArriveTime());
//        Period period = interval.toPeriod();
//        helper.setText(R.id.tvUsedTime,  periodFormatter.print(period));
    }

}
