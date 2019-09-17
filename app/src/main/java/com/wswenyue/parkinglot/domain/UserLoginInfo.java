package com.wswenyue.parkinglot.domain;

import android.support.annotation.NonNull;

import com.wswenyue.parkinglot.constant.Constant;

import static com.wswenyue.parkinglot.constant.Constant.PROTOCOLSEPPERATOR;

public class UserLoginInfo {
    private String uNewPasswd = null;
    private String uConfirmPasswd = null;
    private String uphone = null;
    private String umail = null;

    private UserLoginInfo(String uNewPasswd, String uConfirmPasswd, String uphone, String umail) {
        this.uNewPasswd = uNewPasswd;
        this.uConfirmPasswd = uConfirmPasswd;
        this.uphone = uphone;
        this.umail = umail;
    }


    public boolean isValid() {
        return !"".equals(uNewPasswd) && uNewPasswd != null &&
                !"".equals(uConfirmPasswd) && uConfirmPasswd != null &&
                !"".equals(uphone) && uphone != null &&
                !"".equals(umail) && umail != null;
    }

    public boolean canChangePassword() {
        return this.uNewPasswd.equals(this.uConfirmPasswd);
    }

    public static com.wswenyue.parkinglot.domain.UserLoginInfo createUserLoginInfo(String uNewPasswd, String uConfirmPasswd, String uphone, String umail) {
        return new com.wswenyue.parkinglot.domain.UserLoginInfo(uNewPasswd, uConfirmPasswd, uphone, umail);
    }

    @NonNull
    public String assemblingMessage() {
        StringBuffer messageCode = new StringBuffer();
        messageCode.append(Constant.Reset).append(PROTOCOLSEPPERATOR).append(uNewPasswd)
                .append(PROTOCOLSEPPERATOR).append(umail).append(PROTOCOLSEPPERATOR).append(uphone);
        return messageCode.toString();
    }
}
