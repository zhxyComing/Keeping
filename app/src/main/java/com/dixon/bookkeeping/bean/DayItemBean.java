package com.dixon.bookkeeping.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 日条目数据结构 todo
 */
@Entity
public class DayItemBean {

    @Id
    private long id;
    private String timeDesc;
    private boolean inOrOut; //true is in
    private long amount;

    public DayItemBean(String timeDesc, boolean inOrOut, long amount) {
        this.timeDesc = timeDesc;
        this.inOrOut = inOrOut;
        this.amount = amount;
        this.id = System.currentTimeMillis();
    }

    public DayItemBean() {
        this.id = System.currentTimeMillis();
    }

    @Generated(hash = 1491926221)
    public DayItemBean(long id, String timeDesc, boolean inOrOut, long amount) {
        this.id = id;
        this.timeDesc = timeDesc;
        this.inOrOut = inOrOut;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
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
        return "DayItemBean{" +
                "id=" + id +
                ", timeDesc='" + timeDesc + '\'' +
                ", inOrOut=" + inOrOut +
                ", amount=" + amount +
                '}';
    }

    public boolean getInOrOut() {
        return this.inOrOut;
    }
}
