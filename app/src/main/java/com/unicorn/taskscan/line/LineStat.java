package com.unicorn.taskscan.line;

public class LineStat {

    private String lineNo;

    private Long count;

    private Long departCount;

    private Long arriveCount;

    public LineStat(String lineNo, Long count, Long departCount, Long arriveCount) {
        this.lineNo = lineNo;
        this.count = count;
        this.departCount = departCount;
        this.arriveCount = arriveCount;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getDepartCount() {
        return departCount;
    }

    public void setDepartCount(Long departCount) {
        this.departCount = departCount;
    }

    public Long getArriveCount() {
        return arriveCount;
    }

    public void setArriveCount(Long arriveCount) {
        this.arriveCount = arriveCount;
    }
}
