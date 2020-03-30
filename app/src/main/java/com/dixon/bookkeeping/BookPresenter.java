package com.dixon.bookkeeping;

import android.widget.Adapter;

import com.dixon.bookkeeping.adapter.CalculationDetailAdapter;
import com.dixon.bookkeeping.bean.DayItemBean;
import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.bean.MonthItemBean;
import com.dixon.bookkeeping.manager.DetailDao;
import com.dixon.bookkeeping.manager.SpecialTipDao;
import com.dixon.bookkeeping.manager.TotalDao;
import com.dixon.bookkeeping.util.AppStringUtil;
import com.dixon.bookkeeping.util.DataParser;

import java.util.List;

public class BookPresenter {

    private BookActivity bookActivity;

    public BookPresenter(BookActivity bookActivity) {
        this.bookActivity = bookActivity;
    }

    //加载总数
    public void allocTotalAmount() {
        bookActivity.setTotalView(TotalDao.instance().getTotalAmount());
    }

    //加载详情列表
    public void allocDetailList() {
        List<DetailItemBean> detailItemBeans = DetailDao.instance().queryAll();
        //时间倒序显示
        DataParser.getSortDetailList(detailItemBeans);
        bookActivity.setDetailListView(detailItemBeans);
    }

    //加载详情列表
    public void allocDetailListToLastPosition() {
        List<DetailItemBean> detailItemBeans = DetailDao.instance().queryAll();
        //时间倒序显示
        DataParser.getSortDetailList(detailItemBeans);
        bookActivity.setDetailListViewToLastPosition(detailItemBeans);
    }

    //返回顺序的 DetailList
    private List<DetailItemBean> getSortDetailList() {
        List<DetailItemBean> detailItemBeans = DetailDao.instance().queryAll();
        //时间倒序显示
        DataParser.getSortDetailList(detailItemBeans);
        return detailItemBeans;
    }

    //运行计算 添加新数据并刷新总数
    public void runCalculation(long num, boolean inOrOut) {
        //添加新数据
        DetailItemBean bean = new DetailItemBean.Builder().setAmount(num).isInOrOut(inOrOut).build();
        DetailDao.instance().insert(bean);
        //更新总数据
        TotalDao.instance().reset(num, inOrOut);
        //刷新UI
        allocTotalAmount();
        //因为调整时间的缘故 直接加数据到列表可能导致显示bug 所以重新load一遍 保证数据同步
//        bookActivity.addToDetailListView(bean);
        allocDetailList();
        bookActivity.clearInput();
    }

    //运行计算 删除指定数据并刷新总数
    public void deleteCalculation(DetailItemBean bean, int position) {
        //删除保存数据
        DetailDao.instance().delete(bean);
        //更新总数据
        TotalDao.instance().reset(bean.getAmount(), !bean.isInOrOut());
        //刷新UI
        allocTotalAmount();
        bookActivity.removeFormDetailListView(position);
    }

    public void updateProductDesc(String desc, DetailItemBean bean, int position) {
        bean.setProductDesc(desc);
        DetailDao.instance().update(bean);
        bookActivity.updateToDetailListView(bean, position);
    }

    public void updateDayBefore(DetailItemBean bean, int position) {
        updateTimeDesc(AppStringUtil.getDayBefore(bean.getTimeDesc()), bean, position);
    }

    public void updateDayAfter(DetailItemBean bean, int position) {
        updateTimeDesc(AppStringUtil.getDayAfter(bean.getTimeDesc()), bean, position);
    }

    public void updateTimeDesc(String desc, DetailItemBean bean, int position) {
        bean.setTimeDesc(desc);
        DetailDao.instance().update(bean);
        allocDetailList();
    }

    public void updateTag(String tag, DetailItemBean bean){
        bean.setTag(tag);
        DetailDao.instance().update(bean);
        allocDetailListToLastPosition();
    }

    public String getSummaryData() {
        List<DetailItemBean> detailItemBeans = DataParser.getSortDetailList(DetailDao.instance().queryAll());
        StringBuilder builder = new StringBuilder();
        builder.append("* 总支出：")
                .append(DataParser.getTotalOut(detailItemBeans))
                .append("\n")
                .append("* 平均每天支出：")
                .append(DataParser.getAverageOut(detailItemBeans));
        return builder.toString();
    }

    public void translateToDayList(Adapter adapter) {
        if (adapter instanceof CalculationDetailAdapter) {
            bookActivity.setAdapter(
                    DataParser.toDayList((((CalculationDetailAdapter) adapter).getDetails())),
                    DayItemBean.class);

        } else {
            bookActivity.setAdapter(
                    DataParser.toDayList(getSortDetailList()),
                    DayItemBean.class);
        }
    }

    public void translateToMonthList(Adapter adapter) {
        if (adapter instanceof CalculationDetailAdapter) {
            bookActivity.setAdapter(
                    DataParser.toMonthList((((CalculationDetailAdapter) adapter).getDetails())),
                    MonthItemBean.class);

        } else {
            bookActivity.setAdapter(
                    DataParser.toMonthList(getSortDetailList()),
                    MonthItemBean.class);
        }
    }

    public void loadSpecialTip() {
        String tip = SpecialTipDao.instance().getTip();
        bookActivity.setSpecialTipText(tip);
    }

    public void saveSpecialTip(String tip) {
        SpecialTipDao.instance().resetTip(tip);
        bookActivity.setSpecialTipText(tip);
    }
}

