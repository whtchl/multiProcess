package com.lifeng.multiprocess;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class ProcessTestService extends Service {
    public ProcessTestService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(isLocalAppProcess("com.lifeng.multiprocess")){
            Log.i("tchl service","  是当前应用主进程");
        }else{
            Log.i("tchl service","不是当前应用主进程");
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.




        Log.d("tchl","sercice uid："+  android.os.Process.myUid());
        throw new UnsupportedOperationException("Not yet implemented");
    }

     //如何判断是当前应用主进程
    private boolean isLocalAppProcess(String packName) {
        int myPid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo myProcess = null;
        List runningProcesses = activityManager.getRunningAppProcesses();
        if (runningProcesses != null) {
            Iterator var11 = runningProcesses.iterator();
            while (var11.hasNext()) {
                ActivityManager.RunningAppProcessInfo process = (ActivityManager.RunningAppProcessInfo) var11.next();
                Log.i("tchl", "process=" + process.processName + " pid=" + process.pid);
                if (process.pid == myPid) {
                    myProcess = process;
                    break;
                }
            }
        }

        if (myProcess == null) {
            Log.i("tchl", "Could not find running process for %d" + myPid);
            return false;
        } else {
            return myProcess.processName.equals(packName);
        }
    }
}
