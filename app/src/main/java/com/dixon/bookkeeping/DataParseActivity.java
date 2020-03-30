package com.dixon.bookkeeping;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.dixon.bookkeeping.base.BaseActivity;
import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.bean.ITag;
import com.dixon.bookkeeping.manager.DetailDao;

import java.util.List;

public class DataParseActivity extends BaseActivity {

    private TextView mGameCount, mMarksCount, mEatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_parse);

        initData();
    }

    private void initData() {
        mGameCount.setText(getTagCount(ITag.TAG_GAME) + " ¥");
        mMarksCount.setText(getTagCount(ITag.TAG_MARKS) + " ¥");
        mEatCount.setText(getTagCount(ITag.TAG_EAT) + " ¥");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mGameCount = findViewById(R.id.tvGameCount);
        mMarksCount = findViewById(R.id.tvMarksCount);
        mEatCount = findViewById(R.id.tvEatCount);
    }

    private int getTagCount(String tag) {
        int res = 0;
        List<DetailItemBean> detailItemBeans = DetailDao.instance().queryAll();
        for (int i = 0; i < detailItemBeans.size(); i++) {
            DetailItemBean detailItemBean = detailItemBeans.get(i);
            String itemTag = detailItemBean.getTag();
            if (!TextUtils.isEmpty(itemTag) && tag.equals(itemTag) && !detailItemBean.isInOrOut()) {
                res += detailItemBean.getAmount();
            }
        }
        return res;
    }
}
