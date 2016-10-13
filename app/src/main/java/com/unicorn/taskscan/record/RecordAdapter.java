package com.unicorn.taskscan.record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.taskscan.R;
import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.utils.DateUtils;
import com.unicorn.taskscan.utils.ToastUtils;

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
            if (record.getArriveTime() == null) {
                ToastUtils.show("尚未到达扫码");
                return;
            }
            WoyouPrinter.printRecord(record);
        }
    }


    // ================================== onBindViewHolder ==================================

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Record record = records.get(position);
        holder.tvTeamNo.setText(record.getTeamNo());
        holder.tvDepartTime.setText("出发时间: " + DateUtils.getDateString(record.getDepartTime()));
        Long arriveTime = record.getArriveTime();
        if (arriveTime != null) {
            holder.tvArriveTime.setText("到达时间: " + DateUtils.getDateString(arriveTime));
            holder.tvDiff.setText("总计耗时: " + DateUtils.getDiff(record));
        }
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
