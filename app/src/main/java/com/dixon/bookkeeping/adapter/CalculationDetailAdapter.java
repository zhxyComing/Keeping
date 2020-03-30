package com.dixon.bookkeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dixon.bookkeeping.R;
import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.bean.ITag;
import com.dixon.bookkeeping.util.AppFontUtil;

import java.util.List;

public class CalculationDetailAdapter extends BaseAdapter implements IDetailData<DetailItemBean> {

    private List<DetailItemBean> mDetails;
    private Context mContext;
    private OnProductDescClickListener onProductDescClickListener;
    private OnTimeDescClickListener onTimeDescClickListener;
    private OnTagClickListener onTagClickListener;

    public CalculationDetailAdapter(Context context,
                                    List<DetailItemBean> details,
                                    OnProductDescClickListener onProductDescClickListener,
                                    OnTimeDescClickListener onTimeDescClickListener,
                                    OnTagClickListener onTagClickListener) {
        this.mContext = context;
        this.mDetails = details;
        this.onProductDescClickListener = onProductDescClickListener;
        this.onTimeDescClickListener = onTimeDescClickListener;
        this.onTagClickListener = onTagClickListener;
    }

    @Override
    public List<DetailItemBean> getDetails() {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_detail, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final DetailItemBean itemDetail = mDetails.get(position);
        vh.timeDescView.setText(itemDetail.getTimeDesc());
        vh.productDescView.setText(itemDetail.getProductDesc());
        vh.amountView.setText(String.valueOf(itemDetail.getAmount()));
        if (itemDetail.isInOrOut()) {
            vh.symbolView.setText("+");
            vh.symbolView.setTextColor(mContext.getResources().getColor(R.color.colorSymbolGreen));
        } else {
            vh.symbolView.setText("-");
            vh.symbolView.setTextColor(mContext.getResources().getColor(R.color.colorSymbolRed));
        }

        vh.productDescView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductDescClickListener != null) {
                    onProductDescClickListener.onClick(itemDetail, position);
                }
            }
        });
        vh.timeDescView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTimeDescClickListener != null) {
                    onTimeDescClickListener.onClick(itemDetail, position);
                }
            }
        });
        vh.tagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTagClickListener != null) {
                    onTagClickListener.onClick(itemDetail, position);
                }
            }
        });

        AppFontUtil.setNumberFont(vh.amountView);
        setTagIcon(vh.tagView, itemDetail.getTag());
        return convertView;
    }

    private void setTagIcon(ImageView tagView, String tag) {
        if (tag == null) {
            tagView.setImageResource(R.mipmap.tag_no_select);
            return;
        }
        switch (tag) {
            case ITag.TAG_GAME:
                tagView.setImageResource(R.mipmap.tag_game);
                break;
            case ITag.TAG_MARKS:
                tagView.setImageResource(R.mipmap.tag_marks);
                break;
            case ITag.TAG_EAT:
                tagView.setImageResource(R.mipmap.tag_eat);
                break;
            default:
                tagView.setImageResource(R.mipmap.tag_no_select);
        }
    }

    public interface OnProductDescClickListener {
        void onClick(DetailItemBean bean, int position);
    }

    public interface OnTimeDescClickListener {
        void onClick(DetailItemBean bean, int position);
    }

    public interface OnStarClickListener {
        void onClick(DetailItemBean bean, int position);
    }

    public interface OnTagClickListener {
        void onClick(DetailItemBean bean, int position);
    }

    private static final class ViewHolder {

        private TextView timeDescView, productDescView, amountView, symbolView;
        private ImageView tagView;

        ViewHolder(View item) {
            timeDescView = item.findViewById(R.id.tvTimeDesc);
            productDescView = item.findViewById(R.id.tvProductDesc);
            amountView = item.findViewById(R.id.tvAmount);
            symbolView = item.findViewById(R.id.tvSymbol);
            tagView = item.findViewById(R.id.ivTagChoose);
        }
    }
}
