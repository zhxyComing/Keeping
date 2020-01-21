package com.dixon.bookkeeping.bean;

/**
 * 月条目数据结构 todo
 */
public class MonthItemBean {

    private String monthDesc;
    private boolean inOrOut; //true is in
    private long amount;

    public MonthItemBean(String monthDesc, boolean inOrOut, long amount) {
        this.monthDesc = monthDesc;
        this.inOrOut = inOrOut;
        this.amount = amount;
    }

    public MonthItemBean() {
    }

    public void setMonthDesc(String monthDesc) {
        this.monthDesc = monthDesc;
    }

    public String getMonthDesc() {
        return monthDesc;
    }

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MonthItemBean{" +
                "monthDesc='" + monthDesc + '\'' +
                ", inOrOut=" + inOrOut +
                ", amount=" + amount +
                '}';
    }
}
