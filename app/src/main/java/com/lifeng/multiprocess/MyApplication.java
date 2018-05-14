package com.lifeng.multiprocess;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by happen on 2018-05-14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        Log.d("tchl", "MyApplication onCreate");
        Log.d("tchl", "MyApplication pid is " + pid);


        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    if (procInfo.processName.equals("com.lifeng.multiprocess")) {
                        Log.d("tchl", "process name is " + procInfo.processName);
                    } else if (procInfo.processName.equals("com.lifeng.multiprocess:remote")) {
                        Log.d("tchl", "process name is " + procInfo.processName);
                    }
                }
            }
        }
    }
}
