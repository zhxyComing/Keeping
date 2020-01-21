package com.dixon.bookkeeping.manager;

import java.util.List;

public abstract class AbstractDao<BeanDao extends org.greenrobot.greendao.AbstractDao, Bean> {

    protected abstract BeanDao getDao();

    public void insert(Bean bean) {
        getDao().insert(bean);
    }

    public void delete(Bean bean) {
        getDao().delete(bean);
    }

    public void deleteById(long id) {
        getDao().deleteByKey(id);
    }

    public void update(Bean bean) {
        getDao().update(bean);
    }

    public List<Bean> queryAll() {
        return getDao().loadAll();
    }

    public Bean queryById(long id) {
        return (Bean) getDao().loadByRowId(id);
    }
}
