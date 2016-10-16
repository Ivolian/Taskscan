package com.unicorn.taskscan.record;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Record {

    // 队伍编号
    @Id
    private String teamNo;

    // 线路编号
    @NotNull
    private String lineNo;

    // 操作人
    @NotNull
    private String account;

    // 出发时间
    @NotNull
    private Long departTime;

    // 到达时间
    private Long arriveTime;

    @Generated(hash = 2108704887)
    public Record(String teamNo, @NotNull String lineNo, @NotNull String account,
            @NotNull Long departTime, Long arriveTime) {
        this.teamNo = teamNo;
        this.lineNo = lineNo;
        this.account = account;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
    }

    @Generated(hash = 477726293)
    public Record() {
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTeamNo() {
        return this.teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public String getLineNo() {
        return this.lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public Long getDepartTime() {
        return this.departTime;
    }

    public void setDepartTime(Long departTime) {
        this.departTime = departTime;
    }

    public Long getArriveTime() {
        return this.arriveTime;
    }

    public void setArriveTime(Long arriveTime) {
        this.arriveTime = arriveTime;
    }

}
