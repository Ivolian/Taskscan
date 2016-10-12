package com.unicorn.taskscan.base;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.unicorn.taskscan.utils.ToastUtils;
import com.woyou.aidlservice.IWoyouCallback;
import com.woyou.aidlservice.IWoyouService;


public abstract class PreparePrint extends ButterKnifeActivity {

    @Override
    protected void init() {
        //连接绑定
//        try {
//            Log.e("result", result + "");
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
    }


    private IWoyouService woyouService;

    private IWoyouCallback mCallback = new IWoyouCallback.Stub() {

        @Override
        public void onReturnValue(final String v) throws RemoteException {
            final String val = v;
            System.out.println("onReturnValue == " + val);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    Toast.makeText(getBaseContext(), "onReturnValue:" + val, 100).show();
//                    info.setText("result: "+val);

                    ToastUtils.show(v);
                }
            });
        }

        @Override
        public void onRaiseException(final int code, final String e) throws RemoteException {
            final String ex = e;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //操作失败
//                    String msg = "exception:" + ex;
                    //Toast.makeText(getBaseContext(), msg, 0).show();
//                    info.setText(msg);
                    ToastUtils.show(e);

                }
            });
        }

        @Override
        public void onSuccessOperation(final String msg) throws RemoteException {
            //操作成功
            runOnUiThread(new Runnable() {
                public void run() {
                    ToastUtils.show(msg);
//                    Toast.makeText(getBaseContext(), msg, 0).show();
//                    info.setText(msg);
                }
            });


        }
    };

    private static final int USB_PORT_DISPLAY = 1;

    protected ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("result", "服务连接断开");
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("result", "服务连接成功");
            woyouService = IWoyouService.Stub.asInterface(service);
            try {
                woyouService.registerCallback(mCallback);
//                serviceVersion = woyouService.getServiceVersion();

                //设置为USB接口的客显
                woyouService.setCDType(USB_PORT_DISPLAY);

            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e("result", e.getMessage());
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
