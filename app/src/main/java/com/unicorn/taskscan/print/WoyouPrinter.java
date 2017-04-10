package com.unicorn.taskscan.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.unicorn.taskscan.record.Record;
import com.unicorn.taskscan.utils.DateUtils;
import com.unicorn.taskscan.utils.ToastUtils;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;


public class WoyouPrinter {

    private static IWoyouService woyouService = null;

    private static final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
            ToastUtils.show("已断开打印服务");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
            ToastUtils.show("成功连接打印服务");
        }
    };

    private static final ICallback callback = new ICallback.Stub() {
        @Override
        public void onRunResult(final boolean success) throws RemoteException {
        }

        @Override
        public void onReturnString(final String value) throws RemoteException {
        }

        @Override
        public void onRaiseException(int code, final String msg) throws RemoteException {
        }
    };

    public static void init(Context context) {
        Intent intent = new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public static void destroy(Context context) {
        context.unbindService(connection);
    }

    public static void printRecord(final Record record) {
        if (woyouService == null) {
            ToastUtils.show("尚未连接打印服务");
            return;
        }
        if (record == null || record.getArriveTime() == null) {
            return;
        }
        printRecord_(record);
    }

    private static void printRecord_(final Record record) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    woyouService.setFontSize(24, callback);
                    woyouService.setAlignment(0, callback);
                    woyouService.printText(record.getMatchName() + "\n", callback);
                    woyouService.printText("----------------------------" + "\n", callback);
                    woyouService.printText("队伍编号：" + record.getTeamNo() + "\n", callback);
                    woyouService.printText("队伍名称：" + record.getTeamName() + "\n", callback);
                    woyouService.printText("出发时间：" + DateUtils.getDateString(record.getDepartTime()) + "\n", callback);
                    woyouService.printText("到达时间：" + DateUtils.getDateString(record.getArriveTime()) + "\n", callback);
                    woyouService.printText("总计耗时：" + DateUtils.getDiff(record) + "\n", callback);
                    woyouService.lineWrap(4, callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
