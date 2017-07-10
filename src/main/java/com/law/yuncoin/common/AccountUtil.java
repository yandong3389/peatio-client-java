package com.law.yuncoin.common;

import org.bitcoin.market.bean.AppAccount;

public class AccountUtil {
    
    public static AppAccount getAppAccount() {
        AppAccount appAccount = new AppAccount();
        appAccount.setId(1L);
        appAccount.setAccessKey("xx");
        appAccount.setSecretKey("xx");
        return appAccount;
    }
}
