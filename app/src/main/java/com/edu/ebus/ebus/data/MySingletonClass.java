package com.edu.ebus.ebus.data;

public class MySingletonClass {

    private int loginMethod = 1;
    private static MySingletonClass instance;
    private UserAccount account;
    private MySingletonClass() {

    }

    public static MySingletonClass getInstance() {
        if (instance == null) {
            instance = new MySingletonClass();
        }
        return instance;
    }

    public int getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(int loginMethod) {
        this.loginMethod = loginMethod;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }
}
