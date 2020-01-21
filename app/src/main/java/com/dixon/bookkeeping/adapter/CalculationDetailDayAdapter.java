package com.dixon.bookkeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dixon.bookkeeping.R;
import com.dixon.bookkeeping.bean.DayItemBean;
import com.dixon.bookkeeping.util.AppFontUtil;

import java.util.List;

public class CalculationDetailDayAdapter extends BaseAdapter implements IDetailData<DayItemBean> {

    private List<DayItemBean> mDetails;
    private Context mContext;

    public CalculationDetailDayAdapter(Context context, List<DayItemBean> details) {
        this.mContext = context;
        this.mDetails = details;
    }

    @Override
    public List<DayItemBean> getDetails() {
        return mDetails;
    }

    @Override
    public int getCount() {
        return mDetails == null ? 0 : mDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_detail_day, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final DayItemBean itemDetail = mDetails.get(position);
        vh.timeDescView.setText(itemDetail.getTimeDesc());
        vh.amountView.setText(String.valueOf(itemDetail.getAmount()));
        if (itemDetail.isInOrOut()) {
            vh.symbolView.setText("+");
            vh.symbolView.setTextColor(mContext.getResources().getColor(R.color.colorSymbolGreen));
            vh.tip.setBackgroundColor(mContext.getResources().getColor(R.color.colorSymbolGreen));
        } else {
            vh.symbolView.setText("-");
            vh.symbolView.setTextColor(mContext.getResources().getColor(R.color.colorSymbolRed));
            vh.tip.setBackgroundColor(mContext.getResources().getColor(R.color.colorSymbolRed));
        }
        AppFontUtil.setNumberFont(vh.amountView);
        return convertView;
    }

    private static final class ViewHolder {

        private TextView timeDescView, amountView, symbolView;
        private View tip;

        ViewHolder(View item) {
            timeDescView = item.findViewById(R.id.tvTimeDesc);
            amountView = item.findViewById(R.id.tvAmount);
            symbolView = item.findViewById(R.id.tvSymbol);
            tip = item.findViewById(R.id.vTip);
        }

    }
}
