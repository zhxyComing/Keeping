package com.dixon.bookkeeping.manager;

import com.dixon.bookkeeping.bean.SpecialTipBean;
import com.dixon.bookkeeping.db.SpecialTipBeanDao;

public class SpecialTipDao extends AbstractDao<SpecialTipBeanDao, SpecialTipBean> {

    private SpecialTipDao() {
    }

    public static SpecialTipDao instance() {
        return SpecialTipDaoCreator.tipDao;
    }

    private static final class SpecialTipDaoCreator {
        private static final SpecialTipDao tipDao = new SpecialTipDao();
    }

    @Override
    public SpecialTipBeanDao getDao() {
        return Dao.getDaoSession().getSpecialTipBeanDao();
    }

    public void resetTip(String tip) {
        SpecialTipBean bean = queryById(1);
        bean.setTip(tip);
        update(bean);
    }

    public String getTip() {
        SpecialTipBean specialTipBean = queryById(1);
        if (specialTipBean == null) {
            return "";
        }
        return specialTipBean.getTip();
    }
}
