package com.edu.ebus.ebus.login;

import com.edu.ebus.ebus.data.UserAccount;

public class LoginResult {

    private int code;
    private UserAccount account;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

}
