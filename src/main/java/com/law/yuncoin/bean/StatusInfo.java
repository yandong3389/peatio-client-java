package com.law.yuncoin.bean;

import java.math.BigDecimal;

public class StatusInfo {

    private boolean upFlag;
    private BigDecimal level;
    
    private BigDecimal buy1;
    private BigDecimal sell1;
    
    public BigDecimal getLevel() {
        return level;
    }
    public void setLevel(BigDecimal level) {
        this.level = level;
    }
    public BigDecimal getBuy1() {
        return buy1;
    }
    public void setBuy1(BigDecimal buy1) {
        this.buy1 = buy1;
    }
    public BigDecimal getSell1() {
        return sell1;
    }
    public void setSell1(BigDecimal sell1) {
        this.sell1 = sell1;
    }
    public boolean isUpFlag() {
        return upFlag;
    }
    public void setUpFlag(boolean upFlag) {
        this.upFlag = upFlag;
    }
    
}
