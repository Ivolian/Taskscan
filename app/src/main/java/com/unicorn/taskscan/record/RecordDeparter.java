package com.unicorn.taskscan.record;

import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.ToastUtils;

import java.util.Date;

public class RecordDeparter {

    /*
    扫码出发流程：
    1、读取二维码或一维条码，队伍号
    2、判断队伍号在本地是否有数据，如果没有数据，提示“未查询到队伍信息”
    3、如果有信息，update这条记录的的出发时间
    4、如果重复扫码，继续update这条记录的出发时间，按照之后时间计算
    5、update记录完成之后，将本记录的isupt字段改成 1
     */

    public void depart(final String teamNo) {
        Record record = RecordHelper.getRecordByTeamNo(teamNo);
        if (record == null) {
            ToastUtils.show("未查询到队伍信息");
            return;
        }
        if (record.getDepartTime() != null) {
            ToastUtils.show("队伍" + record.getTeamNo() + "已出发");
            return;
        }
        record.setDepartTime(new Date().getTime());
        record.setIsupt(1);
        record.setAccount(ConfigUtils.getAccount());
        RecordHelper.getRecordDao().update(record);
        ToastUtils.show("扫码出发完成");
    }

}
