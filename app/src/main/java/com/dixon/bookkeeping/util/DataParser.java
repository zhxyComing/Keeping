package com.dixon.bookkeeping.util;

import com.dixon.bookkeeping.bean.DayItemBean;
import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.bean.MonthItemBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 对列表等数据进行转换
 */
public final class DataParser {

    public static List<DayItemBean> toDayList(List<DetailItemBean> srcList) {
        List<DayItemBean> resList = new ArrayList<>();
        if (srcList.isEmpty()) {
            return resList;
        }
        // 该list是有序的 利用有序转为日数据
        String timeDesc = srcList.get(0).getTimeDesc();
        long amount = 0;

        DetailItemBean bean;
        for (int i = 0; i < srcList.size(); i++) {
            bean = srcList.get(i);
            if (AppStringUtil.isSameDay(timeDesc, bean.getTimeDesc())) {
                // 同一天 数值相加
                amount = bean.isInOrOut() ? amount + bean.getAmount() : amount - bean.getAmount();
                //最后一天 Day不可能变了
                if (i == srcList.size() - 1) {
                    resList.add(new DayItemBean(timeDesc, amount >= 0, Math.abs(amount)));
                }
            } else {
                // 说明Day变了
                // 保存数据
                resList.add(new DayItemBean(timeDesc, amount >= 0, Math.abs(amount)));
                // 重新赋值
                amount = 0;
                timeDesc = bean.getTimeDesc();
                // 本次Bean重新累加
                --i;
            }
        }
        return resList;
    }

    public static List<MonthItemBean> toMonthList(List<DetailItemBean> srcList) {
        List<MonthItemBean> resList = new ArrayList<>();
        if (srcList.isEmpty()) {
            return resList;
        }
        // 该list是有序的 利用有序转为月数据
        // 找到月份切换点
        List<Integer> indexList = new ArrayList<>();
        indexList.add(0);
        String lastTime = srcList.get(0).getTimeDesc();
        for (int i = 1; i < srcList.size(); i++) {
            DetailItemBean bean = srcList.get(i);
            if (!isSameMonth(lastTime, bean.getTimeDesc())) {
                indexList.add(i);
                lastTime = bean.getTimeDesc();
            }
        }
        indexList.add(srcList.size());
        //说明月份没变过
        if (indexList.size() == 2) {
            MonthItemBean monthItemBean = new MonthItemBean();
            monthItemBean.setInOrOut(false);
            monthItemBean.setAmount(getTotalOut(srcList));
            monthItemBean.setMonthDesc(getMonthString(lastTime));
            resList.add(monthItemBean);
            return resList;
        }
        for (int i = 0; i < indexList.size() - 1; i++) {
            List<DetailItemBean> detailItemBeans = srcList.subList(indexList.get(i), indexList.get(i + 1));
            MonthItemBean monthItemBean = new MonthItemBean();
            monthItemBean.setInOrOut(false);
            monthItemBean.setAmount(getTotalOut(detailItemBeans));
            monthItemBean.setMonthDesc(getMonthString(detailItemBeans.get(0).getTimeDesc()));
            resList.add(monthItemBean);
        }
        return resList;
    }

    private static boolean isSameMonth(String time1, String time2) {
        String[] yearMonthDay1 = time1.split("\\.");
        String[] yearMonthDay2 = time2.split("\\.");
        if (yearMonthDay1[0].equals(yearMonthDay2[0]) &&
                yearMonthDay1[1].equals(yearMonthDay2[1])) {
            return true;
        }
        return false;
    }

    private static String getMonthString(String time) {
        return time.substring(0, time.lastIndexOf("."));
    }

    //0 -1 3
    //1 0 -1
    //计算支出总天数 会抛去当天没有支出的天
    public static int getOutDays(List<DetailItemBean> srcList) {
        int day = 0;
        String lastDay = "";
        boolean hasOut = false;
        for (DetailItemBean bean : srcList) {
            if (bean.getTimeDesc() != null
                    && !bean.getTimeDesc().equals(lastDay)
                    && hasOut) {
                lastDay = bean.getTimeDesc();
                hasOut = false;
                day++;
            } else if (bean.getTimeDesc() != null
                    && !bean.getTimeDesc().equals(lastDay)) {
                lastDay = bean.getTimeDesc();
            }
            if (!bean.isInOrOut()) {
                hasOut = true;
            }
        }
        //最后一天也要累加
        if (hasOut) {
            day++;
        }
        return day;
    }

    public static int getTotalOut(List<DetailItemBean> srcList) {
        int total = 0;
        for (DetailItemBean bean : srcList) {
            if (!bean.isInOrOut()) {
                total += bean.getAmount();
            }
        }
        return total;
    }

    public static String getAverageOut(List<DetailItemBean> srcList) {
        int outDay = getOutDays(srcList);
        if (outDay == 0) {
            return "0";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format((double) getTotalOut(srcList) / outDay);
    }

    public static List<DetailItemBean> getSortDetailList(List<DetailItemBean> srcList) {
        //时间倒序显示
        Collections.sort(srcList, new Comparator<DetailItemBean>() {
            public int compare(DetailItemBean arg0, DetailItemBean arg1) {
                return (int) (AppStringUtil.getTime(arg0.getTimeDesc()) - AppStringUtil.getTime(arg1.getTimeDesc()));
            }
        });
        Collections.reverse(srcList);
        return srcList;
    }
}
