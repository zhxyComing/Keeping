package com.dixon.bookkeeping.bean;

import android.text.TextUtils;

import com.dixon.bookkeeping.util.AppStringUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DetailItemBean {

    @Id
    private long id;
    private String timeDesc;
    private boolean inOrOut; //true is in
    private long amount;
    private String productDesc;

    public DetailItemBean() {
        this.id = System.currentTimeMillis();
    }

    public DetailItemBean(String timeDesc, boolean inOrOut, int amount, String productDesc) {
        this.timeDesc = timeDesc;
        this.inOrOut = inOrOut;
        this.amount = amount;
        this.productDesc = productDesc;
        this.id = System.currentTimeMillis();
    }

    @Generated(hash = 1318208913)
    public DetailItemBean(long id, String timeDesc, boolean inOrOut, long amount,
            String productDesc) {
        this.id = id;
        this.timeDesc = timeDesc;
        this.inOrOut = inOrOut;
        this.amount = amount;
        this.productDesc = productDesc;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Override
    public String toString() {
        return "DetailItemBean{" +
                "timeDesc='" + timeDesc + '\'' +
                ", inOrOut=" + inOrOut +
                ", amount=" + amount +
                ", productDesc='" + productDesc + '\'' +
                '}';
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getInOrOut() {
        return this.inOrOut;
    }

    public static final class Builder {

        private long amount;
        private boolean inOrOut;
        private String productDesc;

        public Builder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public Builder isInOrOut(boolean inOrOut) {
            this.inOrOut = inOrOut;
            return this;
        }

        public Builder setProductDesc(String productDesc) {
            this.productDesc = productDesc;
            return this;
        }

        public DetailItemBean build() {
            DetailItemBean bean = new DetailItemBean();
            bean.setAmount(amount);
            bean.setInOrOut(inOrOut);
            bean.setProductDesc(TextUtils.isEmpty(productDesc) ? "尚未添加" : productDesc);
            bean.setTimeDesc(AppStringUtil.getTodayString());
            return bean;
        }
    }
}
