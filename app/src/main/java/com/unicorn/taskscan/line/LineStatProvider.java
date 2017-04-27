package com.unicorn.taskscan.line;

import android.database.Cursor;

import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.record.DaoSession;
import com.unicorn.taskscan.record.RecordDao;
import com.unicorn.taskscan.record.RecordHelper;

import java.util.ArrayList;
import java.util.List;

public class LineStatProvider {

    public List<LineStat> provide() {
        RecordDao recordDao = RecordHelper.getRecordDao();
        List<String> lineNos = lineNos(SimpleApplication.getDaoSession());
        List<LineStat> lineStats = new ArrayList<>();
        for (String lineNo : lineNos) {
            long count = recordDao.queryBuilder()
                    .where(RecordDao.Properties.LineNo.eq(lineNo))
                    .count();
            long departCount = recordDao.queryBuilder()
                    .where(RecordDao.Properties.LineNo.eq(lineNo))
                    .where(RecordDao.Properties.DepartTime.isNotNull())
                    .count();
            long arriveCount = RecordHelper.getRecordDao().queryBuilder()
                    .where(RecordDao.Properties.LineNo.eq(lineNo))
                    .where(RecordDao.Properties.ArriveTime.isNotNull())
                    .count();
            lineStats.add(new LineStat(lineNo, count, departCount, arriveCount));
        }
        return lineStats;
    }

    private List<String> lineNos(DaoSession session) {
        List<String> result = new ArrayList<>();
        final String SQL = "SELECT DISTINCT " + RecordDao.Properties.LineNo.columnName +
                " FROM " + RecordDao.TABLENAME + " ORDER BY " + RecordDao.Properties.LineNo.columnName;
        Cursor cursor = session.getDatabase().rawQuery(SQL, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    result.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

}
