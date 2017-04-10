package com.unicorn.taskscan.record;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unicorn.taskscan.R;

import org.joda.time.Interval;
import org.joda.time.Period;

public class StatAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {

    public StatAdapter() {
        super(R.layout.item_stat);
    }

    @Override
    protected void convert(BaseViewHolder helper, Record record) {
        if (record.getDepartTime() == null || record.getArriveTime() == null) {
            return;
        }
        Interval interval = new Interval(record.getDepartTime(), record.getArriveTime());
        Period period = interval.toPeriod();
        String usedTime = period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds();
        helper.setText(R.id.tvTeamNo, usedTime);
    }

}
