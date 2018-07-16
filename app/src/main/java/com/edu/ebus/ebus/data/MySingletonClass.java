package com.edu.ebus.ebus.data;

public class MySingletonClass {

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

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }
}
