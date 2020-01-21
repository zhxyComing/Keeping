package com.dixon.bookkeeping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dixon.bookkeeping.adapter.CalculationDetailAdapter;
import com.dixon.bookkeeping.adapter.CalculationDetailDayAdapter;
import com.dixon.bookkeeping.adapter.CalculationDetailMonthAdapter;
import com.dixon.bookkeeping.base.AppStateRegister;
import com.dixon.bookkeeping.base.AppStateTracker;
import com.dixon.bookkeeping.base.BaseActivity;
import com.dixon.bookkeeping.bean.DayItemBean;
import com.dixon.bookkeeping.bean.DetailItemBean;
import com.dixon.bookkeeping.bean.MonthItemBean;
import com.dixon.bookkeeping.util.AppFontUtil;
import com.dixon.bookkeeping.util.AppStringUtil;
import com.dixon.bookkeeping.util.DialogUtil;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.MDEditDialog;
import com.wevey.selector.dialog.MDSelectionDialog;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends BaseActivity implements View.OnClickListener, AppStateTracker.AppStateChangeListener {

    enum Mode {
        normal, day, month
    }

    private BookPresenter mPresenter;

    private ListView mDetailView;
    private TextView mTotalView, mAddBtn, mMinusBtn;
    private EditText mInputView, mSpecialTipView;

    private Mode mode = Mode.normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new BookPresenter(this);
        initView();
        loadData();
        AppStateRegister.register(this, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData();
    }

    @Override
    public void appTurnIntoForeground() {

    }

    @Override
    public void appTurnIntoBackGround() {
        String tip = mSpecialTipView.getText().toString();
        if (!TextUtils.isEmpty(tip)) {
            mPresenter.saveSpecialTip(tip);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_day:
                mode = mode == Mode.day ? Mode.normal : Mode.day;
                setModeData(mode);
                return true;
            case R.id.action_out:
                openSummaryDialog();
                return true;
            case R.id.action_month:
                mode = mode == Mode.month ? Mode.normal : Mode.month;
                setModeData(mode);
                return true;
            case R.id.action_edit:
                startActivity(new Intent(BookActivity.this, EditActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSummaryDialog() {
        DialogUtil.showDescDialog("数据分析", mPresenter.getSummaryData());
    }

    //设置view行为
    private void initView() {
        mAddBtn.setOnClickListener(this);
        mMinusBtn.setOnClickListener(this);
        mTotalView.setOnClickListener(this);
        mDetailView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //1.非普通模式下不能变更数据
                if (mode != Mode.normal) {
                    Toast.makeText(BookActivity.this, " 本模式下无法删除条目", Toast.LENGTH_SHORT).show();
                    return false;
                }
                deleteAskDialog(position);
                return false;
            }
        });
        AppFontUtil.setNumberFont(mTotalView);
    }

    private void deleteAskDialog(final int pos) {

        DialogUtil.showAskDialog("温馨提示", "是否删除本条目？", "删除", "关闭", new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
            @Override
            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                DetailItemBean bean = ((CalculationDetailAdapter) mDetailView.getAdapter()).getDetails().get(pos);
                mPresenter.deleteCalculation(bean, pos);
                dialog.dismiss();
            }

            @Override
            public void clickRightButton(NormalAlertDialog dialog, View view) {
                dialog.dismiss();
            }
        });
    }

    //加载数据
    private void loadData() {
        mPresenter.allocTotalAmount();
        mPresenter.allocDetailList();
        mPresenter.loadSpecialTip();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mDetailView = findViewById(R.id.lvCalculationDetail);
        mTotalView = findViewById(R.id.tvTotal);
        mAddBtn = findViewById(R.id.tvAdd);
        mMinusBtn = findViewById(R.id.tvMinus);
        mInputView = findViewById(R.id.etInput);
        mSpecialTipView = findViewById(R.id.etSpecialTip);
    }

    public void setTotalView(long totalAmount) {
        //换算为万元字符串并显示
        mTotalView.setText(AppStringUtil.getTenThousandNum(totalAmount));
    }

    //设置列表
    public void setDetailListView(List<DetailItemBean> details) {
        mDetailView.setAdapter(new CalculationDetailAdapter(this, details,
                new CalculationDetailAdapter.OnProductDescClickListener() {
                    @Override
                    public void onClick(DetailItemBean bean, int position) {
                        productDescDialog(bean, position);
                    }
                },
                new CalculationDetailAdapter.OnTimeDescClickListener() {
                    @Override
                    public void onClick(DetailItemBean bean, int position) {
                        timeDescDialog(bean, position);
                    }
                }));
    }

    public void productDescDialog(final DetailItemBean bean, final int position) {

        DialogUtil.showEditDialog("添加描述", bean.getProductDesc(), "取消", "确定", new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {

            @Override
            public void clickLeftButton(MDEditDialog dialog, View view) {
                dialog.dismiss();
            }

            @Override
            public void clickRightButton(MDEditDialog dialog, View view) {
                String desc = dialog.getEditTextContent();
                mPresenter.updateProductDesc(desc, bean, position);
                dialog.dismiss();
            }
        });
    }

    public void timeDescDialog(final DetailItemBean bean, final int pos) {
        List<String> data = new ArrayList<>();
        data.add("调至前一天");
        data.add("调至后一天");
        data.add("关闭");

        DialogUtil.showListSelectDialog(data, new DialogInterface.OnItemClickListener<MDSelectionDialog>() {
            @Override
            public void onItemClick(MDSelectionDialog dialog, View button, int position) {
                switch (position) {
                    case 0:
                        mPresenter.updateDayBefore(bean, pos);
                        break;
                    case 1:
                        mPresenter.updateDayAfter(bean, pos);
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    //添加新数据到列表
    public void addToDetailListView(DetailItemBean detail) {
        ((CalculationDetailAdapter) mDetailView.getAdapter()).getDetails().add(0, detail);
        ((CalculationDetailAdapter) mDetailView.getAdapter()).notifyDataSetChanged();
    }

    //移除指定数据到列表
    public void removeFormDetailListView(int position) {
        ((CalculationDetailAdapter) mDetailView.getAdapter()).getDetails().remove(position);
        ((CalculationDetailAdapter) mDetailView.getAdapter()).notifyDataSetChanged();
    }

    //更新指定数据到列表
    public void updateToDetailListView(DetailItemBean bean, int position) {
        ((CalculationDetailAdapter) mDetailView.getAdapter()).getDetails().set(position, bean);
        ((CalculationDetailAdapter) mDetailView.getAdapter()).notifyDataSetChanged();
    }

    //清除输入框
    public void clearInput() {
        mInputView.setText("");
    }

    @Override
    public void onClick(View v) {
        //2.非普通模式下不能变更数据
        if (mode != Mode.normal) {
            Toast.makeText(BookActivity.this, "本模式下无法增添数据", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.tvAdd:
                if (ifInputNotNull()) {
                    try {
                        mPresenter.runCalculation(Long.valueOf(mInputView.getText().toString()), true);
                    } catch (NumberFormatException e) {
                        Toast.makeText(BookActivity.this, "输入不符合标准，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tvMinus:
                if (ifInputNotNull()) {
                    try {
                        mPresenter.runCalculation(Long.valueOf(mInputView.getText().toString()), false);
                    } catch (NumberFormatException e) {
                        Toast.makeText(BookActivity.this, "输入不符合标准，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private boolean ifInputNotNull() {
        return !TextUtils.isEmpty(mInputView.getText());
    }

    public void setAdapter(List list, Class type) {
        if (DayItemBean.class.equals(type)) {
            mDetailView.setAdapter(new CalculationDetailDayAdapter(this, (List<DayItemBean>) list));
        } else if (MonthItemBean.class.equals(type)) {
            mDetailView.setAdapter(new CalculationDetailMonthAdapter(this, (List<MonthItemBean>) list));
        }
    }

    //临时做法 重新拉数据布局
    private void translateToNormalList() {
        mPresenter.allocDetailList();
    }

    //临时做法 加载对应列表视图
    private void setModeData(Mode modeData) {
        if (modeData == Mode.normal) {
            translateToNormalList();
        } else if (modeData == Mode.day) {
            mPresenter.translateToDayList(mDetailView.getAdapter());
        } else if (modeData == Mode.month) {
            mPresenter.translateToMonthList(mDetailView.getAdapter());
        }
    }

    public void setSpecialTipText(String text) {
        mSpecialTipView.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppStateRegister.unRegister(this);
    }
}
