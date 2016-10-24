package com.unicorn.taskscan.record;

import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.ToastUtils;

import java.util.Date;

public class RecordArriver {

    /*
    扫码到达流程：
    1、读取二维码或一维条码，队伍号
    2、判断队伍号在本地是否有数据，如果没有数据，提示“未查询到队伍信息”；
    3、判断本地记录是否有出发数据，如果没有数据，提示“未查询到出发信息”；
    4、如果有信息，update这条记录的的到达时间，同时计算时间间距，update时间间距字段；
    5、如果重复扫码，继续update这条记录的到达时间，按照之后时间计算；
    6、update记录完成之后，将本记录的isupt字段改成 1；
     */

    public void arrive(final String teamNo){
        Record record = RecordHelper.getRecordByTeamNo(teamNo);
        if (record == null){
            ToastUtils.show("未查询到队伍信息");
            return;
        }
        if (record.getDepartTime() == null){
            ToastUtils.show("未查询到出发信息");
            return;
        }
        record.setArriveTime(new Date().getTime());
        record.setIsupt(1);
        record.setAccount(ConfigUtils.getAccount());
        RecordHelper.getRecordDao().update(record);
        ToastUtils.show("到达扫码成功");

        WoyouPrinter.printRecord(record);
    }

}
