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
import android.widget.EditText;
import android.widget.Toast;

import com.wswenyue.parkinglot.R;
import com.wswenyue.parkinglot.constant.Constant;
import com.wswenyue.parkinglot.domain.UserLoginInfo;
import com.wswenyue.parkinglot.service.BackendService;

public class LoginActivity extends BasicActivity {

    private EditText userNameTextBox, passwdTextBox;
    private String uname = "";
    private String upasswd = "";
    private Button loginButton = null ;
    private Button forgetPasswordButton = null;
    private Button registerButton = null;
    BroadcastMain receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameTextBox = findViewById(R.id.usernameView);
        passwdTextBox = findViewById(R.id.passwordView);

        loginButton = findViewById(R.id.login_bt);
        forgetPasswordButton = findViewById(R.id.bt_forgetPassword);
        registerButton = findViewById(R.id.register_bt);

        receiver = new BroadcastMain();
        IntentFilter filter = createIntentFilter();
        registerReceiver(receiver, filter);

    }

    private IntentFilter createIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadCastSend);
        return filter;
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    public void login(View source) {
        uname = userNameTextBox.getText().toString().trim();
        upasswd = passwdTextBox.getText().toString().trim();
        UserLoginInfo loginInfo = UserLoginInfo.createUserLoginInfo(uname,upasswd,null,null,null);
        if (loginInfo.canLogin()) {
            askForLogin(null);
            showMessageToast("Login...");
        } else {
            showMessageToast("用户名和密码不能为空");
        }

    }

    private void showMessageToast(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void forgetPassword(View source) {
        startActivity(
                new Intent(this, ResetPasswdActivity.class));
    }

    public void startRegister(View source) {
        startActivity(
                new Intent(this, RegisterActivity.class));
    }

    public void askForLogin(UserLoginInfo loginInfo){
        Log.i("Login", "Login...");

        Message message = new Message();
        message.what = Constant.MSG_WHAT_SENDMSG;
        message.obj = loginInfo.assemblingMessageForLogin();
        BackendService.revHandler.sendMessage(message);
    }

    public void storeUsername(){
        SharedPreferences sharedPreferences =getSharedPreferences("parkinglotInfo",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(uname != null && !uname.equals("")){
            editor.putString("uname",uname);
            editor.commit();
        }
    }


    public class BroadcastMain extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String MsgStr = intent.getStringExtra("msg");
            if(MsgStr.equals(Constant.Login_Succeed)){
                storeUsername();
                showMessageToast("登陆成功");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else if(MsgStr.equals(Constant.Login_Fail)){
                showMessageToast("用户名和密码错误，请核对后登陆");
            }
        }
    }

}

