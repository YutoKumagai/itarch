package com.example.yutokumagai.myservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("i","start service");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        if(IMyAidlInterface.class.getName().equals(intent.getAction())){
            // ISampleServiceの実装クラスのインスタンスを返す
            Log.i("i","hoge");
            return sampleSerciceIf;
        }
        return null;
    }

    private IMyAidlInterface.Stub sampleSerciceIf = new IMyAidlInterface.Stub(){
        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };
}
