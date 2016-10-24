package com.unicorn.taskscan.record;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Record {
    
    @Id
    private String teamId;

    @NotNull
    private String teamName;

    @NotNull
    private String teamNo;

    @NotNull
    private String lineNo;

    @NotNull
    private String matchId;

    @NotNull
    private String matchName;

    @NotNull
    private Integer isupt;

    // 操作人
    private String account;

    // 出发时间
    private Long departTime;

    // 到达时间
    private Long arriveTime;

    @Generated(hash = 2050024337)
    public Record(String teamId, @NotNull String teamName, @NotNull String teamNo,
            @NotNull String lineNo, @NotNull String matchId,
            @NotNull String matchName, @NotNull Integer isupt, String account,
            Long departTime, Long arriveTime) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamNo = teamNo;
        this.lineNo = lineNo;
        this.matchId = matchId;
        this.matchName = matchName;
        this.isupt = isupt;
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

    public String getTeamId() {
        return this.teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getMatchId() {
        return this.matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchName() {
        return this.matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Integer getIsupt() {
        return this.isupt;
    }

    public void setIsupt(Integer isupt) {
        this.isupt = isupt;
    }

}
