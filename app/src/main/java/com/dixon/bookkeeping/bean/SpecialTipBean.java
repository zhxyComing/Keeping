package com.dixon.bookkeeping.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SpecialTipBean {

    @Id
    private Long id;
    //tips
    private String tip;

    public SpecialTipBean(String tip) {
        this.tip = tip;
        this.id = 1L;
    }

    @Generated(hash = 2032744860)
    public SpecialTipBean(Long id, String tip) {
        this.id = id;
        this.tip = tip;
    }

    @Generated(hash = 471953087)
    public SpecialTipBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
