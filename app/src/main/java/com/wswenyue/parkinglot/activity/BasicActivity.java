package com.wswenyue.parkinglot.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.wswenyue.parkinglot.service.BackendService;

import static java.lang.System.currentTimeMillis;

public class BasicActivity extends Activity {

    BackendService backendService;
    private long exitTime = 0;

    public ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName arg0, IBinder service) {
            backendService = ((BackendService.InterBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName arg0) {

            backendService = null;
        }

    };

    @Override
    public void onBackPressed() {
        if((currentTimeMillis() - exitTime) > 2000){
            showMessageToast();
            exitTime = currentTimeMillis();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(BasicActivity.this, BackendService.class),
                serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void showMessageToast() {
        Toast.makeText(this, "再次按返回键退出", Toast.LENGTH_SHORT).show();
    }
}
