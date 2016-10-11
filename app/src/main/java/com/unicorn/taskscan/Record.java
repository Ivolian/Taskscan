package com.unicorn.taskscan;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Record {

    @Id(autoincrement = true)
    private Long id;

    // 操作人
    @NotNull
    private String account;

    // 队伍编号
    @NotNull
    private String teamNo;

    // 线路编号
    @NotNull
    private String lineNo;

    // 出发时间
    @NotNull
    private Long departTime;

    // 到达时间
    private Long arriveTime;

    // 成绩时间：(到达时间-出发时间)
    private Long diff;

    @Generated(hash = 2050397800)
    public Record(Long id, @NotNull String account, @NotNull String teamNo,
            @NotNull String lineNo, @NotNull Long departTime, Long arriveTime,
            Long diff) {
        this.id = id;
        this.account = account;
        this.teamNo = teamNo;
        this.lineNo = lineNo;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.diff = diff;
    }

    @Generated(hash = 477726293)
    public Record() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDiff() {
        return this.diff;
    }

    public void setDiff(Long diff) {
        this.diff = diff;
    }




   

}
