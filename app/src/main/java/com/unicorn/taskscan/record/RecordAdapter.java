package com.unicorn.taskscan.record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.taskscan.R;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {


    // ================================== data ==================================

    private List<Record> records = new ArrayList<>();

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    // ================================== ViewHolder ==================================

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTeamNo)
        TextView tvTeamNo;

        @BindView(R.id.tvDepartTime)
        TextView tvDepartTime;

        @BindView(R.id.tvArriveTime)
        TextView tvArriveTime;

        @BindView(R.id.tvDiff)
        TextView tvDiff;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.btnPrint)
        public void btnPrintOnClick() {
            Record record = records.get(getAdapterPosition());
            RecordHelper.printRecord(record);
        }
    }


    // ================================== onBindViewHolder ==================================

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Record record = records.get(position);
        holder.tvTeamNo.setText(record.getTeamNo());
        Long departTime = record.getDepartTime();
        holder.tvDepartTime.setText("出发时间: " + getDateString(departTime));
        Long arriveTime = record.getArriveTime();
        if (arriveTime != null) {
            holder.tvArriveTime.setText("到达时间: " + getDateString(arriveTime));
            holder.tvDiff.setText("总计耗时: " + getDiff(departTime, arriveTime));
        }
    }

    private String getDateString(long time) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        return new DateTime(time).toString(format);
    }

    private String getDiff(long departTime, long arriveTime) {
        Interval interval = new Interval(departTime, arriveTime);
        Period period = interval.toPeriod();
        String diff = "";
        int hour = period.getHours();
        if (hour != 0) {
            diff += (hour + "小时");
        }
        int minute = period.getMinutes();
        if (minute != 0) {
            diff += (minute + "分钟");
        }
        int second = period.getSeconds();
        if (second != 0) {
            diff += (second + "秒");
        }
        return diff;
    }


    // ================================== 基本无视 ==================================

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }


}
