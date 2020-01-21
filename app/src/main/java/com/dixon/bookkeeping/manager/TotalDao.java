package com.dixon.bookkeeping.manager;

import com.dixon.bookkeeping.bean.TotalBean;
import com.dixon.bookkeeping.db.TotalBeanDao;

public class TotalDao extends AbstractDao<TotalBeanDao, TotalBean> {

    private TotalDao() {
    }

    public static TotalDao instance() {
        return TotalDaoCreator.totalDao;
    }

    private static final class TotalDaoCreator {
        private static final TotalDao totalDao = new TotalDao();
    }

    @Override
    public TotalBeanDao getDao() {
        return Dao.getDaoSession().getTotalBeanDao();
    }

    public void reset(long num) {
        TotalBean totalBean = queryById(1);
        totalBean.setTotalAmount(num);
        update(totalBean);
    }

    public void reset(long num, boolean isInOrOut) {
        TotalBean totalBean = queryById(1);
        long totalAmount = totalBean.getTotalAmount();
        if (isInOrOut) {
            totalAmount += num;
        } else {
            totalAmount -= num;
        }
        totalBean.setTotalAmount(totalAmount);
        update(totalBean);
    }

    public long getTotalAmount() {
        TotalBean totalBean = queryById(1);
        if (totalBean == null) {
            return 0;
        }
        return totalBean.getTotalAmount();
    }
}
