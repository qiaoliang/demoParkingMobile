package com.wswenyue.parkinglot.domain;

import com.wswenyue.parkinglot.constant.Constant;

import static com.wswenyue.parkinglot.constant.Constant.*;
import static com.wswenyue.parkinglot.constant.Constant.PROTOCOLSEPPERATOR;

public class UserLoginInfo {

    private String uName = null;
    private String passWord = null;
    private String uConfirmPasswd = null;
    private String uphone = null;
    private String umail = null;


    private UserLoginInfo(String uName, String uNewPasswd, String uConfirmPasswd, String uphone, String umail) {
        this.uName = uName;
        this.passWord = uNewPasswd;
        this.uConfirmPasswd = uConfirmPasswd;
        this.uphone = uphone;
        this.umail = umail;
    }


    public boolean isValid() {
        return !"".equals(passWord) && passWord != null &&
                !"".equals(uConfirmPasswd) && uConfirmPasswd != null &&
                !"".equals(uphone) && uphone != null &&
                !"".equals(umail) && umail != null;
    }

    public boolean canChangePassword() {
        return this.passWord.equals(this.uConfirmPasswd);
    }

    public static com.wswenyue.parkinglot.domain.UserLoginInfo createUserLoginInfo(String uName, String uNewPasswd, String uConfirmPasswd, String uphone, String umail) {
        return new com.wswenyue.parkinglot.domain.UserLoginInfo(null,uNewPasswd, uConfirmPasswd, uphone, umail);
    }

    public String assemblingMessageForResetPassword() {
        StringBuffer messageCode = new StringBuffer();
        messageCode.append(Reset).append(PROTOCOLSEPPERATOR).append(passWord)
                .append(PROTOCOLSEPPERATOR).append(umail).append(PROTOCOLSEPPERATOR).append(uphone);
        return messageCode.toString();
    }

    public boolean canLogin() {
        return uName!=null&& !uName.isEmpty()
                && passWord!=null && !passWord.isEmpty();
    }

    public String assemblingMessageForLogin() {
        return new StringBuffer()
                .append(Login).append(PROTOCOLSEPPERATOR)
                .append(uName).append(PROTOCOLSEPPERATOR)
                .append(passWord)
                .toString();
    }
}
