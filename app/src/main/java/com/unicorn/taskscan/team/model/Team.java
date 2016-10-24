package com.unicorn.taskscan.team.model;

public class Team {

    /**
     * teamid : 06e477c0-a818-4165-9f98-33f09fec6359
     * teamname : 千里追踪战队
     * match_id : d3335b57-3835-46f9-2d5d-bf4e4c43fa00
     * match_name : 冲刺上海·天翼城市定向赛
     */

    private String teamid;
    private String teamname;
    private String match_id;
    private String match_name;

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

}
