package com.wswenyue.parkinglot.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wswenyue.parkinglot.R;
import com.wswenyue.parkinglot.constant.Constant;
import com.wswenyue.parkinglot.domain.UserLoginInfo;
import com.wswenyue.parkinglot.service.MyService;

import static com.wswenyue.parkinglot.domain.UserLoginInfo.createUserLoginInfo;

public class ResetPasswdActivity extends Activity {
    private EditText newPasswd, confirmPasswd, phone, email;
    //定义界面上的按钮
    private Button reset;

    public ResetPasswdActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwd);

        //获取界面上的两个文本框
        newPasswd = (EditText) findViewById(R.id.passwordView);
        confirmPasswd = (EditText) findViewById(R.id.confirmPasswdView);
        phone = (EditText) findViewById(R.id.phoneView);
        email = (EditText) findViewById(R.id.emailView);
        //获取界面上的按钮
        reset = (Button) findViewById(R.id.resetPasswd_bt);

        receiver = new BroadcastMain();
        //新添代码，在代码中注册广播接收程序
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadCastSend);
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    public void resetPasswd(View v) {
        String newPassword = newPasswd.getText().toString().trim();
        String confirmedPassword = confirmPasswd.getText().toString().trim();
        String phoneNumber = phone.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        UserLoginInfo userLoginInfo = createUserLoginInfo(newPassword, confirmedPassword, phoneNumber, email);
        //判断是否为空
        if (!userLoginInfo.isValid()) {
            showMessageToast("所有信息均为必填项");
            return;
        }
        if (userLoginInfo.canChangePassword()) {
            String encodedMessageString = userLoginInfo.assemblingMessage();
            Message message = createMessageToSend(encodedMessageString);
            sendResetpasswordCommand(message);
            showMessageToast("修改中...");

            Intent intent = new Intent(ResetPasswdActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            showMessageToast("密码输入不一致，请重新输入");
            cleanupPasswordInputBox();
        }


    }

    private boolean sendResetpasswordCommand(Message message) {
        return MyService.revHandler.sendMessage(message);
    }

    private void cleanupPasswordInputBox() {
        newPasswd.setText("");
        confirmPasswd.setText("");
    }

    private void showMessageToast(String s) {
        Toast.makeText(ResetPasswdActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    private Message createMessageToSend(String encodedMessageString) {
        Message message = new Message();
        message.what = Constant.MSG_WHAT_SENDMSG;
        message.obj = encodedMessageString;
        return message;
    }

    BroadcastMain receiver;

    //内部类，实现BroadcastReceiver
    public class BroadcastMain extends BroadcastReceiver {
        //必须要重载的方法，用来监听是否有广播发送
        @Override
        public void onReceive(Context context, Intent intent) {
            String MsgStr = intent.getStringExtra("msg");
            Log.i("收到来自服务器的消息", MsgStr);
            if (MsgStr.equals(Constant.Rsset_Succeed)) {
                showMessageToast("重置密码成功");
                intent = new Intent(ResetPasswdActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                showMessageToast("网络故障。。。");
            }
        }
    }

}
