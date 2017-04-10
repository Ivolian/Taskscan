package com.unicorn.taskscan.record;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unicorn.taskscan.R;

import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class StatAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {

    public StatAdapter() {
        super(R.layout.item_stat);
    }

    PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .printZeroAlways()
            .minimumPrintedDigits(2)
            .appendHours()
            .appendSeparator(":")
            .minimumPrintedDigits(2)
            .appendMinutes()
            .appendSeparator(":")
            .minimumPrintedDigits(2)
            .appendSeconds()
            .toFormatter();

    @Override
    protected void convert(BaseViewHolder helper, Record record) {
        if (record.getDepartTime() == null || record.getArriveTime() == null) {
            return;
        }

        helper.setText(R.id.tvTeamName, (helper.getAdapterPosition() + 1) + "." + record.getTeamName());
        Interval interval = new Interval(record.getDepartTime(), record.getArriveTime());
        Period period = interval.toPeriod();
        helper.setText(R.id.tvUsedTime,  periodFormatter.print(period));
    }

}
