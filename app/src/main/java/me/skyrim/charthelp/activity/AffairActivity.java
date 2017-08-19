package me.skyrim.charthelp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.koma.greendao.gen.AffairBeanDao;
import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.adapter.AffairAdapter;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.base.BaseFunc;
import me.skyrim.charthelp.base.BeanDaoManager;
import me.skyrim.charthelp.dao.AffairBean;
import me.skyrim.charthelp.dao.WorkerBean;
import me.skyrim.charthelp.event.AffairAddEvent;
import me.skyrim.charthelp.event.WorkerTotalMoneyEvent;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.activity
 */

public class AffairActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvTotalMoney)
    TextView mTvTotalMoney;
    private String mDepartmentName;
    private String mWorkerName;
    private float mTotalMoney;
    private long mWorkerId;
    private AffairBeanDao mAffairDao;
    private List<AffairBean> mBeanList;
    private AffairAdapter mAdapter;

    @Override
    public void bindData() {
        mTvName.setText(String.format(Locale.CHINA, "%s: %s", mDepartmentName, mWorkerName));
        mTvTotalMoney.setText(String.format(Locale.CHINA, "该员工共花费: %.2f", mTotalMoney));
        mAffairDao = BeanDaoManager.Builder().getAffairDao();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBeanList = mAffairDao.queryBuilder().where(AffairBeanDao.Properties.WorkerId.eq(mWorkerId))
                .list();
        mAdapter = new AffairAdapter(this, mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        View footer = View.inflate(this, R.layout.adapter_footer_affair, null);
        mAdapter.addFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("workerId", mWorkerId);
                BaseFunc.startActivity(mBaseActivity, AddAffairActivity.class, bundle);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AffairBean affairBean = mAffairDao.queryBuilder().where(AffairBeanDao.Properties.Id.eq
                        (mAdapter.getData().get(position)
                                .getId())).unique();
                switch (view.getId()) {
                    case R.id.tv_delete:
                        mAffairDao.delete(affairBean);
                        mAdapter.remove(position);
                        refreshData();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int initLayout() {
        EventBus.getDefault().register(this);
        mBundle = getIntent().getBundleExtra("bundle");
        mDepartmentName = mBundle.getString("departmentName");
        mTvTitle.setText(mDepartmentName);
        mWorkerName = mBundle.getString("workerName");
        mTotalMoney = mBundle.getFloat("totalMoney");
        mWorkerId = mBundle.getLong("workerId");
        return R.layout.activity_affair;
    }

    @Subscribe
    public void changeAffair(AffairAddEvent event) {
        refreshData();
        mAdapter.setNewData(mBeanList);
    }

    private void refreshData() {
        mBeanList = mAffairDao.queryBuilder().where(AffairBeanDao.Properties.WorkerId.eq(mWorkerId))
                .list();
        float curMoney = 0;
        for (AffairBean affairBean : mBeanList) {
            curMoney += affairBean.getCountPrice();
        }
        curMoney += mTotalMoney;
        mTvTotalMoney.setText(String.format(Locale.CHINA, "共花费 %.2f", curMoney));
        WorkerBeanDao workerDao = BeanDaoManager.Builder().getWorkerDao();
        WorkerBean workerBean = workerDao.queryBuilder().where(WorkerBeanDao.Properties.Id.eq(mWorkerId))
                .unique();
        workerBean.setTotalPrice(curMoney);
        workerDao.update(workerBean);
        EventBus.getDefault().post(new WorkerTotalMoneyEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
