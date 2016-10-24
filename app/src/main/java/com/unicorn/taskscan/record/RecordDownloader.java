package com.unicorn.taskscan.record;

import android.app.Activity;

import com.unicorn.taskscan.match.MatchDownloader;
import com.unicorn.taskscan.utils.ToastUtils;

public class RecordDownloader {

      /*
    比赛下载流程：
    第一步 判断本地数据库，是否有数据信息，如果有提示“已有比赛信息，请清除数据之后再下载”
    第二步 选择需要下载的比赛信息，如果没有查询到，返回未查询到比赛信息
    第三步 选择完比赛信息，显示进度读取条，正在下载比赛信息
    第四步 下载完成，显示比赛名称和下载队伍数量
     */

    // =================== constructor ===================

    private Activity mActivity;

    public RecordDownloader(Activity mActivity) {
        this.mActivity = mActivity;
    }


    // =================== downloadRecord ===================

    public void downloadRecord() {
        if (isRecordEmpty()) {
            MatchDownloader matchDownloader = new MatchDownloader(mActivity);
            matchDownloader.downloadMatch();
        } else {
            ToastUtils.show("已有比赛信息，请清除数据之后再下载");
        }
    }

    private boolean isRecordEmpty() {
        return RecordHelper.getRecordDao().count() == 0;
    }


}
