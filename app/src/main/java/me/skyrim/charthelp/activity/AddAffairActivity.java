package me.skyrim.charthelp.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.koma.greendao.gen.AffairBeanDao;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.base.BaseFunc;
import me.skyrim.charthelp.base.BeanDaoManager;
import me.skyrim.charthelp.dao.AffairBean;
import me.skyrim.charthelp.event.AffairAddEvent;

/**
 * Created by Obl on 2017/8/19.
 * me.skyrim.charthelp.activity
 */

public class AddAffairActivity extends BaseActivity {
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.editPrice)
    EditText mEditPrice;
    @BindView(R.id.tvData)
    TextView mTvData;
    @BindView(R.id.tvDel)
    TextView mTvDel;
    @BindView(R.id.tvNum)
    TextView mTvNum;
    @BindView(R.id.tvAdd)
    TextView mTvAdd;
    private TimePickerView mPvTime;

    @Override
    public void bindData() {
        initTimePicker();
    }

    @Override
    public int initLayout() {
        mTvTitle.setText("添加员工花项");
        mBundle = getIntent().getBundleExtra("bundle");
        mTvToolBarRight.setVisibility(View.VISIBLE);
        return R.layout.activity_add_affair;
    }

    @Override
    public void rightButtonClick() {
        if (mEditText.getText() == null || mEditText.getText().toString().equals("")) {
            BaseFunc.showToast(mBaseActivity, "请输入商品信息");
            return;
        }
        if (mEditPrice.getText() == null || mEditPrice.getText().toString().equals("")) {
            BaseFunc.showToast(mBaseActivity, "请输入商品单价");
            return;
        }
        AffairBean affairBean = new AffairBean();
        AffairBeanDao affairDao = BeanDaoManager.Builder().getAffairDao();
        List<AffairBean> beanList = affairDao.queryBuilder().list();
        affairBean.setId(beanList.size() == 0 ? 0 : beanList.get(beanList.size() - 1).getId() + 1);

        affairBean.setWorkerId(mBundle.getLong("workerId"));

        affairBean.setContent(mEditText.getText().toString());

        int count = Integer.parseInt(mTvNum.getText().toString());

        affairBean.setCount(count);
        float price = Float.parseFloat(mEditPrice.getText().toString());
        affairBean.setPrice(price);
        affairBean.setCountPrice(count * price);

        affairBean.setDate(mTvData.getText().toString());

        affairDao.insert(affairBean);
        EventBus.getDefault().post(new AffairAddEvent());
        finish();
    }

    @OnClick({R.id.tvData, R.id.tvDel, R.id.tvAdd})
    public void onClick(View view) {
        int anInt = Integer.parseInt(mTvNum.getText().toString());
        switch (view.getId()) {
            case R.id.tvData:
                BaseFunc.closeInput(this, mTvData);
                if (!mPvTime.isShowing()) {
                    mPvTime.show();
                }
                break;
            case R.id.tvDel:
                if (anInt > 0) {
                    String del = "" + (--anInt);
                    mTvNum.setText(del);
                }
                break;
            case R.id.tvAdd:
                String add = "" + (++anInt);
                mTvNum.setText(add);
                break;
        }
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 23);
        mPvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvData.setText(getTime(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
