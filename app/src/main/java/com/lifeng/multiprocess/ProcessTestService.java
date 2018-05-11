package com.lifeng.multiprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ProcessTestService extends Service {
    public ProcessTestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        Log.d("tchl","sercice uid："+  android.os.Process.myUid());
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
