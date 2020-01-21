package com.dixon.bookkeeping.manager;

import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.db.DetailItemBeanDao;

public final class DetailDao extends AbstractDao<DetailItemBeanDao, DetailItemBean> {

    private DetailDao() {
    }

    public static DetailDao instance() {
        return DetailDaoCreator.detailDao;
    }

    private static final class DetailDaoCreator {
        private static final DetailDao detailDao = new DetailDao();
    }

    @Override
    public DetailItemBeanDao getDao() {
        return Dao.getDaoSession().getDetailItemBeanDao();
    }
}
