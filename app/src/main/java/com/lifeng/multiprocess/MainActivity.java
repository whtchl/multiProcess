package com.lifeng.multiprocess;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button  = (Button) findViewById(R.id.button);
        //绑定activity
        Log.d("tchl","activity的UID："+  android.os.Process.myUid());
        Intent intent = new Intent(MainActivity.this, ProcessTestService.class);
        startService(intent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isServiceExisted("com.lifeng.multiprocess:remote")){
                    Log.d("tchl","@@@@@@@@@@@：");
                    Intent intent = new Intent(MainActivity.this, ProcessTestService.class);
                    startService(intent);
                }

                if(!isUIProcess()){
                    Log.d("tchl","进程运行中");

                }else{
                    Log.d("tchl","进程打开");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    /**
     * 判断service是否已经运行
     * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
     * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
     * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
     *
     * @param className Service的全名,例如PushService.class.getName()
     * @return true:Service已运行 false:Service未运行
     */
    public boolean isServiceExisted(String className) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = android.os.Process.myUid();

        for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
            Log.d("tchl","classname:"+ runningServiceInfo.service.getClassName()+ " "+ runningServiceInfo.uid);
            if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {

                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否在主进程,这个方法判断进程名或者pid都可以,如果进程名一样那pid肯定也一样
     *
     * @return true:当前进程是主进程 false:当前进程不是主进程
     */
    public boolean isUIProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        mainProcessName = mainProcessName+":remote";
        int myPid = android.os.Process.myPid();
        Log.d("tchl", "mypid"+myPid);
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            Log.d("tchl", "pid"+info.pid +"  processName:"+info.processName);
            if (mainProcessName.equals(info.processName)) {

                return true;
            }
        }
        return false;
    }

}
