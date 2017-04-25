package com.unicorn.taskscan.record;

import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.team.model.Team;

import java.util.ArrayList;
import java.util.List;

public class RecordHelper {


    // ======================== 下载队伍后 ========================

    public static void saveTeam(List<Team> teams) {
        List<Record> records = new ArrayList<>();
        for (Team team : teams) {
            records.add(teamToRecord(team));
        }
        getRecordDao().insertInTx(records);
    }

    private static Record teamToRecord(final Team team) {
        Record record = new Record();
        record.setTeamId(team.getTeamid());
        record.setTeamName(team.getTeamname());
        record.setTeamNo(team.getTeamno());
        record.setLineNo(team.getLine_no());
        record.setMatchId(team.getMatch_id());
        record.setMatchName(team.getMatch_name());
        // 0 表示不需要上传
        record.setIsupt(0);
        return record;
    }


    // ======================== getRecordByTeamNo ========================

    public static Record getRecordByTeamNo(final String teamNo) {
        return getRecordDao().queryBuilder().where(RecordDao.Properties.TeamNo.eq(teamNo)).unique();
    }


    // ======================== getRecordDao ========================

    public static RecordDao getRecordDao() {
        return SimpleApplication.getDaoSession().getRecordDao();
    }

    public static List<Record> top10ByLineNo(final String lineNo) {
        RecordDao recordDao = SimpleApplication.getDaoSession().getRecordDao();
        List<Record> records = recordDao.queryBuilder()
                .where(RecordDao.Properties.UsedTime.isNotNull())
                .where(RecordDao.Properties.LineNo.eq(lineNo))
                .orderAsc(RecordDao.Properties.UsedTime)
                .limit(10)
                .list();
        return records;
    }


}
