package com.wswenyue.parkinglot.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wswenyue.parkinglot.R;
import com.wswenyue.parkinglot.constant.Constant;
import com.wswenyue.parkinglot.service.BackendService;


public class MainActivity extends BasicActivity {

    private Button btOpen = null;
    private Button btXiuxi = null;
    private Button btYule = null;

    private ImageView mHome;

    private String uname = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btOpen = this.findViewById(R.id.bt_open);
        btXiuxi = this.findViewById(R.id.bt_xiuxi);
        btYule = this.findViewById(R.id.bt_yule);

        mHome = this.findViewById(R.id.img_my);

        getUserName();

        //新添代码，在代码中注册广播接收程序
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadCastSend);
        receiver = new BroadcastMain();
        registerReceiver(receiver, filter);



        //添加事件监听
        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsg(Constant.OPEN_IDENTIFER);
//                btOpen.setClickable(false);
            }
        });

        //添加事件监听
        btXiuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsg(Constant.OPEN02_IDENTIFER);
//                btXiuxi.setClickable(false);
            }
        });

        //添加事件监听
        btYule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsg(Constant.OPEN03_IDENTIFER);
//                btYule.setClickable(false);
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    public void getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("parkinglotInfo", 0);
        uname = sharedPreferences.getString("uname", "");
    }

    public void SendMsg(String cmd) {
        Message message = new Message();
        message.what = Constant.MSG_WHAT_SENDMSG;
        message.obj = Constant.CMD + "#" + uname + "#" + cmd;
        BackendService.revHandler.sendMessage(message);
    }


    BroadcastMain receiver;

    public class BroadcastMain extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String MsgStr = intent.getStringExtra("msg");
            Log.i("收到来自服务器的消息", MsgStr);

            if(!isLoginedUser(MsgStr)){
                showToastMessage("请登录后再操作");
                return;
            }
            if(MsgStr.equals(Constant.Authority_Not_Allowed)){
                showToastMessage("请进入停车场，再操作");
            }else if(MsgStr.equals(Constant.Server_CMD_Execution_Succeed)){
                showToastMessage("门已打开，请尽快通过");
            }else if(MsgStr.equals(Constant.Server_CMD_Repuat)){
                showToastMessage("您的指令正在执行，请勿重复。");
            }else if(MsgStr.equals(Constant.Authority_Area_Outside)){
                showToastMessage("请到指定区域操作");
            }
        }

        private boolean isLoginedUser(String msgStr) {
            return !msgStr.equals(Constant.Authority_Permission_denied);
        }
        private void showToastMessage(String 请登录后再操作) {
            Toast.makeText(MainActivity.this, 请登录后再操作, Toast.LENGTH_SHORT).show();
        }

    }

}