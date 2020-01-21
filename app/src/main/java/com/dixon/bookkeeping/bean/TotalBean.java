package com.dixon.bookkeeping.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TotalBean {

    @Id
    private long id;
    //记录次数
    private int count;
    //总数
    private long totalAmount;

    public TotalBean(int count, long totalAmount) {
        this.count = count;
        this.totalAmount = totalAmount;
        this.id = 1;
    }

    @Generated(hash = 181178924)
    public TotalBean(long id, int count, long totalAmount) {
        this.id = id;
        this.count = count;
        this.totalAmount = totalAmount;
    }

    @Generated(hash = 104034742)
    public TotalBean() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TotalBean{" +
                "id=" + id +
                ", count=" + count +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
