package com.unicorn.taskscan.menu;

class Menu {

    public static final String DEPART_SCAN = "出发扫码";
    public static final String DEPART_INPUT = "出发输入";
    public static final String ARRIVE_SCAN = "到达扫码";
    public static final String ARRIVE_INPUT = "到达输入";
    public static final String MATCH_DOWNLOAD = "比赛下载";
    public static final String RECORD_SYNC = "数据同步";
    public static final String RECORD_QUERY = "成绩查询";
    public static final String CLEAR_RECORD = "清除数据";
    public static final String RECORD_STAT = "成绩统计";

    //

    private String text;

    private int icon;

    Menu(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public int getIcon() {
        return icon;
    }

}
